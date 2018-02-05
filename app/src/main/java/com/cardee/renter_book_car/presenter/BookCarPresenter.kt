package com.cardee.renter_book_car.presenter

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cardee.R
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
import kotlinx.android.synthetic.main.dialog_cost_breakdown.view.*

class BookCarPresenter : BookCarContract.BookCarPresenter {
    private var mView: BookCarContract.BookCarView? = null
    private val getOfferById = GetOfferById()
    private val getCostBreakdown = GetCostBreakdown()
    private var mGetOfferDisposable: Disposable? = null
    private var mGetCostDisposable: Disposable? = null
    private var mCostBreakdown: BookingCost? = null
    private var mCarId: Int? = null

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

        mCarId = offer.carDetails?.carId
        getCost(mCarId ?: return, state)
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

    override fun showCostBreakdown(context: AppCompatActivity, state: BookCarState) {
        if (mCostBreakdown == null) {
            getCost(mCarId ?: return, state)
        }
        val nonPeakCount = (mCostBreakdown ?: return).nonPeakCount
        val nonPeakCost = mCostBreakdown?.nonPeakCost
        val peakCount = mCostBreakdown?.peakCount
        val peakCost = mCostBreakdown?.peakCost
        val delivery = mCostBreakdown?.delivery
        val fee = mCostBreakdown?.fee
        val discount = mCostBreakdown?.discount
        val total = mCostBreakdown?.total


        val builder = AlertDialog.Builder(context)
        val root = context.layoutInflater.inflate(R.layout.dialog_cost_breakdown, null)
        builder.setView(root)
        val dialog = builder.create()
        root.buttonOk.setOnClickListener { dialog.dismiss() }

        if (nonPeakCount == null) {
            root.non_peak_container.visibility = View.GONE
        } else {
            val count = if (state.bookingHourly == true) {
                "$nonPeakCount hour"
            } else {
                "$nonPeakCount day"
            }
            if (nonPeakCount != 1) count.plus("s")

            root.non_peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_non_peak_template), count)
            root.non_peak_amount.text = "$$nonPeakCost"
        }

        if (peakCount == null) {
            root.peak_container.visibility = View.GONE
        } else {
            val count = if (state.bookingHourly == true) {
                "$peakCount hour"
            } else {
                "$peakCount day"
            }
            if (peakCount != 1) count.plus("s")

            root.peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_peak_template), count)
            root.peak_amount.text = "$$peakCost"
        }

        if (delivery == null) {
            root.delivery_container.visibility = View.GONE
        } else {
            root.delivery_amount.text = "$$delivery"
        }

        if (fee == null) {
            root.fee_container.visibility = View.GONE
        } else {
            root.fee_amount.text = "$$fee"
        }

        if (discount == null) {
            root.discount_container.visibility = View.GONE
        } else {
            root.discount_amount.text = "$discount%"
        }

        root.total_amount.text = "$%.2f".format(total)

        dialog.show()
    }

    override fun onDestroy() {
        mView = null
        mGetOfferDisposable?.dispose()
        mGetCostDisposable?.dispose()
        mCostBreakdown = null
    }
}