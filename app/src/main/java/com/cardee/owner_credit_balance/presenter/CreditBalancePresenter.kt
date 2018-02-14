package com.cardee.owner_credit_balance.presenter

import com.cardee.owner_credit_balance.CreditBalanceParent
import com.cardee.owner_credit_balance.State
import java.lang.ref.WeakReference


class CreditBalancePresenter : CreditBalanceParent.Presenter {

    lateinit var weakView: WeakReference<CreditBalanceParent.View>

    override fun attachView(view: CreditBalanceParent.View) {
        weakView = WeakReference(view)
        weakView.get()?.initState(State.HOME)
    }

    override fun fetchCurrentBalance() {

    }

    override fun onDestroy() {
        weakView.clear()
    }

}