package com.cardee.domain.renter.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.RenterCarsDataSource
import com.cardee.data_source.RenterCarsRepository
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable


class GetOfferById : RxUseCase<GetOfferById.RequestValues, GetOfferById.ResponseValues> {
    val repository: RenterCarsRepository = RenterCarsRepository.getInstance()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.getOfferById(values.id, object : RenterCarsDataSource.OfferCallback {
            override fun onSuccess(response: OfferByIdResponseBody?) {
                callback.onSuccess(ResponseValues(response))
            }

            override fun onError(error: Error?) {
                callback.onError(error ?: return)
            }
        })
    }

    class RequestValues(val id: Int, val lat: Double? = null, val lng: Double? = null) : RxUseCase.RequestValues
    class ResponseValues(val offer: OfferByIdResponseBody?) : RxUseCase.ResponseValues
}