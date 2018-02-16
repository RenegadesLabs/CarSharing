package com.cardee.data_source.remote

import com.cardee.CardeeApp
import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.profile.Profile
import com.cardee.data_source.remote.api.profile.request.UploadPhotoRequest
import com.cardee.domain.profile.entity.VerifyAccountState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import java.io.File


class RemoteProfileDataSource : ProfileDataSource {

    private val api = CardeeApp.retrofit.create(Profile::class.java)

    override fun getVerifyAccState(): VerifyAccountState {
        return VerifyAccountState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
    }

    override fun saveIdentityFront(front: File, callback: ProfileDataSource.NoDataCallback): Disposable {
        return api.uploadIdentityPhoto(UploadPhotoRequest(identityFront = front)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<NoDataResponse>() {
                    override fun onSuccess(response: NoDataResponse) {
                        if (response.isSuccessful) {
                            callback.onSuccess()
                            return
                        }
                        handleErrorResponse(callback, response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        callback.onError(Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST))
                    }
                })
    }

    override fun saveIdentityBack(back: File, callback: ProfileDataSource.NoDataCallback): Disposable {
        return api.uploadIdentityPhoto(UploadPhotoRequest(identityBack = back)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<NoDataResponse>() {
                    override fun onSuccess(response: NoDataResponse) {
                        if (response.isSuccessful) {
                            callback.onSuccess()
                            return
                        }
                        handleErrorResponse(callback, response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        callback.onError(Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST))
                    }
                })
    }

    private fun handleErrorResponse(callback: ProfileDataSource.NoDataCallback, response: NoDataResponse) {
        when {
            response.responseCode == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR -> callback.onError(Error(Error.Type.SERVER, "Server error"))
            response.responseCode == BaseResponse.ERROR_CODE_UNAUTHORIZED -> callback.onError(Error(Error.Type.AUTHORIZATION, "Unauthorized"))
            else -> callback.onError(Error(Error.Type.OTHER, "Undefined error"))
        }
    }
}