package com.cardee.renter_car_details

import com.cardee.data_source.remote.api.booking.response.entity.BookingCost
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.mvp.BaseView
import com.cardee.util.DateRepresentationDelegate
import com.google.android.gms.maps.model.LatLng

interface RenterCarDetailsContract {

    interface View : BaseView {

        fun setDetailedCar(renterDetailedCar: RenterDetailedCar, hourly: Boolean)

        fun setFavorite(favorite: Boolean)

        fun setLocationString(locationString: String)
        fun getDateReprDelegate(): DateRepresentationDelegate?
        fun onBreakdownFetched(breakdown: BookingCost)

    }

    interface Presenter {

        fun attachView(view: View)

        fun fetchLocation(callback: (LatLng) -> Unit)

        fun fetchDistance(id: Int, lat: Double, lng: Double)

        fun getDetailedCar(carId: Int?, lat: Double? = null, lng: Double? = null)

        fun addCarToFavorites(carId: Int?, favorite: Boolean)

        fun getCachedCar(): RenterDetailedCar?

        fun onDestroy()

        fun getFilter(): BrowseCarsFilter

        fun saveFilter(filter: BrowseCarsFilter)
        fun getCost(carId: Int)
    }
}
