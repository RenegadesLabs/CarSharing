package com.cardee.data_source

import com.cardee.data_source.remote.RemotePaymentsDataSource
import io.reactivex.disposables.Disposable


class PaymentsRepository : PaymentsDataSource {

    private val remoteSourse: RemotePaymentsDataSource = RemotePaymentsDataSource()

    override fun getCards(callback: PaymentsDataSource.CardsCallback): Disposable {
        return remoteSourse.getCards(callback)
    }

}