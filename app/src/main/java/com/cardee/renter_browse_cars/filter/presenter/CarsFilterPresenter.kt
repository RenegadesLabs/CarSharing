package com.cardee.renter_browse_cars.filter.presenter

import com.cardee.data_source.Error
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.mapper.ToFilterRequestMapper
import com.cardee.domain.renter.usecase.GetFilteredCars
import com.cardee.renter_browse_cars.filter.view.FilterView

class CarsFilterPresenter(var mView: FilterView?) {

    val executor: UseCaseExecutor
    val getCars: GetFilteredCars

    init {
        executor = UseCaseExecutor.getInstance()
        getCars = GetFilteredCars()
    }

    fun getFilteredCars(filter: BrowseCarsFilter) {
        val request = GetFilteredCars.RequestValues(ToFilterRequestMapper().transform(filter))
        executor.execute(getCars, request, object : UseCase.Callback<GetFilteredCars.ResponseValues> {
            override fun onSuccess(response: GetFilteredCars.ResponseValues?) {
                val carList = response?.cars
                mView?.setButtonText("View ${carList?.size} Cars")
            }

            override fun onError(
                    error: Error?) {
                mView?.showMessage(error?.message)
            }
        })
    }

    fun onDestroy() {
        mView = null
    }
}
