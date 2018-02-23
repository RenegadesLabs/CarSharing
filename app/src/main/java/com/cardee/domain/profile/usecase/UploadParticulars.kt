package com.cardee.domain.profile.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.ProfileRepository
import com.cardee.data_source.remote.api.profile.request.UploadParticularsRequest
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable

class UploadParticulars : RxUseCase<UploadParticulars.RequestValues, UploadParticulars.ResponseValues> {
    private val repository = ProfileRepository

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.saveParticulars(
                UploadParticularsRequest(
                        values.name,
                        values.phone,
                        values.dateBirth,
                        values.address,
                        values.passport,
                        values.licenseEffectiveDate),
                object : ProfileDataSource.NoDataCallback {
                    override fun onSuccess() {
                        callback.onSuccess(ResponseValues())
                    }

                    override fun onError(error: Error) {
                        callback.onError(error)
                    }
                })
    }

    class RequestValues(val name: String,
                        val phone: String,
                        val dateBirth: String,
                        val address: String,
                        val passport: String,
                        val licenseEffectiveDate: String) : RxUseCase.RequestValues

    class ResponseValues : RxUseCase.ResponseValues
}
