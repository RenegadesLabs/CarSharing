package com.cardee.data_source.remote.api.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CardRequest(@Expose @SerializedName("exp_month") val expMonth: Int,
                       @Expose @SerializedName("exp_year") val expYear: Int,
                       @Expose @SerializedName("number") val number: String,
                       @Expose @SerializedName("cvc") val cvv: Int,
                       @Expose @SerializedName("is_default") val primary: Boolean)