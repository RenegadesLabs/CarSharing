package com.cardee.data_source.remote

import android.net.Uri
import android.util.Log
import com.cardee.CardeeApp
import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.profile.Profile
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.util.ImageProcessor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class RemoteProfileDataSource : ProfileDataSource {

    private val api = CardeeApp.retrofit.create(Profile::class.java)
    private val imageProcessor = ImageProcessor()

    companion object {
        val TAG: String = RemoteProfileDataSource::class.java.simpleName
    }

    override fun getVerifyAccState(): VerifyAccountState {
        return VerifyAccountState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
    }

    override fun saveIdentityFront(uri: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        val imageFile = getImageFile(uri, callback) ?: return emptyDisposable()
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("identity_front",
                imageFile.name, RequestBody.create(MediaType.parse("application/octet-stream"),
                imageFile))
        return api.uploadIdentityPhoto(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<NoDataResponse>() {
                    override fun onSuccess(response: NoDataResponse) {
                        imageFile.deleteOnExit()
                        if (response.isSuccessful) {
                            callback.onSuccess()
                            return
                        }
                        handleErrorResponse(callback, response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        imageFile.deleteOnExit()
                        callback.onError(Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST))
                    }
                })
    }

    override fun saveIdentityBack(uri: Uri, callback: ProfileDataSource.NoDataCallback): Disposable {
        val imageFile = getImageFile(uri, callback) ?: return emptyDisposable()
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("identity_back",
                imageFile.name, RequestBody.create(MediaType.parse("application/octet-stream"),
                imageFile))
        return api.uploadIdentityPhoto(body).subscribeOn(Schedulers.io())
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

    private fun getImageFile(uri: Uri, callback: ProfileDataSource.NoDataCallback): File? {
        val split = uri.path.split("/")
        val imageFile = File(CardeeApp.context.cacheDir, split.last())
        try {
            val inStream = CardeeApp.context.contentResolver.openInputStream(uri)
            val resized = imageProcessor.resize(inStream, imageFile)
            if (!resized) {
                throw Exception("Failed to resize image")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            callback.onError(Error(Error.Type.INTERNAL, e.message ?: ""))
        }
        if (imageFile.exists()) {
            return imageFile
        } else {
            callback.onError(Error(Error.Type.INVALID_REQUEST, "Invalid File path: " + imageFile.absolutePath))
        }
        return null
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

    private fun handleErrorResponse(callback: ProfileDataSource.NoDataCallback, response: NoDataResponse) {
        when {
            response.responseCode == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR -> callback.onError(Error(Error.Type.SERVER, "Server error"))
            response.responseCode == BaseResponse.ERROR_CODE_UNAUTHORIZED -> callback.onError(Error(Error.Type.AUTHORIZATION, "Unauthorized"))
            else -> callback.onError(Error(Error.Type.OTHER, "Undefined error"))
        }
    }
}