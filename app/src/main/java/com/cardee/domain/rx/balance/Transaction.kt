package com.cardee.domain.rx.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Transaction(@Expose @SerializedName("type_transaction") val type: Type?,
                       @Expose @SerializedName("state_transaction") val state: State?,
                       @Expose @SerializedName("transaction_title") val title: String?,
                       @Expose @SerializedName("transaction_object") val number: String?,
                       @Expose @SerializedName("date_created") val date: String?,
                       @Expose @SerializedName("amnt_usd") val amount: Long?,
                       @Expose @SerializedName("amnt_fee_usd") val fee: Long?,
                       @Expose @SerializedName("transaction_id") val id: Int?) {

    data class Type(@Expose @SerializedName("type_transaction_id") val id: Int?,
                    @Expose @SerializedName("type_transaction_name") val typeName: String?)

    data class State(@Expose @SerializedName("state_transaction_name") val id: String?,
                     @Expose @SerializedName("state_transaction_id") val stateName: Int?)
}
