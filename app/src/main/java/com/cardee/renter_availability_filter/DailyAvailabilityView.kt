package com.cardee.renter_availability_filter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.NumberPicker
import android.widget.Toast
import com.cardee.R
import com.cardee.util.AvailabilityFromFilterDelegate
import kotlinx.android.synthetic.main.view_daily_availability.view.*
import java.util.*


class DailyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), FilterViewContract {

    private val noTiming = "--"
    private var toast: Toast? = null
    private val adapter = CalendarAdapter()
    private val delegate: AvailabilityFromFilterDelegate = AvailabilityFromFilterDelegate()
    private val timeValues: Array<String?> = arrayOfNulls(25)
    private var finishCallback: (Boolean) -> Unit = {}
    private var presenter: AvailabilityFilterPresenter? = null
    private val doOnSave = { finishCallback.invoke(true) }
    private val doOnReset = { clearSelection() }
    private val toggle: (v: View) -> Unit = {
        if (presenter?.isTimingAllowed() == true && calendar.visibility == View.VISIBLE) {
            toggleState()
        } else {
            showMessage("Please select dates first")
        }
    }
    private val toggleBack: (v: View) -> Unit = { toggleState() }
    private val onTimePicked: (NumberPicker?, Int, Int) -> Unit = { _, _, _ ->
        val pickupValue = timeValues[pickupTimePicker.value]
        val returnValue = timeValues[returnTimePicker.value]
        val pickupTime = if (pickupValue == noTiming) null else pickupValue
        val returnTime = if (returnValue == noTiming) null else returnValue
        changeDailyTiming(pickupTime, returnTime)
    }

    init {
        timeValues[0] = noTiming
        val values = context.resources.getStringArray(R.array.availability_time_titles)
        for (i in 1..values.size) {
            timeValues[i] = values[i - 1]
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        calendar.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->
            if (selection.isEmpty()) {
                changeDailyRange(null, null)
            } else {
                changeDailyRange(selection[0], selection[selection.size - 1])
            }
        }
        setTime.setOnClickListener(toggle)
        setTimeIcon.setOnClickListener(toggle)
        backTitle.setOnClickListener(toggleBack)
        backTitleIcon.setOnClickListener(toggleBack)
        btnSave.setOnClickListener { saveFilter(doOnSave) }
        btnReset.setOnClickListener { resetFilter(doOnReset) }
        initNumberPickers()
    }

    override fun setPresenter(presenter: AvailabilityFilterPresenter) {
        this.presenter = presenter
        this.presenter?.getFilter { filter ->
            filter.bookingHourly?.let { hourly ->
                if (!hourly) {
                    if (filter.rentalPeriodBegin != null && filter.rentalPeriodEnd != null) {
                        delegate.onInitCalendarSelection(adapter, filter.rentalPeriodBegin!!, filter.rentalPeriodEnd!!)
                    } else return@let
                    filter.pickupTime?.let { delegate.onInitTimingSelection(pickupTimePicker, it, timeValues) }
                    filter.returnTime?.let { delegate.onInitTimingSelection(returnTimePicker, it, timeValues) }
                    delegate.onSetTitlesFromFilter(dateFrom, dateTo, filter)
                    delegate.onSetSubmitTitle(btnSave, filter, false)
                }
            }
        }
        adapter.setAvailabilityRange(arrayOf("2018-02-10", "2018-02-11", "2018-02-12", "2018-02-14", "2018-02-21", "2018-02-22"))
    }

    override fun setCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }

    private fun toggleState() {
        val flag = calendar.visibility == View.VISIBLE
        calendar.visibility = if (!flag) View.VISIBLE else View.GONE
        setTime.visibility = if (!flag) View.VISIBLE else View.GONE
        backTitle.visibility = if (flag) View.VISIBLE else View.GONE
        setTimeIcon.visibility = if (!flag) View.VISIBLE else View.GONE
        timeSelector.visibility = if (flag) View.VISIBLE else View.GONE
        backTitleIcon.visibility = if (flag) View.VISIBLE else View.GONE
        pickupTimeTitle.visibility = if (flag) View.VISIBLE else View.GONE
        returnTimeTitle.visibility = if (flag) View.VISIBLE else View.GONE
        pickupTimePicker.visibility = if (flag) View.VISIBLE else View.GONE
        returnTimePicker.visibility = if (flag) View.VISIBLE else View.GONE
        pickerBackground.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun initNumberPickers() {
        pickupTimePicker.displayedValues = timeValues
        pickupTimePicker.minValue = 0
        pickupTimePicker.maxValue = timeValues.size - 1
        pickupTimePicker.wrapSelectorWheel = false
        returnTimePicker.displayedValues = timeValues
        returnTimePicker.minValue = 0
        returnTimePicker.maxValue = timeValues.size - 1
        returnTimePicker.wrapSelectorWheel = false
        setDividerColor(pickupTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
        setDividerColor(returnTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
        pickupTimePicker.setOnValueChangedListener(onTimePicked)
        returnTimePicker.setOnValueChangedListener(onTimePicked)
    }

    private fun setDividerColor(picker: NumberPicker, color: Int) {
        val fields = NumberPicker::class.java.declaredFields
        fields.filter { it.name == "mSelectionDivider" }[0].let { field ->
            field.isAccessible = true
            try {
                val drawable = ColorDrawable(color)
                field.set(picker, drawable)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun saveFilter(doOnSave: () -> Unit) {
        presenter?.saveFilter(false, doOnSave)
    }

    override fun resetFilter(doOnReset: () -> Unit) {
        presenter?.resetFilter(doOnReset)
    }

    private fun clearSelection() {
        calendar.reset()
        pickupTimePicker.value = 0
        returnTimePicker.value = 0
        if (calendar.visibility != View.VISIBLE) {
            toggleState()
        }
    }

    fun reset() {
        resetFilter(doOnReset)
    }

    fun configureSingleAciton() {
        btnReset.visibility = View.GONE
    }

    private fun changeDailyRange(begin: Date?, end: Date?) {
        presenter?.setDailyFilter(begin, end)
        delegate.onSetTitleFromDate(dateFrom, begin)
        delegate.onSetTitleFromDate(dateTo, end)
        presenter?.doUpdate { filter ->
            delegate.onSetSubmitTitle(btnSave, filter, false)
        }
    }

    private fun changeDailyTiming(pickupTime: String?, returnTime: String?) {
        presenter?.setDailyFilterTime(pickupTime, returnTime)
        delegate.onSetTiming(dateFrom, pickupTime)
        delegate.onSetTiming(dateTo, returnTime)
    }

    private fun showMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}