package com.cardee.domain.renter.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.cardee.BR
import com.cardee.CardeeApp
import com.cardee.R

class BrowseCarsFilter(vehicleTypeId: Int = 0,
                       val vehicleTypeDesc: Array<String> = arrayOf(CardeeApp.context.resources.getString(R.string.vehicle_type_personal_cars_desc),
                               CardeeApp.context.resources.getString(R.string.vehicle_type_private_desc),
                               CardeeApp.context.resources.getString(R.string.vehicle_type_commercial_desc)),
                       bookingHourly: Boolean = false,
                       instantBooking: Boolean = false,
                       curbsideDelivery: Boolean = false,
                       val priceRangeTitles: Array<String> = arrayOf(CardeeApp.context.resources.getString(R.string.price_range_per_hour),
                               CardeeApp.context.resources.getString(R.string.price_range_per_day)),
                       bodyTypeId: Int = 5,
                       transmissionAuto: Boolean = true,
                       transmissionManual: Boolean = true) : BaseObservable() {


    @get:Bindable
    var vehicleTypeId: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.vehicleTypeId)
        }

    @get:Bindable
    var bookingHourly: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.bookingHourly)
        }

    @get:Bindable
    var instantBooking: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.instantBooking)
        }

    @get:Bindable
    var curbsideDelivery: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.curbsideDelivery)
        }

    @get:Bindable
    var bodyTypeId: Int = 5
        set(value) {
            field = value
            notifyPropertyChanged(BR.bodyTypeId)
        }

    @get:Bindable
    var transmissionAuto: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.transmissionAuto)
        }

    @get:Bindable
    var transmissionManual: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.transmissionManual)
        }
}
