package com.cardee.domain.owner.usecase

import com.cardee.data_source.CarEditDataSource
import com.cardee.data_source.CarEditRepository
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.common.entity.MinRentalDurationEntity
import com.cardee.domain.UseCase


class UpdateHourlyMinRentDuration : UseCase<UpdateHourlyMinRentDuration.RequestValues, UpdateHourlyMinRentDuration.ResponseValues> {

    private val repository = CarEditRepository.getInstance()

    override fun execute(values: RequestValues, callback: UseCase.Callback<ResponseValues>) {
        repository.updateMinRentHourly(values.carId, MinRentalDurationEntity(values.duration),
                object : CarEditDataSource.Callback {
                    override fun onSuccess() {
                        callback.onSuccess(ResponseValues(true))
                    }

                    override fun onError(error: Error?) {
                        callback.onError(error)
                    }
                })
    }

    class RequestValues(val carId: Int, val duration: Int) : UseCase.RequestValues

    class ResponseValues(val isSuccess: Boolean) : UseCase.ResponseValues
}