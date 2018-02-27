package com.cardee.owner_bookings.car_checklist.presenter

import com.cardee.mvp.BasePresenter
import com.cardee.mvp.BaseView
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy


interface RenterChecklistContract {

    interface View : BaseView {

        fun setPresenter(presenter: Presenter)

        fun onDestroy()

    }

    interface Presenter : BasePresenter, PresentationStrategy.ActionListener {

        fun setView(view: View)

        fun setStrategy(strategy: PresentationStrategy)

        fun getChecklist()

        fun setViewRenterCallbacks(callbacks: RenterChecklistPresenter.View)
    }
}