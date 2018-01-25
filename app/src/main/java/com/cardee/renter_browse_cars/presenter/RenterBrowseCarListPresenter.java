package com.cardee.renter_browse_cars.presenter;


import android.support.v4.app.FragmentActivity;

import com.cardee.custom.modal.SortRenterOffersDialog;
import com.cardee.data_source.Error;
import com.cardee.domain.RxUseCase;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.domain.renter.entity.mapper.ToFilterRequestMapper;
import com.cardee.domain.renter.usecase.AddCarToFavorites;
import com.cardee.domain.renter.usecase.GetCars;
import com.cardee.domain.renter.usecase.GetFilteredCars;
import com.cardee.domain.renter.usecase.SearchCars;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;
import com.cardee.settings.Settings;
import com.crashlytics.android.Crashlytics;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RenterBrowseCarListPresenter implements Consumer<RenterBrowseCarListContract.CarEvent>,
        RenterBrowseCarListContract.Presenter, SortRenterOffersDialog.SortSelectListener {

    private final UseCaseExecutor mExecutor;
    private RenterBrowseCarListContract.View mView;
    private GetFilteredCars mGetFilteredCars;

    private boolean firstStart = true;

    private final Settings mSettings;
    private Disposable mDisposable;


    public RenterBrowseCarListPresenter(RenterBrowseCarListContract.View view, Settings settings) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mSettings = settings;
        mGetFilteredCars = new GetFilteredCars();
    }

    @Override
    public void accept(RenterBrowseCarListContract.CarEvent carEvent) throws Exception {

        switch (carEvent.getAction()) {
            case UPDATED:
                break;

            case OPEN:
                break;

            case FAVORITE:
                addCarToFavorites(carEvent.getCar().getCarId());
                break;
        }

    }

    @Override
    public void loadItems() {
        if (firstStart && mView != null) {
            mView.showProgress(true);
        }

        mExecutor.execute(new GetCars(), new GetCars.RequestValues(), new UseCase.Callback<GetCars.ResponseValues>() {
            @Override
            public void onSuccess(GetCars.ResponseValues response) {
                List<OfferCar> cars = response.getOfferCars();
                if (firstStart && mView != null) {
                    mView.showProgress(false);
                    firstStart = false;
                } else {
                    Crashlytics.logException(new Throwable("Success: View is null"));
                }
                mView.setItems(cars);
            }

            @Override
            public void onError(Error error) {
                handleError(error);
            }
        });

    }

    @Override
    public void addCarToFavorites(int carId) {
        if (mView != null) {
            mView.showProgress(true);
        }
        mExecutor.execute(new AddCarToFavorites(), new AddCarToFavorites.RequestValues(carId), new UseCase.Callback<AddCarToFavorites.ResponseValues>() {
            @Override
            public void onSuccess(AddCarToFavorites.ResponseValues response) {
                mView.showProgress(false);
                loadItems();
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                handleError(error);
            }
        });
    }

    @Override
    public void searchCars(String criteria) {

        if (mView != null) {
            mView.showSearchProgress(true);
        }

        mExecutor.execute(new SearchCars(), new SearchCars.RequestValues(criteria),
                new UseCase.Callback<SearchCars.ResponseValues>() {
                    @Override
                    public void onSuccess(SearchCars.ResponseValues response) {
                        mView.showSearchProgress(false);
                        mView.setItemsSearchList(response.getOfferCars());
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showSearchProgress(false);
                        handleError(error);
                    }
                });
    }

    @Override
    public void getCarsByFilter(BrowseCarsFilter filter) {
        if (mView != null) {
            mView.showProgress(true);
        }

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        mDisposable = mGetFilteredCars.execute(new GetFilteredCars.RequestValues(
                        new ToFilterRequestMapper().transform(filter)),
                new RxUseCase.Callback<GetFilteredCars.ResponseValues>() {
                    @Override
                    public void onError(@NotNull Error error) {
                        if (mView != null) {
                            mView.showProgress(false);
                            handleError(error);
                        }
                    }

                    @Override
                    public void onSuccess(GetFilteredCars.ResponseValues response) {
                        if (mView != null) {
                            mView.showProgress(false);
                            mView.setItems(response.getCars());
                        }
                    }
                });
    }

    public void refresh() {
        firstStart = true;
    }

    private void handleError(Error error) {

        if (error.isAuthError()) {
            mView.onUnauthorized();
        } else if (error.isConnectionError()) {
            mView.onConnectionLost();
        } else {
            mView.showMessage(error.getMessage());
        }
    }


    @Override
    public void showSort(FragmentActivity activity) {
        SortRenterOffersDialog sortDialog = SortRenterOffersDialog.getInstance(mSettings.getSortOffers());
        sortDialog.show(activity.getSupportFragmentManager(), sortDialog.getTag());
        sortDialog.setSortSelectListener(this);
    }

    @Override
    public void setSort(RenterBrowseCarListContract.Sort sort) {
        mSettings.setSortOffers(sort);
    }

    @Override
    public void onSortSelected(RenterBrowseCarListContract.Sort sort) {

    }
}
