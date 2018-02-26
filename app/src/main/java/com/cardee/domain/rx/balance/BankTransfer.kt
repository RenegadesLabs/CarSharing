package com.cardee.domain.rx.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BankTransfer(@Expose @SerializedName("transaction_number") val transactionNumber: String,
                        @Expose @SerializedName("transfer_date") val transferDate: String,
                        @Expose @SerializedName("amount") val amount: Long)