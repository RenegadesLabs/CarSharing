package com.cardee.data_source.cache;

import com.cardee.data_source.RenterCarsDataSource;


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
    public void addCarToFavorites(int carId, Callback callback) {

    }

    @Override
    public void getFavorites(boolean isFavorite, OffersCallback offersCallback) {

    }
}
