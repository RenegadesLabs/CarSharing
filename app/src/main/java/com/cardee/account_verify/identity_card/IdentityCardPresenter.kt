package com.cardee.account_verify.identity_card

import android.net.Uri
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadIdentityFrontPhoto
import com.cardee.domain.profile.usecase.UploadidentityBackPhoto
import io.reactivex.disposables.Disposable

class IdentityCardPresenter(var view: IdentityCardView?) {

    private val uploadFrontUseCase = UploadIdentityFrontPhoto()
    private val uploadBackUseCase = UploadidentityBackPhoto()
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private var frontDisposable: Disposable? = null
    private var backDisposable: Disposable? = null
    private var identityAdded: Boolean = false

    fun setFrontImage(frontUri: Uri) {
        if (frontDisposable?.isDisposed == false) {
            frontDisposable?.dispose()
        }

        frontDisposable = uploadFrontUseCase.execute(UploadIdentityFrontPhoto.RequestValues(frontUri),
                object : RxUseCase.Callback<UploadIdentityFrontPhoto.ResponseValues> {
                    override fun onSuccess(response: UploadIdentityFrontPhoto.ResponseValues) {
                        view?.setFrontPhoto(frontUri)
                        identityAdded = true
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    fun setBackImage(backUri: Uri) {
        if (backDisposable?.isDisposed == false) {
            backDisposable?.dispose()
        }

        backDisposable = uploadBackUseCase.execute(UploadidentityBackPhoto.RequestValues(backUri),
                object : RxUseCase.Callback<UploadidentityBackPhoto.ResponseValues> {
                    override fun onSuccess(response: UploadidentityBackPhoto.ResponseValues) {
                        view?.setBackPhoto(backUri)
                        identityAdded = true
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun onDestroy() {
        view = null
    }

    fun isIdentityAdded(): Boolean {
        return identityAdded
    }
}
