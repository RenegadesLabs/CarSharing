package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import kotlinx.android.synthetic.main.view_hourly_availability.view.*


class HourlyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private var finishCallback: (Boolean) -> Unit = {}
    private val adapter: TimePickerAdapter = TimePickerAdapter()

    override fun onFinishInflate() {
        super.onFinishInflate()
        timePicker.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->

        }
    }

    fun setFinishCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }
}