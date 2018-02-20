package com.cardee.account_verify.identity_card

import android.net.Uri
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.usecase.UploadIdentityFrontPhoto
import com.cardee.domain.profile.usecase.UploadidentityBackPhoto
import io.reactivex.disposables.Disposable

class IdentityCardPresenter(var view: IdentityCardView?) {

    private val uploadFrontUseCase = UploadIdentityFrontPhoto()
    private val uploadBackUseCase = UploadidentityBackPhoto()
    private var frontDisposable: Disposable? = null
    private var backDisposable: Disposable? = null

    fun setFrontImage(frontUri: Uri) {
        if (frontDisposable?.isDisposed == false) {
            frontDisposable?.dispose()
        }

        frontDisposable = uploadFrontUseCase.execute(UploadIdentityFrontPhoto.RequestValues(frontUri),
                object : RxUseCase.Callback<UploadIdentityFrontPhoto.ResponseValues> {
                    override fun onSuccess(response: UploadIdentityFrontPhoto.ResponseValues) {
                        view?.setFrontPhoto(frontUri)
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
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    fun onDestroy() {
        view = null
    }
}
