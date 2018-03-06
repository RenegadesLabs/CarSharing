package com.cardee.renter_bookings.presenter

import android.support.v4.app.FragmentActivity
import com.cardee.data_source.Error
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.bookings.BookingState
import com.cardee.domain.bookings.entity.Booking
import com.cardee.domain.bookings.usecase.ObtainBookings
import com.cardee.owner_bookings.OwnerBookingListContract
import com.cardee.settings.Settings

class RenterBookingsListPresenter(val view: OwnerBookingListContract.View?, val settings: Settings) : OwnerBookingListContract.Presenter {

    private var bookings: List<Booking>? = null
    private var executor: UseCaseExecutor? = null
    private var obtainBookings: ObtainBookings? = null
    private var filter: BookingState? = null
    private var initialized: Boolean? = null

    init {
        bookings = ArrayList()
        executor = UseCaseExecutor.getInstance()
        obtainBookings = ObtainBookings()
        initialized = false
    }

    private fun obtainBookings(showProgress: Boolean) {
        view?.showProgress(showProgress)
        executor?.execute(obtainBookings, ObtainBookings.RequestValues(ObtainBookings.Strategy.RENTER, filter, null, initialized?.not()
                ?: true),
                object : UseCase.Callback<ObtainBookings.ResponseValues> {
                    override fun onSuccess(response: ObtainBookings.ResponseValues?) {
                        if (bookings?.isEmpty() == true || response?.isUpdated == true) {

                            bookings = when (filter) {
                                null -> response?.bookings?.filter { it.bookingStateType != BookingState.COMPLETED }
                                else -> response?.bookings
                            }

                            view?.apply {
                                showProgress(false)
                                invalidate()
                            }
                        }
                        initialized = true
                    }

                    override fun onError(error: Error?) {
                        view?.showMessage(error?.message)
                        view?.showProgress(false)
                    }
                })
    }

    override fun onDestroy() {
    }

    override fun setSort(sort: ObtainBookings.Sort?) {
    }

    override fun setFilter(filter: BookingState?) {
        initialized = false
        this.filter = filter
        obtainBookings(true)
    }

    override fun showSort(activity: FragmentActivity?) {
    }

    override fun showFilter(activity: FragmentActivity?) {
    }

    override fun onItem(position: Int): Booking? {
        return bookings?.get(position)
    }

    override fun onItemClick(item: Booking?) {
        val bookingId = item?.bookingId
        bookingId
                ?: throw IllegalArgumentException("Invalid bookingId: " + bookingId!!)

        view?.openBooking(bookingId)
    }

    override fun count(): Int {
        return bookings?.size ?: 0
    }

    override fun init() {
        obtainBookings(bookings?.isEmpty() ?: true)
    }

}