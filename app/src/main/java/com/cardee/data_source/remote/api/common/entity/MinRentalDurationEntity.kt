package com.cardee.data_source.remote.api.common.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MinRentalDurationEntity(@Expose
                                   @SerializedName("min_rental_duration")
                                   val minRentalDuration: Int)