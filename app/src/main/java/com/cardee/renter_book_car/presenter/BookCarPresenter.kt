package com.cardee.renter_book_car.presenter

import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.booking.response.entity.BookingCost
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.RxUseCase
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.domain.bookings.usecase.GetCostBreakdown
import com.cardee.domain.renter.usecase.GetOfferById
import com.cardee.renter_book_car.BookCarContract
import io.reactivex.disposables.Disposable

class BookCarPresenter : BookCarContract.BookCarPresenter {
    private var mView: BookCarContract.BookCarView? = null
    private val getOfferById = GetOfferById()
    private val getCostBreakdown = GetCostBreakdown()
    private var mGetOfferDisposable: Disposable? = null
    private var mGetCostDisposable: Disposable? = null
    private var mCostBreakdown: BookingCost? = null

    override fun init(bookCarView: BookCarContract.BookCarView) {
        mView = bookCarView
    }

    override fun getOffer(id: Int, state: BookCarState) {
        if (mGetOfferDisposable?.isDisposed == false) {
            mGetOfferDisposable?.dispose()
        }
        mGetOfferDisposable = getOfferById.execute(GetOfferById.RequestValues(id), object : RxUseCase.Callback<GetOfferById.ResponseValues> {
            override fun onSuccess(response: GetOfferById.ResponseValues) {
                val offer = response.offer ?: return
                setView(offer, state)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    private fun setView(offer: OfferByIdResponseBody, state: BookCarState) {
        val carTitle = offer.carDetails?.carTitle
        mView?.setCarTitle(carTitle)
        val carYear = offer.carDetails?.carYear
        mView?.setCarYear(carYear)

        val hourlyCurb = offer.orderHourlyDetails?.curbsideDelivery
        val dailyCurb = offer.orderDailyDetails?.curbsideDelivery

        state.hourlyCurbsideDelivery.set(hourlyCurb ?: false)
        state.dailyCurbsideDelivery.set(dailyCurb ?: false)
        mView?.updateState(state)

        val carId = offer.carDetails?.carId
        getCost(carId ?: return, state)
    }

    private fun getCost(carId: Int, state: BookCarState) {
        if (mGetCostDisposable?.isDisposed == false) {
            mGetCostDisposable?.dispose()
        }
        val timeBegin = state.timeBegin ?: return
        val timeEnd = state.timeEnd ?: return
        val curbDel = if (state.bookingHourly == true) state.hourlyCurbsideDelivery.get() else state.dailyCurbsideDelivery.get()
        val latitude = state.latitude
        val longitude = state.longitude
        val bookingDaily = state.bookingHourly == false

        val request = CostRequest(carId, timeBegin, timeEnd, curbDel, latitude, longitude, bookingDaily)

        mGetCostDisposable = getCostBreakdown.execute(GetCostBreakdown.RequestValues(request), object : RxUseCase.Callback<GetCostBreakdown.ResponseValues> {
            override fun onSuccess(response: GetCostBreakdown.ResponseValues) {
                mCostBreakdown = response.costBreakdown
                var total = mCostBreakdown?.total?.toString() ?: return
                total = if (state.bookingHourly == true) {
                    String.format("$$total++")
                } else {
                    String.format("$$total")
                }
                mView?.setTotalCost(total)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })

    }

    override fun onDestroy() {
        mView = null
        mGetOfferDisposable?.dispose()
        mGetCostDisposable?.dispose()
        mCostBreakdown = null
    }
}