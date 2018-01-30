package com.cardee.renter_browse_cars.presenter

import android.support.v4.app.FragmentActivity
import com.cardee.custom.modal.SortRenterOffersDialog
import com.cardee.custom.modal.TypeRenterOffersDialog
import com.cardee.data_source.Error
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.renter.usecase.*
import com.cardee.renter_browse_cars.RenterBrowseCarListContract
import com.cardee.settings.Settings
import com.crashlytics.android.Crashlytics
import io.reactivex.functions.Consumer


class RenterBrowseCarListPresenter(private val mView: RenterBrowseCarListContract.View?, private val mSettings: Settings) :
        Consumer<RenterBrowseCarListContract.CarEvent>,
        RenterBrowseCarListContract.Presenter,
        SortRenterOffersDialog.SortSelectListener,
        TypeRenterOffersDialog.TypeSelectListener {

    private var firstStart = true
    private var isFavorites = false

    private val mExecutor: UseCaseExecutor = UseCaseExecutor.getInstance()

    @Throws(Exception::class)
    override fun accept(carEvent: RenterBrowseCarListContract.CarEvent) {

        when (carEvent.action) {
            RenterBrowseCarListContract.Action.UPDATED -> {
            }

            RenterBrowseCarListContract.Action.OPEN -> {
            }

            RenterBrowseCarListContract.Action.FAVORITE -> addCarToFavorites(carEvent.car.carId)
        }

    }

    override fun loadItems() {
        if (firstStart) {
            mView?.showProgress(true)
        }

        mExecutor.execute<GetCars.RequestValues, GetCars.ResponseValues>(GetCars(),
                GetCars.RequestValues(), object : UseCase.Callback<GetCars.ResponseValues> {
            override fun onSuccess(response: GetCars.ResponseValues) {
                val cars = response.offerCars
                if (firstStart) {
                    mView?.showProgress(false)
                    firstStart = false
                } else {
                    Crashlytics.logException(Throwable("Success: View is null"))
                }
                mView?.setItems(cars)
            }

            override fun onError(error: Error) {
                handleError(error)
            }
        })

    }

    override fun addCarToFavorites(carId: Int) {
        mView?.showProgress(true)
        mExecutor.execute<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues>(AddCarToFavorites(),
                AddCarToFavorites.RequestValues(carId), object : UseCase.Callback<AddCarToFavorites.ResponseValues> {
            override fun onSuccess(response: AddCarToFavorites.ResponseValues) {
                mView?.showProgress(false)
                if (isFavorites) {
                    showFavorites(true)
                    return
                }
                loadItems()
            }

            override fun onError(error: Error) {
                mView!!.showProgress(false)
                handleError(error)
            }
        })
    }

    override fun showFavorites(show: Boolean) {
        isFavorites = show
        if (show) {
            mView?.showProgress(true)
            mExecutor.execute<GetFavorites.RequestValues, GetFavorites.ResponseValues>(GetFavorites(), GetFavorites.RequestValues(true),
                    object : UseCase.Callback<GetFavorites.ResponseValues> {
                        override fun onSuccess(response: GetFavorites.ResponseValues) {
                            val cars = response.offerCars
                            mView?.showProgress(false)
                            mView?.setItems(cars)
                        }

                        override fun onError(error: Error) {
                            mView?.showProgress(false)
                            handleError(error)
                        }
                    })
            return
        }
        loadItems()
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
        mSettings.sortOffers = sort
        mView?.setSortValue(sort.value)
        sortCars(sort.value)
    }

    override fun sortCars(sortBy: String?) {
        mView?.showProgress(true)
        mExecutor.execute<SortCars.RequestValues, SortCars.ResponseValues>(SortCars(), SortCars.RequestValues(sortBy),
                object : UseCase.Callback<SortCars.ResponseValues> {
                    override fun onSuccess(response: SortCars.ResponseValues) {
                        mView?.showProgress(false)
                        mView?.setItems(response.offerCars)
                    }

                    override fun onError(error: Error) {
                        mView?.showProgress(false)
                        handleError(error)
                    }
                })
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
}