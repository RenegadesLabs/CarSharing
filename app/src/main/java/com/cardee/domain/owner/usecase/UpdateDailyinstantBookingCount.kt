package com.cardee.domain.owner.usecase

import com.cardee.data_source.CarEditDataSource
import com.cardee.data_source.CarEditRepository
import com.cardee.data_source.Error
import com.cardee.domain.UseCase

class UpdateDailyinstantBookingCount(var mRepository: CarEditRepository = CarEditRepository.getInstance()) :
        UseCase<UpdateDailyinstantBookingCount.RequestValues, UpdateDailyinstantBookingCount.ResponseValues> {

    override fun execute(values: RequestValues?, callback: UseCase.Callback<ResponseValues>?) {
        values?.count?.let {
            mRepository.updateInstantBookingDailyCount(values.id, it,
                    object : CarEditDataSource.Callback {
                        override fun onSuccess() {
                            callback?.onSuccess(ResponseValues(true))
                        }

                        override fun onError(error: Error) {
                            callback?.onError(error)
                        }
                    })
        }
    }

    class RequestValues(val id: Int, val count: Int) : UseCase.RequestValues

    class ResponseValues(val isSuccess: Boolean) : UseCase.ResponseValues
}