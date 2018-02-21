package com.cardee.account_verify.license

import android.net.Uri
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadLicensePhotos
import io.reactivex.disposables.Disposable

class LicensePresenter(var view: LicenseView?) {
    private val uploadPhotosUseCase = UploadLicensePhotos()
    private var disposable: Disposable? = null
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private var licenseAdded: Boolean = false

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun setLicenseImages(frontUri: Uri, backUri: Uri) {
        view?.showProgress(true)
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }

        disposable = uploadPhotosUseCase.execute(UploadLicensePhotos.RequestValues(frontUri, backUri),
                object : RxUseCase.Callback<UploadLicensePhotos.ResponseValues> {
                    override fun onSuccess(response: UploadLicensePhotos.ResponseValues) {
                        view?.showProgress(false)
                        view?.showMessage(CardeeApp.context.getString(R.string.license_added))
                        licenseAdded = true
                        view?.onPhotosUploaded()
                    }

                    override fun onError(error: Error) {
                        view?.showProgress(false)
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
