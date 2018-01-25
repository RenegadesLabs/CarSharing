package com.cardee.data_source.remote.api.offers.response

import com.cardee.data_source.remote.api.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OffersResponse(@Expose @SerializedName("data") val offers: List<Offer>) : BaseResponse()

