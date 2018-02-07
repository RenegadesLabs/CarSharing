package com.cardee.data_source.remote.api.common.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferCarOwner(@Expose @SerializedName("profile_id") val profileId: Int?,
                         @Expose @SerializedName("name") val name: String?,
                         @Expose @SerializedName("cnt_bookings") val bookings: Int?,
                         @Expose @SerializedName("acceptance") val acceptance: Int?,
                         @Expose @SerializedName("profile_photo") val photo: String?,
                         @Expose @SerializedName("response_time") val responseTime: Int?)