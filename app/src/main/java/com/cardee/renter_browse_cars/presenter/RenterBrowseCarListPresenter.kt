package com.cardee.renter_browse_cars.presenter

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.cardee.custom.modal.SortRenterOffersDialog
import com.cardee.custom.modal.TypeRenterOffersDialog
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.mapper.ToFilterRequestMapper
import com.cardee.domain.renter.usecase.*
import com.cardee.renter_browse_cars.RenterBrowseCarListContract
import com.cardee.renter_car_details.view.RenterCarDetailsActivity
import com.cardee.settings.Settings
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class RenterBrowseCarListPresenter(private var mView: RenterBrowseCarListContract.View?, private val mSettings: Settings) :
        Consumer<RenterBrowseCarListContract.CarEvent>,
        RenterBrowseCarListContract.Presenter,
        SortRenterOffersDialog.SortSelectListener,
        TypeRenterOffersDialog.TypeSelectListener {

    private var firstStart = true
    private val mGetFilteredCars: GetFilteredCars = GetFilteredCars()
    private val mGetFilter: GetFilter = GetFilter()
    private val mSaveFilter: SaveFilter = SaveFilter()
    private var mDisposable: Disposable? = null

    private val mExecutor: UseCaseExecutor = UseCaseExecutor.getInstance()

    @Throws(Exception::class)
    override fun accept(carEvent: RenterBrowseCarListContract.CarEvent) {

        when (carEvent.action) {
            RenterBrowseCarListContract.Action.UPDATED -> {
            }

            RenterBrowseCarListContract.Action.OPEN -> {
                val context = (mView as Fragment).activity
                val intent = Intent(context, RenterCarDetailsActivity::class.java)
                intent.apply {
                    putExtra("carId", carEvent.car.carId)
                    putExtra("isFavorite", carEvent.car.isFavorite)
                }
                context.startActivity(intent)
            }

            RenterBrowseCarListContract.Action.FAVORITE -> addCarToFavorites(carEvent.car.carId)
        }

    }

    override fun addCarToFavorites(carId: Int) {
        mView?.showProgress(true)
        mExecutor.execute<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues>(AddCarToFavorites(),
                AddCarToFavorites.RequestValues(carId), object : UseCase.Callback<AddCarToFavorites.ResponseValues> {
            override fun onSuccess(response: AddCarToFavorites.ResponseValues) {
                mView?.showProgress(false)
            }

            override fun onError(error: Error) {
                mView?.showProgress(false)
                handleError(error)
            }
        })
    }

    override fun searchCars(criteria: String) {
        mView?.showSearchProgress(true)
        mView?.showProgress(true)
        mExecutor.execute<SearchCars.RequestValues, SearchCars.ResponseValues>(SearchCars(), SearchCars.RequestValues(criteria),
                object : UseCase.Callback<SearchCars.ResponseValues> {
                    override fun onSuccess(response: SearchCars.ResponseValues) {
                        mView?.showSearchProgress(false)
                        mView?.showProgress(false)
                        mView?.setItems(response.offerCars)
                    }

                    override fun onError(error: Error) {
                        mView?.showSearchProgress(false)
                        mView?.showProgress(false)
                        handleError(error)
                    }
                })
    }

    override fun getCarsByFilter(filter: BrowseCarsFilter) {
        mView?.showProgress(true)

        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }

        mDisposable = mGetFilteredCars.execute(GetFilteredCars.RequestValues(
                ToFilterRequestMapper().transform(filter)),
                object : RxUseCase.Callback<GetFilteredCars.ResponseValues> {
                    override fun onError(error: Error) {
                        mView?.showProgress(false)
                        handleError(error)
                    }

                    override fun onSuccess(response: GetFilteredCars.ResponseValues) {
                        mView?.showProgress(false)
                        mView?.setItems(response.cars)
                    }
                })
    }

    override fun getCarsByFilterWithoutProgress(filter: BrowseCarsFilter) {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }

        mDisposable = mGetFilteredCars.execute(GetFilteredCars.RequestValues(
                ToFilterRequestMapper().transform(filter)),
                object : RxUseCase.Callback<GetFilteredCars.ResponseValues> {
                    override fun onError(error: Error) {
                        handleError(error)
                    }

                    override fun onSuccess(response: GetFilteredCars.ResponseValues) {
                        mView?.setItems(response.cars)
                    }
                })
    }

    override fun getFilter(): BrowseCarsFilter {
        return mGetFilter.getFilter()
    }

    override fun saveFilter(filter: BrowseCarsFilter) {
        mSaveFilter.saveFilter(filter)
    }

    fun refresh() {
        firstStart = true
    }

    private fun handleError(error: Error) {
        when {
            error.isAuthError -> mView?.onUnauthorized()
            error.isConnectionError -> mView?.onConnectionLost()
            else -> mView?.showMessage(error.message)
        }
    }


    override fun showSort(activity: FragmentActivity) {
        val sortDialog = SortRenterOffersDialog.getInstance(mSettings.sortOffers);
        sortDialog.setSortSelectListener(this)
        sortDialog.show(activity.supportFragmentManager, sortDialog.tag)
    }

    override fun showType(activity: FragmentActivity) {
        val typeDialog = TypeRenterOffersDialog.getInstance(mSettings.typeOffers)
        typeDialog.show(activity.supportFragmentManager, typeDialog.tag)
        typeDialog.setTypeSelectListener(this)
    }

    override fun setSort(sort: RenterBrowseCarListContract.Sort) {
        if (sort == RenterBrowseCarListContract.Sort.DISTANCE) {
            mView?.checkLocationPermission()
        } else {
            continueSetSort(sort)
        }
    }

    override fun continueSetSort(sort: RenterBrowseCarListContract.Sort) {
        mSettings.sortOffers = sort
        mView?.setSortValue(sort.value)
        sortCars(sort.value)
    }

    override fun sortCars(sortBy: String?) {
        val fil = filter
        fil.orderBy = sortBy
        saveFilter(fil)
        getCarsByFilter(fil)
    }

    override fun setType(type: RenterBrowseCarListContract.VehicleType) {
        mSettings.typeOffers = type
    }

    override fun onSortSelected(sort: RenterBrowseCarListContract.Sort) {
        setSort(sort)
    }

    override fun onTypeSelected(type: RenterBrowseCarListContract.VehicleType) {
//        setType(type)
    }

    override fun onDestroy() {
        mView = null
    }

}