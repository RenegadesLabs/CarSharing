package com.cardee.renter_browse_cars_map

import android.util.Log
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.mapper.ToFilterRequestMapper
import com.cardee.domain.renter.usecase.GetFilter
import com.cardee.domain.renter.usecase.GetFilteredCars
import com.cardee.domain.renter.usecase.SaveFilter
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.browse_car.ObtainAllOffers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer

class BrowseCarsPresenter(private var view: BrowseCarsContract.View<OfferItem>?,
                          private val getFilterUseCase: GetFilter = GetFilter(),
                          private val saveFilterUseCase: SaveFilter = SaveFilter(),
                          private val getFilteredCars: GetFilteredCars = GetFilteredCars()) :
        BrowseCarsContract.Presenter, Consumer<UIModelEvent> {

    private var disposable = Disposables.empty()

    override fun accept(event: UIModelEvent?) {
        when (event?.eventType) {
            UIModelEvent.EVENT_OFFER_LIST_CLICK -> {
                //TODO("implement offer screen")
                Log.e("CLICK_ON_OFFER", event.model.offer.carId.toString())
            }
        }
    }

    fun toggleFavorite() {
        val filter = getFilterUseCase.getFilter()
        val favorite = filter.favorite ?: false
        filter.favorite = favorite
        saveFilterUseCase.saveFilter(filter)
        view?.toggleFavorites(favorite)
        loadOffersByFilter(filter)
    }

    override fun load() {
        val filter = getFilterUseCase.getFilter()
        view?.toggleFavorites(filter.favorite == true)
        loadOffersByFilter(filter)
    }

    private fun loadOffersByFilter(filter: BrowseCarsFilter) {
        view?.showProgress(true)
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable = getFilteredCars.execute(GetFilteredCars.RequestValues(
                ToFilterRequestMapper().transform(filter)),
                object : RxUseCase.Callback<GetFilteredCars.ResponseValues> {
                    override fun onError(error: Error) {
                        view?.showProgress(false)
                        handleErrorResponse(error.errorType.ordinal, error.message)
                    }

                    override fun onSuccess(response: GetFilteredCars.ResponseValues) {
                        view?.showProgress(false)
                        view?.bind(response.cars.flatMap { offer ->
                            val id = offer.carId
                            listOf(OfferItem(id, offer))
                        })
                    }
                })
    }

    private fun handleErrorResponse(responseCode: Int, message: String?) {
        view?.let {
            it.showProgress(false)
            when (responseCode) {
                Response.UNAUTHORIZED -> {
                    view?.showMessage(message)
                }
                Response.SERVER_ERROR -> {
                    view?.showMessage(message)
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
