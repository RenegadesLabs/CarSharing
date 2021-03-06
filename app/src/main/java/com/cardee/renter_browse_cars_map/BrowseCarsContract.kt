package com.cardee.renter_browse_cars_map

import com.cardee.mvp.BaseView

interface BrowseCarsContract {

    interface View<in T> : BaseView {

        fun bind(offers: List<T>)

        fun openFilter()

        fun toggleFavorites(selected: Boolean)
    }

    interface Presenter {

        fun load()

    }
}
