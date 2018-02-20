package com.cardee.account_verify.profilePhoto

import android.net.Uri
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.domain.profile.usecase.GetVerifyAccState
import com.cardee.domain.profile.usecase.SaveVerifyAccState
import com.cardee.domain.profile.usecase.UploadProfilePhoto
import io.reactivex.disposables.Disposable


class ProfilePhotoPresenter(var view: ProfilePhotoView?) {

    private val saveProfilePhoto = UploadProfilePhoto()
    private val saveStateUseCase = SaveVerifyAccState()
    private val getStateUseCase = GetVerifyAccState()
    private var savePhotoDisposable: Disposable? = null
    private var photoAdded: Boolean = false

    fun saveState(state: VerifyAccountState) {
        saveStateUseCase.saveVerifyState(state)
    }

    fun getState(): VerifyAccountState {
        return getStateUseCase.getVerifyState()
    }

    fun setProfileImage(uri: Uri) {
        if (savePhotoDisposable?.isDisposed == false) {
            savePhotoDisposable?.dispose()
        }
        savePhotoDisposable = saveProfilePhoto.execute(UploadProfilePhoto.RequestValues(uri),
                object : RxUseCase.Callback<UploadProfilePhoto.ResponseValues> {
                    override fun onSuccess(response: UploadProfilePhoto.ResponseValues) {
                        view?.setPhoto(uri)
                        photoAdded = true
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    fun isPhotoAdded(): Boolean {
        return photoAdded
    }

    fun onDestroy() {
        view = null
    }


}