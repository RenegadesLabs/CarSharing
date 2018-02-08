package com.cardee.renter_car_details.presenter

import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.domain.renter.entity.mapper.OfferResponseByIdToRenterDetailedCar
import com.cardee.domain.renter.usecase.AddCarToFavorites
import com.cardee.domain.renter.usecase.GetOfferById
import com.cardee.renter_car_details.RenterCarDetailsContract
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat


class RenterCarDetailsPresenter : RenterCarDetailsContract.Presenter {

    private var view: RenterCarDetailsContract.View? = null
    private var pendingCallback: (LatLng) -> Unit = {}
    private var pendingValue: RenterDetailedCar? = null
    private val executor: UseCaseExecutor = UseCaseExecutor.getInstance()
    private val getOfferById = GetOfferById()
    private var mGetOfferDisposable: Disposable? = null

    override fun attachView(view: RenterCarDetailsContract.View) {
        this.view = view
    }

    override fun fetchLocation(callback: (LatLng) -> Unit) {
        pendingCallback = callback
        if (pendingValue != null) {
            initLocation(pendingValue!!)
        }
    }

    override fun getDetailedCar(carId: Int?, lat: Double?, lng: Double?) {
        if (mGetOfferDisposable?.isDisposed == false) {
            mGetOfferDisposable?.dispose()
        }
        mGetOfferDisposable = getOfferById.execute(GetOfferById.RequestValues(carId!!, lat, lng), object : RxUseCase.Callback<GetOfferById.ResponseValues> {
            override fun onSuccess(response: GetOfferById.ResponseValues) {
                val offerDetails = OfferResponseByIdToRenterDetailedCar().transform(response.offer ?: return)
                view?.setDetailedCar(offerDetails)
                initLocation(offerDetails)
                composeAddressString(offerDetails.address, offerDetails.distance)
            }

            override fun onError(error: Error) {
                view?.showMessage(error.message)
            }
        })
    }

    fun initLocation(rentalDetails: RenterDetailedCar) {
        if (rentalDetails.latitude != null && rentalDetails.longitude != null) {
            pendingCallback.invoke(LatLng(rentalDetails.latitude, rentalDetails.longitude))
        }
    }

    private fun composeAddressString(address: String?, distance: Int?) {
        view?.let { viewRef ->
            val locationString = "${address ?: ""} " +
                    "${if (address == null || distance == null) "" else "•"} " +
                    (formatDistance(distance) ?: "")
            viewRef.setCarLocationString(locationString)
        }
    }

    private fun formatDistance(meters: Int?): String? {
        meters ?: return null
        return when {
            meters > 9999 -> "${meters / 1000}km"
            meters > 999 -> {
                val kilometers = meters.toFloat() / 1000
                val kmFormatted = DecimalFormat("#.#").format(kilometers)
                "${kmFormatted}km"
            }
            else -> "${meters}m"
        }
    }

    override fun addCarToFavorites(carId: Int?, favorite: Boolean) {
        executor.execute<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues>(AddCarToFavorites(),
                AddCarToFavorites.RequestValues(carId ?: 0), object : UseCase.Callback<AddCarToFavorites.ResponseValues> {
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
