package com.cardee.data_source

import com.cardee.data_source.cache.LocalPaymentsDataSource
import com.cardee.data_source.remote.RemotePaymentsDataSource
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class PaymentsRepository : PaymentsDataSource {

    private val remoteSource: RemotePaymentsDataSource = RemotePaymentsDataSource()
    private val localSource: LocalPaymentsDataSource = LocalPaymentsDataSource

    override fun getCards(callback: PaymentsDataSource.CardsCallback): Disposable {
        val disposable = CompositeDisposable()
        disposable.add(localSource.getCards(callback))
        disposable.add(remoteSource.getCards(object : PaymentsDataSource.CardsCallback {
            override fun onSuccess(cards: List<CardsResponseBody>) {
                callback.onSuccess(cards)
                localSource.saveCache(cards)
            }

            override fun onError(error: Error) {
                callback.onError(error)
            }
        }))
        return disposable
    }

    override fun getTransactions(): Single<List<Transaction>> {
        return remoteSource.getTransactions()
    }

}