package com.cardee.data_source.remote.api.booking.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BookingExtension(@Expose @SerializedName("time_end") val timeEnd: String)