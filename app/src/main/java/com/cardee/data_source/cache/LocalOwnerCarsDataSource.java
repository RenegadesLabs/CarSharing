package com.cardee.data_source.cache;

import com.cardee.data_source.OwnerCarsDataSource;

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
