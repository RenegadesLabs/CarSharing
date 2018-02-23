package com.cardee.owner_credit_balance.presenter

import com.cardee.domain.balance.FetchCreditBalance
import com.cardee.domain.rx.Request
import com.cardee.owner_credit_balance.BalanceParent
import com.cardee.owner_credit_balance.view.BaseActionsView
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CreditBalancePresenter(private val fetchUseCase: FetchCreditBalance = FetchCreditBalance()) :
        BalanceParent.Presenter {

    private lateinit var weakView: WeakReference<BalanceParent.View>
    private var balanceFormatter: DecimalFormat = DecimalFormat("######0.00")

    init {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        balanceFormatter.decimalFormatSymbols = symbols
    }

    override fun attachView(view: BalanceParent.View) {
        weakView = WeakReference(view)
        weakView.get()?.initState()
    }

    override fun fetchCurrentDeposit() {

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