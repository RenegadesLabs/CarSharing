package com.cardee.owner_credit_balance.presenter

import com.cardee.domain.balance.FetchCreditBalance
import com.cardee.domain.rx.Request
import com.cardee.owner_credit_balance.CreditBalanceParent
import com.cardee.owner_credit_balance.view.BaseActionsView
import java.lang.ref.WeakReference
import java.text.DecimalFormat


class CreditBalancePresenter(val fetchUseCase: FetchCreditBalance = FetchCreditBalance()) : CreditBalanceParent.Presenter {

    private lateinit var weakView: WeakReference<CreditBalanceParent.View>
    private var balanceFormatter: DecimalFormat = DecimalFormat("######0.00")

    override fun attachView(view: CreditBalanceParent.View) {
        weakView = WeakReference(view)
        weakView.get()?.initState()
    }

    override fun fetchCurrentBalance() {
        fetchUseCase.stop()
        fetchUseCase.execute(object : Request {}, { response ->
            if (response.success) {
                weakView.get()?.onResult(balanceFormatter.format(response.body))
            } else {
                showMessage(response.errorMessage)
            }
        }, { throwable ->
            showMessage(throwable.message)
        })
    }

    fun showMessage(message: String?) {
        weakView.get()?.let {
            val view = it as BaseActionsView
            view.showMessage(message ?: return@let)
        }
    }

    override fun onDestroy() {
        fetchUseCase.stop()
        weakView.clear()
    }
}