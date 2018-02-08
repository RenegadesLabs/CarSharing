package com.cardee.renter_car_details.presenter

import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.mapper.OfferResponseByIdToRenterDetailedCar
import com.cardee.domain.renter.usecase.AddCarToFavorites
import com.cardee.domain.renter.usecase.GetOfferById
import com.cardee.renter_car_details.RenterCarDetailsContract
import io.reactivex.disposables.Disposable


class RenterCarDetailsPresenter : RenterCarDetailsContract.Presenter {

    private var view: RenterCarDetailsContract.View? = null
    private var pendingCallback: ((String) -> Unit)? = null
    private val executor: UseCaseExecutor = UseCaseExecutor.getInstance()
    private val getOfferById = GetOfferById()
    private var mGetOfferDisposable: Disposable? = null

    override fun attachView(view: RenterCarDetailsContract.View) {
        this.view = view
    }

    override fun fetchLocation(callback: (String) -> Unit) {
        pendingCallback = callback
        pendingCallback?.invoke("")
    }

    override fun getDetailedCar(carId: Int?) {
        if (mGetOfferDisposable?.isDisposed == false) {
            mGetOfferDisposable?.dispose()
        }
        mGetOfferDisposable = getOfferById.execute(GetOfferById.RequestValues(carId!!), object : RxUseCase.Callback<GetOfferById.ResponseValues> {
            override fun onSuccess(response: GetOfferById.ResponseValues) {
                view?.setDetailedCar(OfferResponseByIdToRenterDetailedCar().transform(response.offer?: return))
            }

            override fun onError(error: Error) {
                view?.showMessage(error.message)
            }
        })
    }


     override fun addCarToFavorites(carId: Int?, favorite: Boolean) {
        executor.execute<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues>(AddCarToFavorites(),
                AddCarToFavorites.RequestValues(carId?: 0), object : UseCase.Callback<AddCarToFavorites.ResponseValues> {
            override fun onSuccess(response: AddCarToFavorites.ResponseValues) {
                view?.showMessage(R.string.renter_car_details_added_to_fav)
                if (favorite) {
                    view?.setFavorite(false)
                    return
                }
                view?.setFavorite(true)
            }

            override fun onError(error: Error) {
                view?.showMessage(R.string.error_occurred)
//                handleError(error)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}
