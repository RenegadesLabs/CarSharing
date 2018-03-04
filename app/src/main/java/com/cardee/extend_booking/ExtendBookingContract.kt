package com.cardee.extend_booking

import android.content.Intent
import android.support.annotation.StringRes
import com.cardee.R
import com.cardee.mvp.BaseView


interface ExtendBookingContract {

    companion object {
        const val MODE: String = "calendar_view_mode"
        const val ID: String = "booking_id"
    }

    enum class Mode(val titleId: Int) {
        @StringRes
        DAILY(R.string.extend_daily_booking_title),
        @StringRes
        HOURLY(R.string.extend_hourly_booking_title);
    }

    interface View : BaseView {

        fun onDataReady()

        fun onInitMode(mode: Mode)

        fun onFinish()

    }

    interface Presenter {

        fun onAttachView(view: View)

        fun init(intent: Intent)

        fun save()

        fun onDestroy()
    }
}