package com.cardee.data_source

import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.rx.balance.BankTransfer
import com.cardee.domain.rx.balance.CardPayment
import com.cardee.domain.rx.balance.Transaction
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable


interface PaymentsDataSource {

    fun getCards(callback: CardsCallback): Disposable

    fun saveCard(request: CardRequest, callback: NoDataCallback): Disposable

    fun payCreditWithCard(payment: CardPayment): Single<Boolean>

    fun payCreditWithBankTransfer(transfer: BankTransfer): Single<Boolean>

    fun payDepositWithCard(payment: CardPayment): Single<Boolean>

    fun payDepositWithBankTransfer(transfer: BankTransfer): Single<Boolean>

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    fun getTransactions(): Single<List<Transaction>>

    interface CardsCallback : BaseCallback {
        fun onSuccess(cards: List<CardsResponseBody>)
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}