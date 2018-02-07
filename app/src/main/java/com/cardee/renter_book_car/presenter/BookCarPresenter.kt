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
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.usecase.GetBookState
import com.cardee.domain.renter.usecase.GetFilter
import com.cardee.domain.renter.usecase.GetOfferById
import com.cardee.domain.renter.usecase.SaveBookState
import com.cardee.renter_book_car.BookCarContract
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_cost_breakdown.view.*

class BookCarPresenter : BookCarContract.BookCarPresenter {

    private var mView: BookCarContract.BookCarView? = null
    private val getOfferById = GetOfferById()
    private val getCostBreakdown = GetCostBreakdown()
    private val getFilter = GetFilter()
    private val getBookState = GetBookState()
    private val saveBookState = SaveBookState()

    private var mGetOfferDisposable: Disposable? = null
    private var mGetCostDisposable: Disposable? = null
    private var mCostBreakdown: BookingCost? = null
    private var mCarId: Int? = null

    override fun init(bookCarView: BookCarContract.BookCarView) {
        mView = bookCarView
    }

    override fun getOffer(id: Int, state: BookCarState) {
        mCarId = id

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
        val filter = getFilter.getFilter()
        state.bookingHourly = getHourly(filter)
        state.timeBegin = filter.rentalPeriodBegin
        state.timeEnd = filter.rentalPeriodEnd

        val carTitle = offer.carDetails?.carTitle
        mView?.setCarTitle(carTitle)
        val carYear = offer.carDetails?.carYear
        mView?.setCarYear(carYear)
        val hourlyCurb = offer.orderHourlyDetails?.curbsideDelivery
        val dailyCurb = offer.orderDailyDetails?.curbsideDelivery
        state.hourlyCurbsideDelivery.set(hourlyCurb ?: false)
        state.dailyCurbsideDelivery.set(dailyCurb ?: false)
        val hourlyInstant = offer.orderHourlyDetails?.instantBooking
        val dailyInstant = offer.orderDailyDetails?.instantBooking
        state.hourlyInstantBooking.set(hourlyInstant ?: false)
        state.dailyInstantBooking.set(dailyInstant ?: false)
        val acceptCashHourly = offer.orderHourlyDetails?.acceptCash
        val acceptCashDaily = offer.orderDailyDetails?.acceptCash
        state.acceptCashHourly.set(acceptCashHourly ?: false)
        state.acceptCashDaily.set(acceptCashDaily ?: false)

        mView?.resetCost()

        getCost(mCarId ?: return, state)
    }

    private fun getHourly(filter: BrowseCarsFilter): Boolean {
        if (filter.bookingHourly == null) {
            return true
        }
        return filter.bookingHourly == true
    }

    override fun getCost(carId: Int, state: BookCarState) {
        if (mGetCostDisposable?.isDisposed == false) {
            mGetCostDisposable?.dispose()
        }
        var timeBegin = state.timeBegin ?: return
        if (state.bookingHourly == false) timeBegin = timeBegin.dropLast(15)
        var timeEnd = state.timeEnd ?: return
        if (state.bookingHourly == false) timeEnd = timeEnd.dropLast(15)
        var curbDel = if (state.bookingHourly == true) state.hourlyCurbsideDelivery.get() else state.dailyCurbsideDelivery.get()
        if (state.collectionPicked.get().not()) {
            curbDel = false
        }
        val latitude = state.latitude
        val longitude = state.longitude
        val bookingDaily = state.bookingHourly == false

        val request = CostRequest(carId, timeBegin, timeEnd, curbDel, latitude, longitude, bookingDaily)

        mGetCostDisposable = getCostBreakdown.execute(GetCostBreakdown.RequestValues(request), object : RxUseCase.Callback<GetCostBreakdown.ResponseValues> {
            override fun onSuccess(response: GetCostBreakdown.ResponseValues) {
                mCostBreakdown = response.costBreakdown
                val total: Number = mCostBreakdown?.total ?: return
                var totalString: String?
                totalString = if (total.toFloat() % 1f == 0f) {
                    total.toInt().toString()
                } else {
                    "%.2f".format(total.toFloat())
                }

                totalString = if (state.bookingHourly == true) {
                    "$$totalString++"
                } else {
                    "$$totalString"
                }

                mView?.setTotalCost(totalString)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    override fun showCostBreakdown(context: AppCompatActivity, state: BookCarState) {
        getCost(mCarId ?: return, state)

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

        if (nonPeakCount == null || nonPeakCount == 0) {
            root.non_peak_container.visibility = View.GONE
        } else {
            val count = if (state.bookingHourly == true) {
                "$nonPeakCount hour"
            } else {
                "$nonPeakCount day"
            }
            if (nonPeakCount != 1) count.plus("s")

            root.non_peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_non_peak_template), count)
            root.non_peak_amount.text = "$%.2f".format(nonPeakCost)
        }

        if (peakCount == null || peakCount == 0) {
            root.peak_container.visibility = View.GONE
        } else {
            val count = if (state.bookingHourly == true) {
                "$peakCount hour"
            } else {
                "$peakCount day"
            }
            if (peakCount != 1) count.plus("s")

            root.peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_peak_template), count)
            root.peak_amount.text = "$%.2f".format(peakCost)
        }

        if (delivery == null || delivery == 0f) {
            root.delivery_container.visibility = View.GONE
        } else {
            root.delivery_amount.text = "$%.2f".format(delivery)
        }

        if (fee == null || fee == 0f) {
            root.fee_container.visibility = View.GONE
        } else {
            root.fee_amount.text = "$%.2f".format(fee)
        }

        if (discount == null || discount == 0f) {
            root.discount_container.visibility = View.GONE
        } else {
            if (discount % 1f == 0f) {
                root.discount_amount.text = "%.0f%%".format(discount)
            } else {
                root.discount_amount.text = "$discount%"
            }
        }

        root.total_amount.text = "$%.2f".format(total)

        dialog.show()
    }

    override fun getState(): BookCarState {
        return getBookState.getBookState()
    }

    override fun saveSate(state: BookCarState) {
        saveBookState.saveBookState(state)
    }


    override fun onDestroy() {
        mView = null
        mGetOfferDisposable?.dispose()
        mGetCostDisposable?.dispose()
        mCostBreakdown = null
        mCarId = null
    }
}