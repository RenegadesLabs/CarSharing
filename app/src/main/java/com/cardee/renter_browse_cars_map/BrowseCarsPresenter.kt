package com.cardee.renter_browse_cars_map

import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.usecase.GetFilter
import com.cardee.domain.renter.usecase.GetFilteredCars
import com.cardee.domain.renter.usecase.SaveFilter
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.browse_car.ObtainAllOffers
import io.reactivex.functions.Consumer

class BrowseCarsPresenter(private var view: BrowseCarsContract.View<OfferItem>?,
                          private val allOffersUseCase: ObtainAllOffers = ObtainAllOffers(),
                          private val getFilterUseCase: GetFilter = GetFilter(),
                          private val saveFilterUseCase: SaveFilter = SaveFilter(),
                          private val getFilteredCara: GetFilteredCars = GetFilteredCars()) :
        BrowseCarsContract.Presenter, Consumer<UIModelEvent> {


    private var filter: BrowseCarsFilter? = null

    override fun accept(event: UIModelEvent?) {
        when (event?.eventType) {
            UIModelEvent.EVENT_OFFER_FAVORITE_TOGGLE -> {

            }
            UIModelEvent.EVENT_OFFER_FILTER_CLICK -> {
            }
            UIModelEvent.EVENT_OFFER_LIST_CLICK -> {
            }
        }
    }

    private fun toggleFavorite() {

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
                            view?.bind(it.flatMap { offer ->
                                val id = offer.carId
                                listOf(OfferItem(id, offer))
                            })
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
