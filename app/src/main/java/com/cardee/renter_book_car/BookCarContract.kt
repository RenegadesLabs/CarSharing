package com.cardee.renter_book_car

import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.mvp.BaseView


interface BookCarContract {

    interface BookCarView : BaseView {
        fun setCarTitle(carTitle: String?)
        fun setCarYear(carYear: String?)
        fun updateState(state: BookCarState)
        fun setTotalCost(total: String)
    }

    interface BookCarPresenter {
        fun init(bookCarView: BookCarView)
        fun getOffer(id: Int, state: BookCarState)
        fun onDestroy()
    }
}