package com.cardee.renter_car_details

import com.cardee.domain.renter.entity.RenterDetailedCar

interface RenterCarDetailsContract {

    interface View {

        fun setCarLocation(location: String)

    }

    interface Presenter {

        fun attachView(view: View)

        fun fetchLocation(callback: (String) -> Unit)

        fun fetchDetailedCar(renterDetailedCar: RenterDetailedCar)

        fun onDestroy()
    }
}
