package com.cardee.data_source

import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import io.reactivex.disposables.Disposable


interface PaymentsDataSource {

    fun getCards(callback: CardsCallback): Disposable

    fun saveCard(request: CardRequest, callback: NoDataCallback): Disposable

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    interface CardsCallback : BaseCallback {
        fun onSuccess(cards: List<CardsResponseBody>)
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}