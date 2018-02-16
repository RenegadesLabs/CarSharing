package com.cardee.account_verify.presenter

import com.cardee.account_verify.view.VerifyAccountView
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState


class VerifyAccountPresenter {

    private var view: VerifyAccountView? = null
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()


    fun init(view: VerifyAccountView) {
        this.view = view
    }

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }


}