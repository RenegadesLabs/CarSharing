package com.cardee.data_source.cache

import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

object LocalPaymentsDataSource : PaymentsDataSource {

    var cardsList: List<CardsResponseBody>? = null

    override fun saveCard(request: CardRequest, callback: PaymentsDataSource.NoDataCallback): Disposable {
        return emptyDisposable()
    }

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

    override fun getTransactions(): Single<List<Transaction>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun saveCache(data: List<CardsResponseBody>) {
        cardsList = data
    }

    private fun emptyDisposable(): Disposable {
        return Observable.just(null).subscribeWith(object : DisposableObserver<Any>() {
            override fun onComplete() {
            }

            override fun onNext(t: Any) {
            }

            override fun onError(e: Throwable) {
            }
        })
    }
}