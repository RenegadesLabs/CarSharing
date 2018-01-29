package com.cardee.renter_availability_filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet


class HourlyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private var finishCallback: (Boolean) -> Unit = {}

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setFinishCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }
}