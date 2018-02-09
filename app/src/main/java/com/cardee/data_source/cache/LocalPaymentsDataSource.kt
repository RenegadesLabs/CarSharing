package com.cardee.data_source.cache

import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

object LocalPaymentsDataSource : PaymentsDataSource {

    var cardsList: List<CardsResponseBody>? = null

    override fun getCards(callback: PaymentsDataSource.CardsCallback): Disposable {
        return Observable.just(cardsList ?: ArrayList())
                .subscribeWith(object : DisposableObserver<List<CardsResponseBody>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: List<CardsResponseBody>) {
                        callback.onSuccess(cardsList ?: return)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun saveCache(data: List<CardsResponseBody>) {
        cardsList = data
    }
}