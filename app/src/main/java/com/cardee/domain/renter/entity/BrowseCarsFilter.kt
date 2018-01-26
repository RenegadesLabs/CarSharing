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
                       instantBooking: Boolean? = null,
                       curbsideDelivery: Boolean? = null,
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
    var instantBooking: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.instantBooking)
        }

    @get:Bindable
    var curbsideDelivery: Boolean? = null
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
            //TODO("vehicleTypeId"),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
//            TODO("bookingHourly"),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
//            TODO("instantBooking"),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
//            TODO("curbsideDelivery"),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
//            TODO("bodyTypeId"),
            parcel.readInt(),
//            TODO("transmissionAuto"),
            parcel.readByte() != 0.toByte(),
//            TODO("transmissionManual"),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean) {
    }

    //    constructor(parcel: Parcel) : this(
//            parcel.readInt(),
//            parcel.readByte() != 0.toByte(),
//            parcel.readDouble(),
//            parcel.readDouble(),
//            parcel.readInt(),
//            parcel.readString(),
//            if (parcel.readByte() == (-1).toByte()) null else {
//                parcel.readByte() != 0.toByte()
//            },
//            if (parcel.readByte() == (-1).toByte()) null else {
//                parcel.readByte() != 0.toByte()
//            },
//            if (parcel.readByte() == (-1).toByte()) null else {
//                parcel.readByte() != 0.toByte()
//            },
//            parcel.readInt(),
//            parcel.readByte() != 0.toByte(),
//            parcel.readByte() != 0.toByte(),
//            parcel.readInt(),
//            parcel.readInt(),
//            parcel.readInt(),
//            parcel.readInt(),
//            if (parcel.readByte() == (-1).toByte()) null else {
//                parcel.readByte() != 0.toByte()
//            })
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(vehicleTypeId)
//        parcel.writeByte(if (byLocation) 1 else 0)
//        parcel.writeDouble(latitude)
//        parcel.writeDouble(longitude)
//        parcel.writeInt(radius)
//        parcel.writeString(address)
//        if (bookingHourly == null) {
//            parcel.writeByte(-1)
//        } else {
//            parcel.writeByte(if (bookingHourly ?: return) 1 else 0)
//        }
//        if (instantBooking == null) {
//            parcel.writeByte(-1)
//        } else {
//            parcel.writeByte(if (instantBooking ?: return) 1 else 0)
//        }
//        if (curbsideDelivery == null) {
//            parcel.writeByte(-1)
//        } else {
//            parcel.writeByte(if (curbsideDelivery ?: return) 1 else 0)
//        }
//        parcel.writeInt(bodyTypeId)
//        parcel.writeByte(if (transmissionAuto) 1 else 0)
//        parcel.writeByte(if (transmissionManual) 1 else 0)
//        parcel.writeInt(minYears)
//        parcel.writeInt(maxYears)
//        parcel.writeInt(minPrice)
//        parcel.writeInt(maxPrice)
//        if (favorite == null) {
//            parcel.writeByte(-1)
//        } else {
//            parcel.writeByte(if (favorite ?: return) 1 else 0)
//        }
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(vehicleTypeId)
        parcel.writeByte(if (byLocation) 1 else 0)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(radius)
        parcel.writeString(address)
        parcel.writeValue(bookingHourly)
        parcel.writeValue(instantBooking)
        parcel.writeValue(curbsideDelivery)
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
            return BrowseCarsFilter(parcel)
        }

        override fun newArray(size: Int): Array<BrowseCarsFilter?> {
            return arrayOfNulls(size)
        }
    }
}
