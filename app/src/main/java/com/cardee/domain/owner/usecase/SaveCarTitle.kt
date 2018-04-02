package com.cardee.domain.owner.usecase

import com.cardee.data_source.CarEditDataSource
import com.cardee.data_source.CarEditRepository
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.cars.request.CarTitleEntity
import com.cardee.domain.UseCase

class SaveCarTitle : UseCase<SaveCarTitle.RequestValues, SaveCarTitle.ResponseValues> {

    private val repository = CarEditRepository.getInstance()

    override fun execute(values: RequestValues, callback: UseCase.Callback<ResponseValues>) {
        repository.updateCarTitle(values.id, CarTitleEntity(values.title), object : CarEditDataSource.Callback {
            override fun onSuccess() {
                callback.onSuccess(ResponseValues(true))
            }

            override fun onError(error: Error?) {
                callback.onError(error)
            }
        })
    }

    class RequestValues(val id: Int, val title: String) : UseCase.RequestValues

    class ResponseValues(val isSuccess: Boolean) : UseCase.ResponseValues
}