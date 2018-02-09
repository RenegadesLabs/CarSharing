package com.cardee.domain.bookings.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import com.cardee.BR
import com.cardee.CardeeApp
import com.cardee.R


class BookCarState(bookingHourly: Boolean? = true,
                   val collectStrings: Array<String> = arrayOf(
                           CardeeApp.context.getString(R.string.self_collect),
                           CardeeApp.context.getString(R.string.self_collect_only)),
                   val counterStrings: Array<String> = arrayOf("0.", "1.", "2.", "3.", "4."),
                   val rentalTermsStrings: Array<String> = arrayOf("Agreed", "Agree"),
                   val noteStrings: Array<String> = arrayOf("Added", "Add"),
                   var timeBegin: String? = null,
                   var timeEnd: String? = null,
                   var timeBeginHourly: String? = null,
                   var timeEndHourly: String? = null,
                   var timeBeginDaily: String? = null,
                   var timeEndDaily: String? = null,
                   var availabilityDaily: Array<String>? = null,
                   var availabilityHourly: Array<String>? = null,
                   var availabilityHourlyBegin: String? = null,
                   var availabilityHourlyEnd: String? = null,
                   val hourlyCurbsideDelivery: ObservableBoolean = ObservableBoolean(),
                   val dailyCurbsideDelivery: ObservableBoolean = ObservableBoolean(),
                   val hourlyInstantBooking: ObservableBoolean = ObservableBoolean(),
                   val dailyInstantBooking: ObservableBoolean = ObservableBoolean(),
                   val collectionPicked: ObservableBoolean = ObservableBoolean(),
                   var latitude: Double? = null,
                   var longitude: Double? = null,
                   var deliveryAddress: String? = null,
                   val promocodeClicked: ObservableBoolean = ObservableBoolean(),
                   val accVerified: ObservableBoolean = ObservableBoolean(),
                   val paymentSelected: ObservableBoolean = ObservableBoolean(),
                   var paymentSource: String = "",
                   var paymentToken: String = "",
                   val acceptCashHourly: ObservableBoolean = ObservableBoolean(),
                   val acceptCashDaily: ObservableBoolean = ObservableBoolean(),
                   val rentalTermsAgreed: ObservableBoolean = ObservableBoolean(),
                   val noteAdded: ObservableBoolean = ObservableBoolean(),
                   var noteText: String? = null,
                   var amountTotal: Float? = null,
                   var amountDiscount: Float? = null

) : BaseObservable() {

    @get:Bindable
    var bookingHourly: Boolean? = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.bookingHourly)
        }

}