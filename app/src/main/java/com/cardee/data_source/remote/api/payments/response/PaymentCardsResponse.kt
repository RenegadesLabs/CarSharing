package com.cardee.data_source.remote.api.payments.response

import com.cardee.data_source.remote.api.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PaymentCardsResponse(@Expose @SerializedName("data") val cardsResponse: List<CardsResponseBody>) : BaseResponse()

data class CardsResponseBody(@Expose @SerializedName("exp_month") val expMonth: Int,
                             @Expose @SerializedName("exp_year") val expYear: Int,
                             @Expose @SerializedName("brand") val brand: String,
                             @Expose @SerializedName("card_number") val cardNumber: String,
                             @Expose @SerializedName("payment_token") val paymentToken: String,
                             @Expose @SerializedName("is_default") val default: Boolean
)