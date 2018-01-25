package com.cardee.renter_browse_cars_map

import com.cardee.domain.rx.Response
import com.cardee.domain.rx.browse_car.ObtainAllOffers
import io.reactivex.functions.Consumer

class BrowseCarsPresenter(private var view: BrowseCarsContract.View<OfferItem>?,
                          private val allOffersUseCase: ObtainAllOffers = ObtainAllOffers()) :
        BrowseCarsContract.Presenter, Consumer<UIModelEvent> {

    override fun accept(t: UIModelEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAll() {
        view?.showProgress(true)
        val request: ObtainAllOffers.ObtainAllRequest = ObtainAllOffers.ObtainAllRequest(0, 0)
        allOffersUseCase.execute(request,
                { response ->
                    if (!response.success) {
                        handleErrorResponse(response.errorCode!!, response.errorMessage)
                    } else {
                        view?.showProgress(false)
                        response.body?.let {
                            view?.bind(it.flatMap {
                                offer ->
                                val id = offer.carId
                                listOf(OfferItem(id, offer)) })
                        }
                    }
                }, onError)
    }

    private fun handleErrorResponse(responseCode: Int, message: String?) {
        view?.let {
            it.showProgress(false)
            when (responseCode) {
                Response.UNAUTHORIZED -> {
                    //TODO("not implemented") //implement
                }
                Response.SERVER_ERROR -> {
                    //TODO("not implemented") //implement
                }
                else -> view?.showMessage(message)
            }
        }
    }

    private val onError = { error: Throwable ->
        if (view != null) {
            view!!.showProgress(false)
            view!!.showMessage(error.message)
        }
    }
}
