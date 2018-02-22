package com.cardee.owner_credit_balance.presenter

import com.cardee.domain.rx.Request
import com.cardee.domain.rx.balance.RetrieveTransactionHistory
import com.cardee.domain.rx.balance.Transaction
import com.cardee.owner_credit_balance.BalanceTransactions
import java.lang.ref.WeakReference


class TransactionsPresenter private constructor(
        private val transactionSource: RetrieveTransactionHistory = RetrieveTransactionHistory()) :
        BalanceTransactions.Presenter {

    companion object {

        @Volatile
        private var instance: TransactionsPresenter? = null

        fun useInstance(): TransactionsPresenter {
            if (instance == null) {
                instance = TransactionsPresenter()
            }
            return instance!!
        }
    }

    override fun <T> onTransferSubmit(view: BalanceTransactions.View<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> onCardChargeSubmit(view: BalanceTransactions.View<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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