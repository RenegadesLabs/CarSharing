package com.cardee.data_source.remote.api.payments

import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
import com.cardee.data_source.remote.api.payments.response.TransactionHistoryResponse
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Maybe
import retrofit2.http.GET


interface Payments {

    @GET("payments/cards/")
    fun obtainCards(): Maybe<PaymentCardsResponse>

    @GET("payments/history/")
    fun obtainTransactions(): Maybe<TransactionHistoryResponse>
}