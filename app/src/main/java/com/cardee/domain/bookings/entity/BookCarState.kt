package com.cardee.domain.bookings.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import com.cardee.BR
import com.cardee.CardeeApp
import com.cardee.R


class BookCarState(bookingHourly: Boolean? = true,
                   val collectStrings: Array<String> = arrayOf(
                           CardeeApp.context.getString(R.string.not_selected),
                           CardeeApp.context.getString(R.string.self_collect)),
                   val counterStrings: Array<String> = arrayOf("0.", "1.", "2.", "3.", "4."),
                   val rentalTermsStrings: Array<String> = arrayOf("Agreed", "Agree"),
                   val noteStrings: Array<String> = arrayOf("Added", "Add"),
                   var timeBegin: String? = null,
                   var timeEnd: String? = null,
                   val hourlyCurbsideDelivery: ObservableBoolean = ObservableBoolean(),
                   val dailyCurbsideDelivery: ObservableBoolean = ObservableBoolean(),
                   var latitude: Double? = null,
                   var longitude: Double? = null,
                   val promocodeClicked: ObservableBoolean = ObservableBoolean(),
                   val accVerified: ObservableBoolean = ObservableBoolean(),
                   val paymentSelected: ObservableBoolean = ObservableBoolean(),
                   val rentalTermsAgreed: ObservableBoolean = ObservableBoolean(),
                   val noteAdded: ObservableBoolean = ObservableBoolean()) : BaseObservable() {

    @get:Bindable
    var bookingHourly: Boolean? = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.bookingHourly)
        }

}