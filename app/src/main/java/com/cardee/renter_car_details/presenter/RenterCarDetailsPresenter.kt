package com.cardee.renter_car_details.presenter

import android.util.Log
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.domain.renter.entity.mapper.OfferResponseByIdToRenterDetailedCar
import com.cardee.domain.renter.usecase.*
import com.cardee.renter_car_details.RenterCarDetailsContract
import com.google.android.gms.maps.model.LatLng
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat
import java.util.*


class RenterCarDetailsPresenter : RenterCarDetailsContract.Presenter {

    private var view: RenterCarDetailsContract.View? = null
    private var pendingCallback: (LatLng) -> Unit = {}
    private var pendingValue: RenterDetailedCar? = null
    private val executor: UseCaseExecutor = UseCaseExecutor.getInstance()
    private val getOfferById = GetOfferById()
    private val getOfferDistance = GetOfferDistance()
    private val getFilter = GetFilter()
    private val saveFilter = SaveFilter()
    private var mGetOfferDisposable: Disposable? = null
    private var mGetDistanceDisposable: Disposable? = null
    private var distance: Int? = null

    override fun attachView(view: RenterCarDetailsContract.View) {
        this.view = view
    }

    override fun fetchLocation(callback: (LatLng) -> Unit) {
        pendingCallback = callback
        if (pendingValue != null) {
            initLocation(pendingValue!!)
        }
    }

    override fun fetchDistance(id: Int, lat: Double, lng: Double) {
        if (mGetDistanceDisposable?.isDisposed == false) {
            mGetDistanceDisposable?.dispose()
        }
        mGetDistanceDisposable = getOfferDistance.execute(GetOfferDistance.RequestValues(id, lat, lng),
                object : RxUseCase.Callback<GetOfferDistance.ResponseValues> {
                    override fun onSuccess(response: GetOfferDistance.ResponseValues) {
                        distance = response.distance
                        val locationString = composeAddressString(response.address, response.distance)
                        view?.setLocationString(locationString)
                    }

                    override fun onError(error: Error) {
                        Log.e("FETCH_DISTANCE", error.message)
                    }
                })
    }

    override fun getDetailedCar(carId: Int?, lat: Double?, lng: Double?) {
        if (mGetOfferDisposable?.isDisposed == false) {
            mGetOfferDisposable?.dispose()
        }
        mGetOfferDisposable = getOfferById.execute(GetOfferById.RequestValues(carId!!, lat, lng),
                object : RxUseCase.Callback<GetOfferById.ResponseValues> {
                    override fun onSuccess(response: GetOfferById.ResponseValues) {
                        val offerDetails = OfferResponseByIdToRenterDetailedCar().transform(response.offer
                                ?: return)

                        val filter = getFilter()
                        val hourly = filter.bookingHourly ?: true
                        view?.setDetailedCar(offerDetails, hourly)

                        val locationString = composeAddressString(offerDetails.address, distance)
                        view?.setLocationString(locationString)
                        initLocation(offerDetails)


                        if (!hourly) {
                            setPickupReturnTime(offerDetails, filter)
                        }
                    }

                    override fun onError(error: Error) {
                        view?.showMessage(error.message)
                    }
                })
    }

    private fun setPickupReturnTime(offerDetails: RenterDetailedCar, filter: BrowseCarsFilter) {
        val delegate = view?.getDateReprDelegate()

        val dailyPickup = offerDetails.orderDailyDetails?.timePickup
        val dailyReturn = offerDetails.orderDailyDetails?.timeReturn
        val beginDate = delegate?.convertDateToDate(filter.rentalPeriodBegin)
        val endDate = delegate?.convertDateToDate(filter.rentalPeriodEnd)

        val processedBeginDate = setHoursToDate(beginDate, dailyPickup)
        val processedEndDate = setHoursToDate(endDate, dailyReturn)
        filter.rentalPeriodBegin = delegate?.formatAsIsoDate(processedBeginDate)
        filter.rentalPeriodEnd = delegate?.formatAsIsoDate(processedEndDate)
        saveFilter(filter)
    }

    private fun setHoursToDate(date: Date?, pickupTime: String?): Date? {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.timeZone = TimeZone.getTimeZone("GMT+8")
        calendar.time = date ?: return null
        val tempDate: Date = view?.getDateReprDelegate()?.convertTimeToDate(pickupTime)
                ?: calendar.time
        val tempCal = GregorianCalendar(Locale.US)
        tempCal.timeZone = TimeZone.getTimeZone("GMT+8")
        tempCal.time = tempDate
        calendar.set(Calendar.HOUR_OF_DAY, tempCal.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, tempCal.get(Calendar.MINUTE))
        calendar.set(Calendar.SECOND, tempCal.get(Calendar.SECOND))
        return calendar.time
    }

    fun initLocation(rentalDetails: RenterDetailedCar) {
        if (rentalDetails.latitude != null && rentalDetails.longitude != null) {
            pendingCallback.invoke(LatLng(rentalDetails.latitude, rentalDetails.longitude))
        }
    }

    private fun composeAddressString(address: String?, distance: Int?): String =
            "${address
                    ?: ""} ${if (address == null || distance == null) "" else "•"} ${formatDistance(distance)
                    ?: ""}"

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
        view?.setFavorite(favorite)
        executor.execute<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues>(AddCarToFavorites(),
                AddCarToFavorites.RequestValues(carId
                        ?: 0), object : UseCase.Callback<AddCarToFavorites.ResponseValues> {
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
            }
        })
    }

    override fun getFilter(): BrowseCarsFilter {
        return getFilter.getFilter()
    }

    override fun saveFilter(filter: BrowseCarsFilter) {
        saveFilter.saveFilter(filter)
    }

    override fun getCachedCar(): RenterDetailedCar? {
        return pendingValue
    }

    override fun onDestroy() {
        view = null
    }
}
