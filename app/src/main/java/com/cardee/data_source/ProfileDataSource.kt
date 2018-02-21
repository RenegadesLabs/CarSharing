package com.cardee.data_source

import android.net.Uri
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.disposables.Disposable


interface ProfileDataSource {

    fun getVerifyAccState(): VerifyAccountState

    fun saveVerifyAccState(state: VerifyAccountState)

    fun saveIdentityPhotos(front: Uri, back: Uri, callback: NoDataCallback): Disposable

    fun saveLicensePhotos(front: Uri, back: Uri, callback: NoDataCallback): Disposable

    fun saveProfilePhoto(photoUri: Uri, callback: NoDataCallback): Disposable

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}