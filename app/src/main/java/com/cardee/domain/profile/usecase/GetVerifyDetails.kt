package com.cardee.domain.profile.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.ProfileDataSource
import com.cardee.data_source.ProfileRepository
import com.cardee.data_source.remote.api.profile.response.entity.VerifyState
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable

class GetVerifyDetails : RxUseCase<GetVerifyDetails.RequestValues, GetVerifyDetails.ResponseValues> {
    private val repository = ProfileRepository

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.getVerifyDetails(object : ProfileDataSource.VerifyCallback {
            override fun onSuccess(response: VerifyState) {
                callback.onSuccess(ResponseValues(response))
            }

            override fun onError(error: Error) {
                callback.onError(error)
            }
        })
    }

    class RequestValues : RxUseCase.RequestValues
    class ResponseValues(var verifyState: VerifyState) : RxUseCase.ResponseValues
}
