package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import kotlinx.android.synthetic.main.view_hourly_availability.view.*
import java.util.*


class HourlyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), FilterViewContract {

    private var finishCallback: (Boolean) -> Unit = {}
    private val adapter: TimePickerAdapter = TimePickerAdapter()
    private var presenter: AvailabilityFilterPresenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        timePicker.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->

        }
    }

    override fun setPresenter(presenter: AvailabilityFilterPresenter) {
        this.presenter = presenter
        this.presenter?.getFilter()
    }

    override fun setCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }

    override fun saveFilter(doOnSave: () -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetFilter(doOnReset: () -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun changeHourlyRange(begin: Date?, end: Date?) {
        presenter?.setHourlyFilter(begin, end)
    }
}