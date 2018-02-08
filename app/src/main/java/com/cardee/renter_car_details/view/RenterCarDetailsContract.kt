package com.cardee.renter_car_details.view

interface RenterCarDetailsContract {

    interface View {

        fun setCarLocation(location: String)

    }

    interface Presenter {

        fun attachView(view: View)

        fun fetchLocation(callback: (String) -> Unit)

        fun onDestroy()
    }
}
