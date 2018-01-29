package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import kotlinx.android.synthetic.main.view_daily_availability.view.*


class DailyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private val adapter = CalendarAdapter()

    private var finishCallback: (Boolean) -> Unit = {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        calendar.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->
            Log.e("SELECTION", selection.size.toString())
        }
    }

    fun setFinishCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }
}