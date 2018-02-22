package com.cardee.data_source.remote.api.payments

import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
import com.cardee.data_source.remote.api.payments.response.TransactionHistoryResponse
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Payments {

    @GET("payments/cards/")
    fun obtainCards(): Maybe<PaymentCardsResponse>

    @POST("payments/cards/")
    fun saveCard(@Body request: CardRequest): Maybe<NoDataResponse>

    @GET("payments/history/")
    fun obtainTransactions(): Maybe<TransactionHistoryResponse>
}