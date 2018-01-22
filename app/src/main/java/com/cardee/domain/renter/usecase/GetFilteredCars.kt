package com.cardee.domain.renter.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.RenterCarsDataSource
import com.cardee.data_source.RenterCarsRepository
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody
import com.cardee.domain.UseCase
import com.cardee.domain.renter.entity.FilterRequest

class GetFilteredCars : UseCase<GetFilteredCars.RequestValues, GetFilteredCars.ResponseValues> {
    val repository: RenterCarsRepository

    init {
        repository = RenterCarsRepository.getInstance()
    }

    override fun execute(values: RequestValues?, callback: UseCase.Callback<ResponseValues>?) {
        repository.obtainCarsByFilter(values?.filter, object : RenterCarsDataSource.Callback {
            override fun onSuccess(response: Array<OfferResponseBody>) {
                callback?.onSuccess(ResponseValues(response))
            }

            override fun onError(error: Error?) {
                callback?.onError(error)
            }
        })
    }

    class RequestValues(val filter: FilterRequest) : UseCase.RequestValues
    class ResponseValues(val cars: Array<OfferResponseBody>) : UseCase.ResponseValues
}