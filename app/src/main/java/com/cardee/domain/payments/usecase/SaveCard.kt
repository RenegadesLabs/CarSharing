package com.cardee.domain.payments.usecase

import com.cardee.data_source.Error
import com.cardee.data_source.PaymentsDataSource
import com.cardee.data_source.PaymentsRepository
import com.cardee.data_source.remote.api.payments.request.CardRequest
import com.cardee.domain.RxUseCase
import io.reactivex.disposables.Disposable


class SaveCard : RxUseCase<SaveCard.RequestValues, SaveCard.ResponseValues> {

    private val repository = PaymentsRepository()

    override fun execute(values: RequestValues, callback: RxUseCase.Callback<ResponseValues>): Disposable {
        return repository.saveCard(
                CardRequest(
                        values.expMonth,
                        values.expYear,
                        values.number,
                        values.cvv,
                        values.primary),
                object : PaymentsDataSource.NoDataCallback {
                    override fun onSuccess() {
                        callback.onSuccess(ResponseValues())
                    }

                    override fun onError(error: Error) {
                        callback.onError(error)
                    }
                })
    }

    class RequestValues(val expMonth: Int,
                        val expYear: Int,
                        val number: String,
                        val cvv: Int,
                        val primary: Boolean) : RxUseCase.RequestValues

    class ResponseValues : RxUseCase.ResponseValues

}