package com.cardee.renter_car_details.view

import com.cardee.renter_car_details.RenterCarDetailsContract


class RenterCarDetailsPresenter : RenterCarDetailsContract.Presenter {

    private var view: RenterCarDetailsContract.View? = null
    private var pendingCallback: ((String) -> Unit)? = null

    override fun attachView(view: RenterCarDetailsContract.View) {
        this.view = view
    }

    override fun fetchLocation(callback: (String) -> Unit) {
        pendingCallback = callback
        pendingCallback?.invoke("")
    }

    override fun onDestroy() {
        view = null
    }
}
