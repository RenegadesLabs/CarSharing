package com.cardee.renter_browse_cars.filter.view

import com.cardee.domain.renter.entity.OfferCar
import com.cardee.mvp.BaseView

interface FilterView : BaseView {
    fun setButtonText(s: String)
    fun setCars(carList: List<OfferCar>)

}