package com.cardee.renter_availability_filter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.NumberPicker
import com.cardee.R
import com.cardee.util.DateStringDelegate
import kotlinx.android.synthetic.main.view_daily_availability.view.*
import java.util.*


class DailyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), FilterViewContract {

    private val adapter = CalendarAdapter()
    private val timePattern = Regex(", \\d{1,2}[ap]m$")
    private val delegate: DateStringDelegate = DateStringDelegate(context)
    private val timeValues = context.resources.getStringArray(R.array.availability_time_titles)
    private var finishCallback: (Boolean) -> Unit = {}
    private val toggle: (v: View) -> Unit = { toggleState() }
    private var presenter: AvailabilityFilterPresenter? = null
    private val doOnSave = { finishCallback.invoke(true) }
    private val onTimePicked: (NumberPicker?, Int) -> Unit = { _, _ ->
        val pickupPos = pickupTimePicker.value
        val returnPos = returnTimePicker.value
        changeDailyTiming(timeValues[pickupPos], timeValues[returnPos])
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
        backTitle.setOnClickListener(toggle)
        setTimeIcon.setOnClickListener(toggle)
        backTitleIcon.setOnClickListener(toggle)
        btnSave.setOnClickListener { saveFilter(doOnSave) }
        btnReset.setOnClickListener { resetFilter(doOnSave) }
        initNumberPickers()
    }

    override fun setPresenter(presenter: AvailabilityFilterPresenter) {
        this.presenter = presenter
        this.presenter?.getFilter()
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
        returnTimePicker.displayedValues = timeValues
        returnTimePicker.minValue = 0
        returnTimePicker.maxValue = timeValues.size - 1
        setDividerColor(pickupTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
        setDividerColor(returnTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
        pickupTimePicker.setOnScrollListener(onTimePicked)
        returnTimePicker.setOnScrollListener(onTimePicked)
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
        presenter?.saveFilter(false)
        doOnSave.invoke()
    }

    override fun resetFilter(doOnReset: () -> Unit) {
        presenter?.resetFilter()
        doOnReset.invoke()
    }

    private fun changeDailyRange(begin: Date?, end: Date?) {
        val dateFromString = if (begin == null) "-" else delegate.getTimeTitle(begin)
        val dateToString = if (begin == null) "-" else delegate.getTimeTitle(end)
        dateFrom.text = dateFromString
        dateTo.text = dateToString
        presenter?.setDailyFilter(begin, end)
    }

    private fun changeDailyTiming(pickupTime: String?, returnTime: String?) {
        var timeFrom = dateFrom.text
        if (timeFrom.contains(timePattern)) {
            dateFrom.text = timeFrom.replace(timePattern, ", " + pickupTime!!)
        } else {
            timeFrom = "${timeFrom ?: ""}, ${pickupTime}"
            dateFrom.text = timeFrom
        }
        var timeTo = dateTo.text
        if (timeTo.contains(timePattern)) {
            dateTo.text = timeTo.replace(timePattern, ", " + returnTime!!)
        } else {
            timeTo = "${timeTo ?: ""}, ${returnTime}"
            dateTo.text = timeTo
        }
        presenter?.setDailyFilterTime(pickupTime, returnTime)
    }
}