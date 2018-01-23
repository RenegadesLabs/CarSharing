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
    public void obtainCars(Callback callback) {

    }

    @Override
    public Disposable obtainCarsByFilter(FilterRequest filter, Callback callback) {
        return null;
    }
}
