package com.cardee.account_verify.presenter

import com.cardee.R
import com.cardee.account_verify.view.VerifyAccountView
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.GetVerifyDetails
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadParticulars
import io.reactivex.disposables.Disposable


class VerifyAccountPresenter {

    private var view: VerifyAccountView? = null
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private val saveParticularsUseCase = UploadParticulars()
    private val getVerifyState = GetVerifyDetails()
    private var verifyDisposable: Disposable? = null


    fun init(view: VerifyAccountView) {
        this.view = view
    }

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun saveProgress() {
        val state = getState()

        if (state.particularsAdded.get() && state.name.isNotEmpty()) {
            view?.showProgress(true)
            saveParticularsUseCase.execute(
                    UploadParticulars.RequestValues(
                            state.name,
                            state.phone,
                            state.birthDate,
                            state.address,
                            state.identityCard,
                            state.licenseDate),
                    object : RxUseCase.Callback<UploadParticulars.ResponseValues> {
                        override fun onSuccess(response: UploadParticulars.ResponseValues) {
                            view?.showProgress(false)
                            view?.showMessage(R.string.progress_saved)
                            view?.onProgressSaved()
                        }

                        override fun onError(error: Error) {
                            view?.showProgress(false)
                            view?.showMessage(error.message)
                        }
                    })
        } else {
            view?.onProgressSaved()
        }
    }

    fun loadVerifyState() {
        if (verifyDisposable?.isDisposed == false) {
            verifyDisposable?.dispose()
        }
        verifyDisposable = getVerifyState.execute(GetVerifyDetails.RequestValues(), object : RxUseCase.Callback<GetVerifyDetails.ResponseValues> {
            override fun onSuccess(response: GetVerifyDetails.ResponseValues) {
                val state = getState()
                val resp = response.verifyState
                state.particularsAdded.set(resp.particularsVerified)
                state.identityAdded.set(resp.identityCardVerified)
                state.licenseAdded.set(resp.driverLicenceVerified)
                state.photoAdded.set(resp.driverPhotoVerified)
                state.creditAdded.set(resp.creditCardVerified)
                state.depositAdded.set(resp.depositVerified)
                saveState(state)
                view?.updateState()
            }

            override fun onError(error: Error) {
                view?.showMessage(error.message)
            }
        })
    }


}