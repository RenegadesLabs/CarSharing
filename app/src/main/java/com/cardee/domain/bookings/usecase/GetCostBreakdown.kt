package com.cardee.domain.bookings.usecase

import com.cardee.data_source.BookingDataSource
import com.cardee.data_source.BookingRepository
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.booking.response.entity.BookingCost
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable


class GetCostBreakdown : RxUseCase<GetCostBreakdown.RequestValues, GetCostBreakdown.ResponseValues> {
    private val repository: BookingRepository = BookingRepository.getInstance()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.getCostBreakdown(values.costRequest, object : BookingDataSource.CostCallback {
            override fun onError(error: Error?) {
                callback.onError(error ?: return)
            }

            override fun onSuccess(bookingCost: BookingCost?) {
                callback.onSuccess(ResponseValues(bookingCost ?: return))
            }
        })
    }

    class RequestValues(val costRequest: CostRequest) : RxUseCase.RequestValues
    class ResponseValues(val costBreakdown: BookingCost) : RxUseCase.ResponseValues
}