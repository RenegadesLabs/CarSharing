package com.cardee.data_source

import android.net.Uri
import com.cardee.data_source.remote.api.profile.request.UploadParticularsRequest
import com.cardee.data_source.remote.api.profile.response.entity.VerifyState
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.disposables.Disposable


interface ProfileDataSource {

    fun getVerifyAccState(): VerifyAccountState

    fun saveVerifyAccState(state: VerifyAccountState)

    fun saveIdentityPhotos(front: Uri, back: Uri, callback: NoDataCallback): Disposable

    fun saveLicensePhotos(front: Uri, back: Uri, callback: NoDataCallback): Disposable

    fun saveProfilePhoto(photoUri: Uri, callback: NoDataCallback): Disposable

    fun saveParticulars(particulars: UploadParticularsRequest, callback: NoDataCallback): Disposable

    fun getVerifyDetails(callback: VerifyCallback): Disposable

    interface VerifyCallback : BaseCallback {
        fun onSuccess(response: VerifyState)
    }

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}