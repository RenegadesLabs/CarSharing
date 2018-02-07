package com.cardee.renter_car_details.presenter

import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_car_details.RenterCarDetailsContract


class RenterCarDetailsPresenter: RenterCarDetailsContract.Presenter {


    override fun attachView(view: RenterCarDetailsContract.View) {
    }

    override fun fetchLocation(callback: (String) -> Unit) {
    }

    override fun fetchDetailedCar(renterDetailedCar: RenterDetailedCar) {
    }

    override fun onDestroy() {
    }
}