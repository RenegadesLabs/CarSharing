package com.cardee.extend_booking.presenter

import android.content.Intent
import com.cardee.domain.bookings.usecase.ChangeBookingReturnTime
import com.cardee.domain.bookings.usecase.GetFullBookingAvailability
import com.cardee.extend_booking.ExtendBookingContract


class ExtendBookingPresenter(private val fetchDataUseCase: GetFullBookingAvailability = GetFullBookingAvailability(),
                             private val saveDataUseCase: ChangeBookingReturnTime = ChangeBookingReturnTime())
    : ExtendBookingContract.Presenter {

    private var currentMode = ExtendBookingContract.Mode.DAILY
    private var view: ExtendBookingContract.View? = null
    private var bookingId: Int = -1

    override fun onAttachView(view: ExtendBookingContract.View) {
        this.view = view
    }

    override fun init(intent: Intent) {
        intent.extras ?: throw IllegalArgumentException("Intent does not have extras")
        currentMode = intent.extras.run {
            bookingId = getInt(ExtendBookingContract.ID, -1)
            if (containsKey(ExtendBookingContract.MODE)) {
                getSerializable(ExtendBookingContract.MODE) as ExtendBookingContract.Mode
            } else ExtendBookingContract.Mode.DAILY
        }
        view?.onInitMode(currentMode)
    }

    override fun requestData() {
        if (bookingId == -1)
            throw IllegalArgumentException("Illegal bookingId value: -1")


    }

    override fun save() {

    }

    override fun onDestroy() {
        view = null
    }
}