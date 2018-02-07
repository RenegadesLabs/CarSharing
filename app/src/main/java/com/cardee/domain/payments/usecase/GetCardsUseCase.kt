package com.cardee.domain.payments.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.PaymentsRepository
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable

class GetCardsUseCase : RxUseCase<GetCardsUseCase.RequestValues, GetCardsUseCase.ResponseValues> {
    private val repository = PaymentsRepository()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.getCards(object : PaymentsDataSource.CardsCallback {
            override fun onSuccess(cards: List<CardsResponseBody>) {
                callback.onSuccess(ResponseValues(cards))
            }

            override fun onError(error: Error) {
                callback.onError(error)
            }
        })
    }

    class RequestValues : RxUseCase.RequestValues
    class ResponseValues(val cards: List<CardsResponseBody>) : RxUseCase.ResponseValues
}