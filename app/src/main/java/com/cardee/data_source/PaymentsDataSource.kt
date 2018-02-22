package com.cardee.data_source

import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable


interface PaymentsDataSource {

    fun getCards(callback: CardsCallback): Disposable

    fun getTransactions(): Single<List<Transaction>>

    interface CardsCallback : BaseCallback {
        fun onSuccess(cards: List<CardsResponseBody>)
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}