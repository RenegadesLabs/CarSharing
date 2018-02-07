package com.cardee.data_source.remote.api.payments

import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
import io.reactivex.Maybe
import retrofit2.http.GET


interface Payments {

    @GET("payments/cards/")
    fun obtainCards(): Maybe<PaymentCardsResponse>
}