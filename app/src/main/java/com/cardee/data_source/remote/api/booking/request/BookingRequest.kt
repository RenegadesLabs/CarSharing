package com.cardee.data_source.remote.api.booking.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BookingRequest(@Expose @SerializedName("car_id") val carId: Int,
                          @Expose @SerializedName("time_begin") val timeBegin: String,
                          @Expose @SerializedName("time_end") val timeEnd: String,
                          @Expose @SerializedName("is_curbside_delivery") val curbsideDelivery: Boolean,
                          @Expose @SerializedName("latitude") val latitude: Double?,
                          @Expose @SerializedName("longitude") val longitude: Double?,
                          @Expose @SerializedName("address_delivery") val addressDelivery: String?,
                          @Expose @SerializedName("payment_source") val paymentSource: String?,
                          @Expose @SerializedName("payment_token") val paymentToken: String?,
                          @Expose @SerializedName("is_booking_by_day") val bookingByDay: Boolean,
                          @Expose @SerializedName("amnt_total") val amntTotal: Float,
                          @Expose @SerializedName("amnt_discount") val amntDiscount: Float,
                          @Expose @SerializedName("remarks") val remarks: String?
)