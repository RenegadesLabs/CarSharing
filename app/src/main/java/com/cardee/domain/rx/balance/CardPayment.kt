package com.cardee.domain.rx.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CardPayment(@Expose @SerializedName("amount") val amount: Long,
                       @Expose @SerializedName("payment_token") val token: String)
