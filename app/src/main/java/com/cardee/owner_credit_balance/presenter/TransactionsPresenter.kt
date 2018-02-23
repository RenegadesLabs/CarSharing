package com.cardee.owner_credit_balance.presenter

import android.os.Bundle
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.payments.usecase.GetCardsUseCase
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.balance.RetrieveTransactionHistory
import com.cardee.domain.rx.balance.Transaction
import com.cardee.owner_credit_balance.BalanceTransactions
import java.lang.ref.WeakReference


class TransactionsPresenter private constructor(
        private val transactionSource: RetrieveTransactionHistory = RetrieveTransactionHistory(),
        private val cardsSource: GetCardsUseCase = GetCardsUseCase()) :
        BalanceTransactions.Presenter {

    companion object {

        const val AMOUNT = "card_transfer_amount"
        const val TOKEN = "card_payment_token"

        @Volatile
        private var instance: TransactionsPresenter? = null

        fun useInstance(): TransactionsPresenter {
            if (instance == null) {
                instance = TransactionsPresenter()
            }
            return instance!!
        }
    }

    override fun <T> onTransferSubmit(view: BalanceTransactions.View<T>, args: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> onCardChargeSubmit(view: BalanceTransactions.View<T>, args: Bundle) {

    }

    override fun fetchCards(view: BalanceTransactions.View<List<CardsResponseBody>>) {
        view.showProgress(true)
        val weakView = WeakReference(view)
        cardsSource.execute(GetCardsUseCase.RequestValues(),
                object : RxUseCase.Callback<GetCardsUseCase.ResponseValues> {
                    override fun onSuccess(response: GetCardsUseCase.ResponseValues) {
                        weakView.get()?.let { view ->
                            view.showProgress(false)
                            val cards = response.cards
                            if (cards.isNotEmpty()) {
                                view.onResult(cards)
                            } else {
                                view.onEmpty()
                            }
                        }
                    }

                    override fun onError(error: Error) {
                        weakView.get()?.let { view ->
                            view.showProgress(false)
                            view.onError(error.message)
                        }
                    }

                })
    }

    override fun fetchHistory(view: BalanceTransactions.View<List<Transaction>>) {
        view.showProgress(true)
        val weakView = WeakReference(view)
        transactionSource.execute(object : Request {},
                { response ->
                    weakView.get()?.let { view ->
                        view.showProgress(false)
                        if (response.success) {
                            val transactions = response.body
                            if (transactions != null && transactions.isNotEmpty()) {
                                view.onResult(transactions)
                            } else {
                                view.onEmpty()
                            }
                        } else {
                            view.onError(response.errorMessage)
                        }
                    }
                }, { error ->
            weakView.get()?.let { view ->
                view.showProgress(false)
                view.onError(error.message)
            }
        })
    }
}