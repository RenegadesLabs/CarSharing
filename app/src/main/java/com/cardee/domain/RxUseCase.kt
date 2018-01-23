package com.cardee.domain

import com.cardee.data_source.Error
import io.reactivex.disposables.Disposable

interface RxUseCase<in V : RxUseCase.RequestValues, out R : RxUseCase.ResponseValues> {

    fun execute(values: V, callback: Callback<R>): Disposable

    interface RequestValues

    interface ResponseValues

    interface Callback<in R> {
        fun onSuccess(response: R)

        fun onError(error: Error)
    }
}