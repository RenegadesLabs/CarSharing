package com.cardee.domain.bookings.usecase

import com.cardee.data_source.BookingDataSource
import com.cardee.data_source.BookingRepository
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.booking.request.BookingRequest
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable


class RequestBooking : RxUseCase<RequestBooking.RequestValues, RequestBooking.ResponseValues> {

    private val repository: BookingRepository = BookingRepository.getInstance()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.requestBooking(values.request, object : BookingDataSource.SimpleCallback {
            override fun onSuccess() {
                callback.onSuccess(ResponseValues())
            }

            override fun onError(error: Error?) {
                callback.onError(error ?: return)
            }
        })
    }


    class RequestValues(val request: BookingRequest) : RxUseCase.RequestValues
    class ResponseValues : RxUseCase.ResponseValues
}