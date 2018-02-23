package com.cardee.owner_credit_balance

import android.os.Bundle
import com.cardee.R
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.rx.balance.Transaction


interface CreditBalanceParent {

    interface Presenter {

        fun attachView(view: View)

        fun fetchCurrentBalance()

        fun onDestroy()
    }

    interface View {

        fun initState()

        fun onResult(balance: String)
    }
}

interface BalanceTransactions {

    companion object {
        const val SUCCESS: Int = 200
        const val FAILED: Int = 202
    }

    interface Presenter {

        fun <T> onTransferSubmit(view: View<T>, args: Bundle)

        fun <T> onCardChargeSubmit(view: View<T>, args: Bundle)

        fun fetchCards(view: View<List<CardsResponseBody>>)

        fun fetchHistory(view: View<List<Transaction>>)
    }

    interface View<in T> {

        fun onResult(result: T)

        fun showProgress(isShowing: Boolean)

        fun onError(message: String?)

        fun onEmpty()
    }
}

interface ChildListener {

    fun onStateChanged(state: State)

    fun onChangeState(state: State)

    fun onTaskDone()
}

enum class State(val titleId: Int, val tag: String) {
    HOME(R.string.title_credit, "balance_home"),
    BANK(R.string.title_top_up, "bank_transfer"),
    CARD(R.string.title_top_up, "card_transaction"),
    HISTORY(R.string.title_transaction_history, "transaction_history");
}