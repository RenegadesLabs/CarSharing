package com.cardee.data_source.cache;

import com.cardee.data_source.OwnerCarDataSource;

public class LocalOwnerCarDataSource implements OwnerCarDataSource {

    private static LocalOwnerCarDataSource INSTANCE;

    private LocalOwnerCarDataSource() {

    }

    public static LocalOwnerCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalOwnerCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(Callback callback) {

    }
}
