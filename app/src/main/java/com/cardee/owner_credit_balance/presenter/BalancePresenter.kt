package com.cardee.owner_credit_balance.presenter

import com.cardee.domain.rx.balance.FetchCreditBalance
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.balance.FetchDepositBalance
import com.cardee.owner_credit_balance.BalanceParent
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.view.BaseActionsView
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class BalancePresenter(private val fetchCreditUseCase: FetchCreditBalance = FetchCreditBalance(),
                       private val fetchDepositUseCase: FetchDepositBalance = FetchDepositBalance()) :
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

    override fun fetchCurrentBalance(mode: BalanceTransactions.Mode) {
        fetchCreditUseCase.stop()
        when (mode) {
            BalanceTransactions.Mode.CREDIT -> fetchCredit()
            BalanceTransactions.Mode.DEPOSIT_BANK -> fetchDeposit()
            BalanceTransactions.Mode.DEPOSIT_CARD -> fetchDeposit()
        }
    }

    private fun fetchCredit() {
        fetchCreditUseCase.execute(object : Request {}, { response ->
            if (response.success) {
                weakView.get()?.onResult(balanceFormatter.format(response.body))
            } else {
                showMessage(response.errorMessage)
            }
        }, { throwable ->
            showMessage(throwable.message)
        })
    }

    private fun fetchDeposit() {
        fetchDepositUseCase.execute(object : Request {}, { response ->
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
        fetchCreditUseCase.stop()
        weakView.clear()
    }
}