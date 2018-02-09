package com.cardee.domain.renter.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.RenterCarsDataSource
import com.cardee.data_source.RenterCarsRepository
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable

class GetOfferDistance : RxUseCase<GetOfferDistance.RequestValues, GetOfferDistance.ResponseValues> {
    val repository: RenterCarsRepository = RenterCarsRepository.getInstance()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.getOfferById(values.id, values.lat!!, values.lng!!, object : RenterCarsDataSource.OfferCallback {
            override fun onSuccess(response: OfferByIdResponseBody?) {
                val distance = response?.carDetails?.distance
                val address = response?.carDetails?.address
                callback.onSuccess(ResponseValues(distance, address))
            }

            override fun onError(error: Error?) {
                callback.onError(error ?: return)
            }
        })
    }

    class RequestValues(val id: Int, val lat: Double? = null, val lng: Double? = null) : RxUseCase.RequestValues
    class ResponseValues(val distance: Int?, val address: String?) : RxUseCase.ResponseValues
}
