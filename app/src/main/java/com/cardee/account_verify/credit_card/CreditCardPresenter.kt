package com.cardee.account_verify.credit_card

import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.payments.usecase.SaveCard
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import io.reactivex.disposables.Disposable


class CreditCardPresenter(var view: CreditCardView?) {

    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private val saveCardUseCase = SaveCard()
    private var disposable: Disposable? = null
    private var creditAdded: Boolean = false

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun saveCard() {
        view?.showProgress(true)
        val state = getState()
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        disposable = saveCardUseCase.execute(
                SaveCard.RequestValues(
                        state.expMonth,
                        state.expYear,
                        state.creditCardNum,
                        state.cvv,
                        state.primaryCard),
                object : RxUseCase.Callback<SaveCard.ResponseValues> {
                    override fun onSuccess(response: SaveCard.ResponseValues) {
                        creditAdded = true
                        view?.showProgress(false)
                        view?.showMessage(CardeeApp.context.getString(R.string.add_card_success))
                        view?.onCardSaved()
                    }

                    override fun onError(error: Error) {
                        creditAdded = false
                        view?.showProgress(false)
                        view?.showMessage(error.message)
                    }
                })
    }

    fun onDestroy() {
        view = null
    }

    fun isCreditAdded(): Boolean {
        return creditAdded
    }
}