package com.cardee.account_verify.identity_card

import android.net.Uri
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadIdentityPhotos
import io.reactivex.disposables.Disposable

class IdentityCardPresenter(var view: IdentityCardView?) {

    private val uploadPhotosUseCase = UploadIdentityPhotos()
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private var disposable: Disposable? = null
    private var identityAdded: Boolean = false

    fun setImages(frontUri: Uri, backUri: Uri) {
        view?.showProgress(true)
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        disposable = uploadPhotosUseCase.execute(UploadIdentityPhotos.RequestValues(frontUri, backUri),
                object : RxUseCase.Callback<UploadIdentityPhotos.ResponseValues> {
                    override fun onSuccess(response: UploadIdentityPhotos.ResponseValues) {
                        view?.showProgress(false)
                        view?.showMessage(CardeeApp.context.getString(R.string.identity_card_added))
                        identityAdded = true
                        view?.onPhotosUploaded()
                    }

                    override fun onError(error: Error) {
                        view?.showProgress(false)
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
