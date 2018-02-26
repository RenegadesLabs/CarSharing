package com.cardee.data_source.remote.api.payments

import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
import com.cardee.data_source.remote.api.payments.response.TransactionHistoryResponse
import com.cardee.domain.rx.balance.BankTransfer
import com.cardee.domain.rx.balance.CardPayment
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface Payments {

    @GET("payments/cards/")
    fun obtainCards(): Maybe<PaymentCardsResponse>

    @POST("payments/cards/")
    fun saveCard(@Body request: CardRequest): Maybe<NoDataResponse>

    @GET("payments/history/")
    fun obtainTransactions(): Maybe<TransactionHistoryResponse>

    @POST("payments/topup/card/")
    @Headers("Content-Type: application/json")
    fun topUpCard(@Body payment: CardPayment): Maybe<NoDataResponse>

    @POST("payments/topup/bank/")
    @Headers("Content-Type: application/json")
    fun topUpBank(@Body transfer: BankTransfer) : Maybe<NoDataResponse>

    @POST("payments/deposit/card/")
    @Headers("Content-Type: application/json")
    fun depositCard(@Body payment: CardPayment): Maybe<NoDataResponse>

    @POST("payments/deposit/bank/")
    @Headers("Content-Type: application/json")
    fun depositBank(@Body transfer: BankTransfer) : Maybe<NoDataResponse>
}