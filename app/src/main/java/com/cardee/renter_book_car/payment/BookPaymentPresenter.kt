package com.cardee.renter_book_car.payment

import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.payments.usecase.GetCardsUseCase
import io.reactivex.disposables.Disposable


class BookPaymentPresenter(view: BookPaymentView) {
    var mView: BookPaymentView? = view
    val getCards = GetCardsUseCase()
    var disposable: Disposable? = null
    var mAdapter: PaymentAdapter? = null

    fun onDestroy() {
        mView = null
        disposable?.dispose()
    }

    fun getMethods() {
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }

        disposable = getCards.execute(GetCardsUseCase.RequestValues(), object : RxUseCase.Callback<GetCardsUseCase.ResponseValues> {
            override fun onSuccess(response: GetCardsUseCase.ResponseValues) {
                val dataList = response.cards.map { Pair(it.brand + " " + it.cardNumber, it.paymentToken) }.toList()
                mAdapter?.setData(dataList)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    fun setAdapter(adapter: PaymentAdapter) {
        mAdapter = adapter
    }

    fun getAdapter(): PaymentAdapter? {
        return mAdapter
    }
}