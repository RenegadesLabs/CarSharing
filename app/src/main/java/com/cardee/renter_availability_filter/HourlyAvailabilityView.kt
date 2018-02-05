package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.cardee.util.AvailabilityFromFilterDelegate
import com.cardee.util.DateStringDelegate
import kotlinx.android.synthetic.main.view_daily_availability.view.*
import kotlinx.android.synthetic.main.view_hourly_availability.view.*
import java.util.*


class HourlyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), FilterViewContract {

    private var finishCallback: (Boolean) -> Unit = {}
    private val delegate: AvailabilityFromFilterDelegate = AvailabilityFromFilterDelegate()
    private val adapter: TimePickerAdapter = TimePickerAdapter()
    private var presenter: AvailabilityFilterPresenter? = null
    private val doOnSave = { finishCallback.invoke(true) }
    private val doOnReset = { clearSelection() }

    override fun onFinishInflate() {
        super.onFinishInflate()
        timePicker.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->
            if (selection.isEmpty()) {
                changeHourlyRange(null, null)
            } else {
                changeHourlyRange(selection[0], selection[selection.size - 1])
            }
        }
        btnHourSave.setOnClickListener { saveFilter(doOnSave) }
        btnHourReset.setOnClickListener { resetFilter(doOnReset) }
    }

    override fun setPresenter(presenter: AvailabilityFilterPresenter) {
        this.presenter = presenter
        this.presenter?.getFilter { filter ->
            filter.bookingHourly?.let { hourly ->
                if (hourly) {
                    if (filter.rentalPeriodBegin != null && filter.rentalPeriodEnd != null) {
                        delegate.onInitPickerSelection(adapter, filter.rentalPeriodBegin!!, filter.rentalPeriodEnd!!)
                    }
                    delegate.onSetTitlesFromFilter(dateHourFrom, dateHourTo, filter)
                    delegate.onSetSubmitTitle(btnHourSave, filter, true)
                }
            }
        }
    }

    override fun setCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }

    override fun saveFilter(doOnSave: () -> Unit) {
        presenter?.saveFilter(true, doOnSave)
    }

    override fun resetFilter(doOnReset: () -> Unit) {
        presenter?.resetFilter(doOnReset)
    }

    private fun clearSelection() {
        timePicker.reset()
    }

    fun reset() {
        resetFilter(doOnReset)
    }

    fun configureSingleAction() {
        btnHourReset.visibility = View.GONE
    }

    private fun changeHourlyRange(begin: Date?, end: Date?) {
        presenter?.setHourlyFilter(begin, end)
        delegate.onSetTitleFromTime(dateHourFrom, begin)
        delegate.onSetTitleFromTime(dateHourTo, end, false)
        presenter?.doUpdate { filter ->
            delegate.onSetSubmitTitle(btnHourSave, filter, true)
        }
    }
}