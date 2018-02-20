package com.cardee.account_verify.license

import android.net.Uri
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadLicenseBack
import com.cardee.domain.profile.usecase.UploadLicenseFront
import io.reactivex.disposables.Disposable

class LicensePresenter(var view: LicenseView?) {
    private val uploadFrontUseCase = UploadLicenseFront()
    private val uploadBackUseCase = UploadLicenseBack()
    private var frontDisposable: Disposable? = null
    private var backDisposable: Disposable? = null
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private var licenseAdded: Boolean = false

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun setFrontImage(frontUri: Uri) {
        if (frontDisposable?.isDisposed == false) {
            frontDisposable?.dispose()
        }

        frontDisposable = uploadFrontUseCase.execute(UploadLicenseFront.RequestValues(frontUri),
                object : RxUseCase.Callback<UploadLicenseFront.ResponseValues> {
                    override fun onSuccess(response: UploadLicenseFront.ResponseValues) {
                        view?.setFrontPhoto(frontUri)
                        licenseAdded = true
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

        backDisposable = uploadBackUseCase.execute(UploadLicenseBack.RequestValues(backUri),
                object : RxUseCase.Callback<UploadLicenseBack.ResponseValues> {
                    override fun onSuccess(response: UploadLicenseBack.ResponseValues) {
                        view?.setBackPhoto(backUri)
                        licenseAdded = true
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    fun onDestroy() {
        view = null
    }

    fun isLicenseAdded(): Boolean {
        return licenseAdded
    }
}
