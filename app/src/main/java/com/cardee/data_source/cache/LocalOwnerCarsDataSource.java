package com.cardee.data_source.cache;

import com.cardee.data_source.OwnerCarsDataSource;
import com.cardee.data_source.remote.api.profile.response.CarsResponse;

import io.reactivex.Single;

public class LocalOwnerCarsDataSource implements OwnerCarsDataSource {

    private static LocalOwnerCarsDataSource INSTANCE;

    private LocalOwnerCarsDataSource() {

    }

    public static LocalOwnerCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalOwnerCarsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(Callback callback) {
    }
}
