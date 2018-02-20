package com.cardee.data_source

import android.net.Uri
import com.cardee.data_source.cache.LocalProfileDataSource
import com.cardee.data_source.remote.RemoteProfileDataSource
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.disposables.Disposable


object ProfileRepository : ProfileDataSource {

    private val localDataSource: ProfileDataSource = LocalProfileDataSource
    private val remoteDataSource: ProfileDataSource = RemoteProfileDataSource()

    override fun getVerifyAccState(): VerifyAccountState {
        return localDataSource.getVerifyAccState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
        localDataSource.saveVerifyAccState(state)
    }

    override fun saveIdentityFront(front: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveIdentityFront(front, callback)
    }

    override fun saveIdentityBack(back: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveIdentityFront(back, callback)
    }

}