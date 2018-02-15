package com.cardee.owner_credit_balance

import com.cardee.R


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

        fun <T> onTransferSubmit(view: View<T>)

        fun <T> onCardChargeSubmit(view: View<T>)

        fun <T> fetchHistory(view: View<T>)
    }

    interface View<in T> {

        fun getData(data: T)

        fun onResul(result: T)
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