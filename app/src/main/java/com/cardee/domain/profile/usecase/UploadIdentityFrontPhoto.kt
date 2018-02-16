package com.cardee.domain.profile.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.ProfileRepository
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable
import java.io.File


class UploadIdentityFrontPhoto : RxUseCase<UploadIdentityFrontPhoto.RequestValues, UploadIdentityFrontPhoto.ResponseValues> {

    private val repository = ProfileRepository

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.saveIdentityFront(values.front, object : ProfileDataSource.NoDataCallback {
            override fun onSuccess() {
                callback.onSuccess(ResponseValues())
            }

            override fun onError(error: Error) {
                callback.onError(error)
            }
        })

    }

    class RequestValues(val front: File) : RxUseCase.RequestValues
    class ResponseValues : RxUseCase.ResponseValues
}