package com.cardee.owner_credit_balance.presenter

import android.os.Bundle
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.payments.usecase.GetCardsUseCase
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.balance.PerformBankTransaction
import com.cardee.domain.rx.balance.PerformCardTransaction
import com.cardee.domain.rx.balance.RetrieveTransactionHistory
import com.cardee.domain.rx.balance.Transaction
import com.cardee.owner_credit_balance.BalanceTransactions
import java.lang.ref.WeakReference


class TransactionsPresenter private constructor(
        private val transactionSource: RetrieveTransactionHistory = RetrieveTransactionHistory(),
        private val cardsSource: GetCardsUseCase = GetCardsUseCase(),
        private val performCardTransaction: PerformCardTransaction = PerformCardTransaction(),
        private val performBankTransaction: PerformBankTransaction = PerformBankTransaction()) :
        BalanceTransactions.Presenter {

    companion object {

        const val AMOUNT = "card_transfer_amount"
        const val TOKEN = "card_payment_token"
        const val MODE = "credit_deposit"
        const val DATE = "transaction_date"
        const val NUMBER = "transaction_number"

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
        val amount = args.getLong(AMOUNT)
        val isoDate = args.getString(DATE)
        val number = args.getString(NUMBER)
        val mode: BalanceTransactions.Mode? = args.getSerializable(MODE) as BalanceTransactions.Mode

        val resultMessage: String
        val type = when (mode) {
            BalanceTransactions.Mode.CREDIT -> {
                resultMessage = "Top-up successfully enrolled"
                PerformBankTransaction.BankTransferRequest.Type.CREDIT
            }
            BalanceTransactions.Mode.DEPOSIT_BANK -> {
                resultMessage = "Deposit successfully enrolled"
                PerformBankTransaction.BankTransferRequest.Type.DEPOSIT
            }
            BalanceTransactions.Mode.DEPOSIT_CARD -> {
                resultMessage = "Deposit successfully enrolled"
                PerformBankTransaction.BankTransferRequest.Type.DEPOSIT
            }
            else -> throw IllegalStateException("Illegal View mode: $mode")
        }

        view.showProgress(true)
        val weakView = WeakReference(view)
        performBankTransaction.execute(PerformBankTransaction.BankTransferRequest(amount, isoDate, number, type), { result ->
            weakView.get()?.let { view ->
                view.showProgress(false)
                if (result.success) {
                    view.onError(resultMessage)
                    view.onFinish()
                } else {
                    view.onError(result.errorMessage)
                }
            }
        }, { error ->
            weakView.get()?.let { view ->
                view.showProgress(false)
                view.onError(error.message)
            }
        })
    }

    override fun <T> onCardChargeSubmit(view: BalanceTransactions.View<T>, args: Bundle) {
        val amount = args.getLong(AMOUNT)
        val token = args.getString(TOKEN)
        val mode: BalanceTransactions.Mode? = args.getSerializable(MODE) as BalanceTransactions.Mode
        if (validate(view, amount, token).not()) {
            return
        }

        val type = when (mode) {
            BalanceTransactions.Mode.CREDIT -> PerformCardTransaction.CardTransactionRequest.Type.CREDIT
            BalanceTransactions.Mode.DEPOSIT_BANK -> PerformCardTransaction.CardTransactionRequest.Type.DEPOSIT
            BalanceTransactions.Mode.DEPOSIT_CARD -> PerformCardTransaction.CardTransactionRequest.Type.DEPOSIT
            else -> throw IllegalStateException("Illegal View mode: $mode")
        }

        view.showProgress(true)
        val weakView = WeakReference(view)
        performCardTransaction.execute(PerformCardTransaction.CardTransactionRequest(amount, token, type), { result ->
            weakView.get()?.let { view ->
                view.showProgress(false)
                if (result.success) {
                    view.onError("Top-up successfully enrolled")
                    view.onFinish()
                } else {
                    view.onError(result.errorMessage)
                }
            }
        }, { error ->
            weakView.get()?.let { view ->
                view.showProgress(false)
                view.onError(error.message)
            }
        })
    }

    private fun <T> validate(view: BalanceTransactions.View<T>, amount: Long?, token: String?): Boolean {
        if (amount == null || amount == 0L) {
            view.onError("Please enter transaction amount")
            return false
        }
        if (token.isNullOrEmpty()) {
            view.onError("Please select card or add new")
            return false
        }
        return true
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