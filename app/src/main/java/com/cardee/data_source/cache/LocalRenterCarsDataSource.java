package com.cardee.data_source.cache;

import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.domain.renter.entity.FilterRequest;

import io.reactivex.disposables.Disposable;


public class LocalRenterCarsDataSource implements RenterCarsDataSource {

    private static LocalRenterCarsDataSource INSTANCE;

    public static LocalRenterCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalRenterCarsDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void obtainCars(OffersCallback offersCallback) {

    }

    @Override
    public void addCarToFavorites(int carId, NoDataCallback callback) {

    }

    @Override
    public void getFavorites(boolean isFavorite, OffersCallback offersCallback) {

    }

    @Override
    public void searchCars(String searchCriteria, OffersCallback offersCallback) {

    }

    @Override
    public Disposable obtainCarsByFilter(FilterRequest filter, OffersCallback callback) {
        return null;
    }
}
