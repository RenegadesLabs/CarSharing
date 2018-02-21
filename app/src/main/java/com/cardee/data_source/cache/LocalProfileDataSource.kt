package com.cardee.data_source.cache

import android.net.Uri
import com.cardee.data_source.ProfileDataSource
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

object LocalProfileDataSource : ProfileDataSource {
    override fun saveLicensePhotos(front: Uri, back: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return emptyDisposable()
    }

    override fun saveIdentityPhotos(front: Uri, back: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return emptyDisposable()
    }

    override fun saveProfilePhoto(photoUri: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return emptyDisposable()
    }

    private var verifyAccState: VerifyAccountState? = null

    override fun getVerifyAccState(): VerifyAccountState {
        return verifyAccState ?: VerifyAccountState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
        verifyAccState = state
    }

    private fun emptyDisposable(): Disposable {
        return Observable.just(null).subscribeWith(object : DisposableObserver<Any>() {
            override fun onNext(t: Any) {

            }

            override fun onComplete() {
            }


            override fun onError(e: Throwable) {

            }
        })
    }

}