package com.cardee.data_source.remote

import com.cardee.CardeeApp
import com.cardee.data_source.Error
import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.payments.Payments
import com.cardee.data_source.remote.api.payments.response.PaymentCardsResponse
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

    private fun handleErrorResponse(callback: PaymentsDataSource.BaseCallback, response: PaymentCardsResponse) {
        when {
            response.responseCode == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR -> callback.onError(Error(Error.Type.SERVER, "Server error"))
            response.responseCode == BaseResponse.ERROR_CODE_UNAUTHORIZED -> callback.onError(Error(Error.Type.AUTHORIZATION, "Unauthorized"))
            else -> callback.onError(Error(Error.Type.OTHER, "Undefined error"))
        }
    }
}