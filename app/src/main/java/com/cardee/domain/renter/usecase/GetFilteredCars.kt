package com.cardee.domain.renter.usecase

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

    }

    class RequestValues(val filter: FilterRequest) : UseCase.RequestValues
    class ResponseValues(val cars: Array<OfferResponseBody>) : UseCase.ResponseValues
}