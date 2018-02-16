package com.cardee.data_source

import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.disposables.Disposable
import java.io.File


interface ProfileDataSource {

    fun getVerifyAccState(): VerifyAccountState

    fun saveVerifyAccState(state: VerifyAccountState)

    fun saveIdentityFront(front: File, callback: NoDataCallback): Disposable

    fun saveIdentityBack(back: File, callback: NoDataCallback): Disposable

    interface NoDataCallback : BaseCallback {
        fun onSuccess()
    }

    interface BaseCallback {
        fun onError(error: Error)
    }
}