package com.cardee.renter_book_car.presenter

import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.RxUseCase
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.domain.renter.usecase.GetOfferById
import com.cardee.renter_book_car.BookCarContract
import io.reactivex.disposables.Disposable

class BookCarPresenter : BookCarContract.BookCarPresenter {
    private var mView: BookCarContract.BookCarView? = null
    private val getOfferById = GetOfferById()
    private var mDisposable: Disposable? = null

    override fun init(bookCarView: BookCarContract.BookCarView) {
        mView = bookCarView
    }

    override fun getOffer(id: Int, state: BookCarState) {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
        mDisposable = getOfferById.execute(GetOfferById.RequestValues(id), object : RxUseCase.Callback<GetOfferById.ResponseValues> {
            override fun onSuccess(response: GetOfferById.ResponseValues) {
                val offer = response.offer ?: return
                setView(offer)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    private fun setView(offer: OfferByIdResponseBody) {
        val carTitle = offer.carDetails?.carTitle
        val carYear = offer.carDetails?.carYear
        mView?.setCarTitle(carTitle)
        mView?.setCarYear(carYear)
    }

    override fun onDestroy() {
        mView = null
    }

}