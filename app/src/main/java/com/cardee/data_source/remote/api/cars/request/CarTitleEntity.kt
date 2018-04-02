package com.cardee.data_source.remote.api.cars.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarTitleEntity(@Expose @SerializedName("car_title") val carTitle: String)