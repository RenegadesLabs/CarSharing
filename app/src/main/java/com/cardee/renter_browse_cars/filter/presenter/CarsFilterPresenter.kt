package com.cardee.renter_browse_cars.filter.presenter

import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.mapper.ToFilterRequestMapper
import com.cardee.domain.renter.usecase.GetFilter
import com.cardee.domain.renter.usecase.GetFilteredCars
import com.cardee.domain.renter.usecase.SaveFilter
import com.cardee.renter_browse_cars.filter.view.FilterView
import io.reactivex.disposables.Disposable

class CarsFilterPresenter(var mView: FilterView?) {

    private val getCars: GetFilteredCars = GetFilteredCars()
    private val getFilterUseCase = GetFilter()
    private val saveFilterUseCase = SaveFilter()
    var disposable: Disposable?

    init {
        disposable = null
    }

    fun getFilteredCars(filter: BrowseCarsFilter) {
        val request = GetFilteredCars.RequestValues(ToFilterRequestMapper().transform(filter))

        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }

        disposable = getCars.execute(request, object : RxUseCase.Callback<GetFilteredCars.ResponseValues> {
            override fun onSuccess(response: GetFilteredCars.ResponseValues) {
                val carList = response.cars
                mView?.setButtonText("View ${carList.size} Cars")
                mView?.setCars(carList)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    fun onDestroy() {
        mView = null
    }

    fun getFilter(): BrowseCarsFilter {
        return getFilterUseCase.getFilter()
    }

    fun saveFilter(filter: BrowseCarsFilter) {
        saveFilterUseCase.saveFilter(filter)
    }
}
