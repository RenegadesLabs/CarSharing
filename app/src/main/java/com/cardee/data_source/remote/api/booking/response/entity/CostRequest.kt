package com.cardee.data_source.remote.api.booking.response.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CostRequest(@Expose @SerializedName("car_id") var carId: Int,
                       @Expose @SerializedName("time_begin") var timeBegin: String,
                       @Expose @SerializedName("time_end") var timeEnd: String,
                       @Expose @SerializedName("is_curbside_delivery") var curbsideDelivery: Boolean,
                       @Expose @SerializedName("latitude") var latitude: Double?,
                       @Expose @SerializedName("longitude") var longitude: Double?,
                       @Expose @SerializedName("is_booking_by_day") var dailyBooking: Boolean?)
