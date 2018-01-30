package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.cardee.util.DateStringDelegate
import kotlinx.android.synthetic.main.view_hourly_availability.view.*
import java.util.*


class HourlyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), FilterViewContract {

    private var finishCallback: (Boolean) -> Unit = {}
    private val delegate: DateStringDelegate = DateStringDelegate(context)
    private val adapter: TimePickerAdapter = TimePickerAdapter()
    private var presenter: AvailabilityFilterPresenter? = null
    private val doOnSave = { finishCallback.invoke(true) }

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
        btnHourReset.setOnClickListener { resetFilter(doOnSave) }
    }

    override fun setPresenter(presenter: AvailabilityFilterPresenter) {
        this.presenter = presenter
        this.presenter?.getFilter()
    }

    override fun setCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }

    override fun saveFilter(doOnSave: () -> Unit) {
        presenter?.saveFilter(true)
        doOnSave.invoke()
    }

    override fun resetFilter(doOnReset: () -> Unit) {
        presenter?.resetFilter()
        doOnReset.invoke()
    }

    private fun changeHourlyRange(begin: Date?, end: Date?) {
        val dateFromString = if (begin == null) "-" else delegate.getTimeLongTitle(begin)
        val dateToString = if (begin == null) "-" else delegate.getTimeLongTitle(end)
        dateHourFrom.text = dateFromString
        dateHourTo.text = dateToString
        presenter?.setHourlyFilter(begin, end)
    }
}