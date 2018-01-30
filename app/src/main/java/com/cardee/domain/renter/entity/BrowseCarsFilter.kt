package com.cardee.domain.renter.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import com.cardee.BR

class BrowseCarsFilter(vehicleTypeId: Int = 1,
                       var byLocation: Boolean = false,
                       var latitude: Double = 0.0,
                       var longitude: Double = 0.0,
                       var radius: Int = 0, // radius in meters
                       var address: String = "",
                       bookingHourly: Boolean? = null,
                       var rentalPeriodBegin: String? = null,
                       var rentalPeriodEnd: String? = null,
                       var pickupTime: String? = null,
                       var returnTime: String? = null,
                       instantBooking: Boolean = false,
                       curbsideDelivery: Boolean = false,
                       bodyTypeId: Int = 0,
                       transmissionAuto: Boolean = true,
                       transmissionManual: Boolean = true,
                       var minYears: Int = 0,
                       var maxYears: Int = 0,
                       var minPrice: Int = 0,
                       var maxPrice: Int = 0,
                       var favorite: Boolean? = null) : BaseObservable(), Parcelable {

    @get:Bindable
    var vehicleTypeId: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.vehicleTypeId)
        }

    @get:Bindable
    var bookingHourly: Boolean? = null
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
    var bodyTypeId: Int = 0
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

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(vehicleTypeId)
        parcel.writeByte(if (byLocation) 1 else 0)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(radius)
        parcel.writeString(address)
        parcel.writeValue(bookingHourly)
        parcel.writeString(rentalPeriodBegin)
        parcel.writeString(rentalPeriodEnd)
        parcel.writeString(pickupTime)
        parcel.writeString(returnTime)
        parcel.writeByte(if (instantBooking) 1 else 0)
        parcel.writeByte(if (curbsideDelivery) 1 else 0)
        parcel.writeInt(bodyTypeId)
        parcel.writeByte(if (transmissionAuto) 1 else 0)
        parcel.writeByte(if (transmissionManual) 1 else 0)
        parcel.writeInt(minYears)
        parcel.writeInt(maxYears)
        parcel.writeInt(minPrice)
        parcel.writeInt(maxPrice)
        parcel.writeValue(favorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BrowseCarsFilter> {
        override fun createFromParcel(parcel: Parcel): BrowseCarsFilter {
            val temp = BrowseCarsFilter(parcel)
            return temp
        }

        override fun newArray(size: Int): Array<BrowseCarsFilter?> {
            return arrayOfNulls(size)
        }
    }
}
