package com.cardee.domain.profile.usecase

import android.net.Uri
import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.ProfileRepository
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable


class UploadLicenseFront : RxUseCase<UploadLicenseFront.RequestValues, UploadLicenseFront.ResponseValues> {
    private val repository = ProfileRepository

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.saveLicenseFront(values.front, object : ProfileDataSource.NoDataCallback {
            override fun onSuccess() {
                callback.onSuccess(ResponseValues())
            }

            override fun onError(error: Error) {
                callback.onError(error)
            }
        })

    }

    class RequestValues(val front: Uri) : RxUseCase.RequestValues
    class ResponseValues : RxUseCase.ResponseValues
}