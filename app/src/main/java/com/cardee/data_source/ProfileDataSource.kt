package com.cardee.data_source

import android.net.Uri
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.disposables.Disposable
import java.io.File


interface ProfileDataSource {

    fun getVerifyAccState(): VerifyAccountState

    fun saveVerifyAccState(state: VerifyAccountState)

    fun saveIdentityFront(front: Uri, callback: NoDataCallback): Disposable

    fun saveIdentityBack(back: Uri, callback: NoDataCallback): Disposable

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}