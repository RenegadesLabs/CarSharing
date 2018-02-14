package com.cardee.owner_credit_balance

import com.cardee.R


interface CreditBalanceParent {

    interface Presenter {

        fun attachView(view: View)

        fun fetchCurrentBalance()

        fun onDestroy()
    }

    interface View {

        fun initState(state: State)

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

enum class State(val titleId: Int) {
    HOME(R.string.title_credit),
    BANK(R.string.title_top_up),
    CARD(R.string.title_top_up),
    HISTORY(R.string.title_transaction_history);
}