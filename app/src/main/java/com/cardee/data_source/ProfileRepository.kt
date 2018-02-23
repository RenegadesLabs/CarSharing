package com.cardee.data_source

import android.net.Uri
import com.cardee.data_source.cache.LocalProfileDataSource
import com.cardee.data_source.remote.RemoteProfileDataSource
import com.cardee.data_source.remote.api.profile.request.UploadParticularsRequest
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

    override fun saveIdentityPhotos(front: Uri, back: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveIdentityPhotos(front, back, callback)
    }

    override fun saveLicensePhotos(front: Uri, back: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveLicensePhotos(front, back, callback)
    }

    override fun saveProfilePhoto(photoUri: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveProfilePhoto(photoUri, callback)
    }

    override fun saveParticulars(particulars: UploadParticularsRequest, callback: ProfileDataSource.NoDataCallback): Disposable {
        return remoteDataSource.saveParticulars(particulars, callback)
    }

    override fun getVerifyDetails(callback: ProfileDataSource.VerifyCallback): Disposable {
        return remoteDataSource.getVerifyDetails(callback)
    }
}