package com.cardee.extend_booking.presenter

import android.content.Intent
import com.cardee.extend_booking.ExtendBookingContract


class ExtendBookingPresenter : ExtendBookingContract.Presenter {

    private var currentMode = ExtendBookingContract.Mode.DAILY
    private var view: ExtendBookingContract.View? = null
    private var bookingId: Int? = null

    override fun onAttachView(view: ExtendBookingContract.View) {
        this.view = view
    }

    override fun init(intent: Intent) {
        intent.extras ?: throw IllegalArgumentException("Intent does not have extras")
        currentMode = intent.extras.run {
            bookingId = getInt(ExtendBookingContract.ID)
            if (containsKey(ExtendBookingContract.MODE)) {
                getSerializable(ExtendBookingContract.MODE) as ExtendBookingContract.Mode
            } else ExtendBookingContract.Mode.DAILY
        }
        view?.onInitMode(currentMode)
    }

    override fun save() {

    }

    override fun onDestroy() {
        view = null
    }
}