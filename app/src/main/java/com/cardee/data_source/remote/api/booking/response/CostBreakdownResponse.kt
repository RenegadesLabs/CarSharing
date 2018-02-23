package com.cardee.data_source.remote.api.booking.response

import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.booking.response.entity.BookingCost
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CostBreakdownResponse(@Expose @SerializedName("data") val costBreakdown: BookingCost) : BaseResponse()