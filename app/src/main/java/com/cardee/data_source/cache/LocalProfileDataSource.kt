package com.cardee.data_source.cache

import com.cardee.data_source.ProfileDataSource
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import java.io.File

object LocalProfileDataSource : ProfileDataSource {
    override fun saveIdentityFront(front: File, callback: ProfileDataSource.NoDataCallback): Disposable {
        return Observable.just(null).subscribeWith(object : DisposableObserver<Any>() {
            override fun onNext(t: Any) {

            }

            override fun onComplete() {
            }


            override fun onError(e: Throwable) {

            }
        })
    }

    override fun saveIdentityBack(back: File, callback: ProfileDataSource.NoDataCallback): Disposable {
        return Observable.just(null).subscribeWith(object : DisposableObserver<Any>() {
            override fun onNext(t: Any) {

            }

            override fun onComplete() {
            }


            override fun onError(e: Throwable) {

            }
        })
    }

    private var verifyAccState: VerifyAccountState? = null

    override fun getVerifyAccState(): VerifyAccountState {
        return verifyAccState ?: VerifyAccountState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
        verifyAccState = state
    }

}