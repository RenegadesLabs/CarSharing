package com.cardee.data_source.remote.api.payments.response

import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.domain.rx.balance.Transaction
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionHistoryResponse(@Expose @SerializedName("data") val transactions: List<Transaction>) :
        BaseResponse()