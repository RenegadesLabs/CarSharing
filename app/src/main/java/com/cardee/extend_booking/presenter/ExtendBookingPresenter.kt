package com.cardee.extend_booking.presenter

import android.content.Intent
import com.cardee.domain.bookings.usecase.ChangeBookingReturnTime
import com.cardee.domain.bookings.usecase.GetFullBookingAvailability
import com.cardee.extend_booking.ExtendBookingContract
import java.util.*


class ExtendBookingPresenter(private val fetchDataUseCase: GetFullBookingAvailability = GetFullBookingAvailability(),
                             private val saveDataUseCase: ChangeBookingReturnTime = ChangeBookingReturnTime())
    : ExtendBookingContract.Presenter {

    private var currentMode = ExtendBookingContract.Mode.DAILY
    private var view: ExtendBookingContract.View? = null
    private var endDate: Date? = null
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
        view?.showProgress(true)
        if (bookingId == -1)
            throw IllegalArgumentException("Illegal bookingId value: -1")
        val hourly = currentMode == ExtendBookingContract.Mode.HOURLY
        fetchDataUseCase.execute(GetFullBookingAvailability.BookingAvailabilityRequest(bookingId, hourly), { response ->
            endDate = response.body?.timeEnd
            view?.showProgress(false)
            if (response.success) {
                when (currentMode) {
                    ExtendBookingContract.Mode.HOURLY -> view?.bindHourly(response.body!!)
                    ExtendBookingContract.Mode.DAILY -> view?.bindDaily(response.body!!)
                }
                return@execute
            }
            view?.showMessage(response.errorMessage)
        }, { error ->
            view?.let { view ->
                view.showProgress(false)
                view.showMessage(error.message)
            }
        })
    }

    override fun save(extension: Date) {
        endDate?.let { base ->
            view?.showProgress(true)
            val request = ChangeBookingReturnTime.ExtensionRequest(bookingId, extension)
            saveDataUseCase.execute(request, { response ->
                view?.let { view ->
                    view.showProgress(false)
                    view.onFinish()
                }
            }, { error ->
                view?.let { view ->
                    view.showProgress(false)
                    view.showMessage(error.message)
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
        fetchDataUseCase.stop()
    }
}