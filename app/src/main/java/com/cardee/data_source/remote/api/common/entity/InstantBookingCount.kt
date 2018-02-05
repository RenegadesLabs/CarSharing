package com.cardee.data_source.remote.api.common.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InstantBookingCount(@Expose
                               @SerializedName("cnt_hours4instant_booking")
                               var instantBookingCount: Int)
