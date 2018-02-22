package com.cardee.data_source.remote

import com.cardee.CardeeApp
import com.cardee.data_source.Error
import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.payments.Payments
import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers


class RemotePaymentsDataSource : PaymentsDataSource {

    private val api = CardeeApp.retrofit.create(Payments::class.java)

    override fun getCards(callback: PaymentsDataSource.CardsCallback): Disposable {
        return api.obtainCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<PaymentCardsResponse>() {
                    override fun onSuccess(response: PaymentCardsResponse) {
                        if (response.isSuccessful) {
                            callback.onSuccess(response.cardsResponse)
                            return
                        }
                        handleErrorResponse(callback, response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        callback.onError(Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST))
                    }
                })
    }

    override fun saveCard(request: CardRequest, callback: PaymentsDataSource.NoDataCallback): Disposable {
        return api.saveCard(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<NoDataResponse>() {
                    override fun onSuccess(response: NoDataResponse) {
                        if (response.isSuccessful) {
                            callback.onSuccess()
                            return
                        }
                        handleErrorResponse(callback, response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        if (e.message == "HTTP 500 INTERNAL SERVER ERROR") {
                            callback.onError(Error(Error.Type.INTERNAL, Error.Message.INVALID_CARD))
                            return
                        }
                        callback.onError(Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST))
                    }
                })
    }

    override fun getTransactions(): Single<List<Transaction>> {
        return api.obtainTransactions().toSingle().flatMap { response -> Single.just(response.transactions) }
    }

    private fun handleErrorResponse(callback: PaymentsDataSource.BaseCallback, response: BaseResponse) {
        when {
            response.responseCode == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR -> callback.onError(Error(Error.Type.SERVER, "Server error"))
            response.responseCode == BaseResponse.ERROR_CODE_UNAUTHORIZED -> callback.onError(Error(Error.Type.AUTHORIZATION, "Unauthorized"))
            else -> callback.onError(Error(Error.Type.OTHER, "Undefined error"))
        }
    }
}