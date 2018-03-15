package com.cardee.renter_book_car

import android.support.v7.app.AppCompatActivity
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.mvp.BaseView
import com.cardee.util.DateRepresentationDelegate
import java.util.*


interface BookCarContract {

    interface BookCarView : BaseView {
        fun setCarTitle(carTitle: String?)
        fun setCarYear(carYear: String?)
        fun setTotalCost(total: String)
        fun resetCost()
        fun setRentalPeriod()
        fun onRequestSuccess()
        fun getDateDelegate(): DateRepresentationDelegate
    }

    interface BookCarPresenter {
        fun init(bookCarView: BookCarView)
        fun getOffer(id: Int, state: BookCarState)
        fun onDestroy()
        fun showCostBreakdown(context: AppCompatActivity?, state: BookCarState)
        fun getCost(carId: Int, state: BookCarState, context: AppCompatActivity?)
        fun getState(): BookCarState
        fun saveSate(state: BookCarState)
        fun requestBooking(mState: BookCarState)
        fun getFilter(): BrowseCarsFilter
        fun saveFilter(filter: BrowseCarsFilter)
        fun addOneHour(end: Date?): Date?
    }
}