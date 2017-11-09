package com.cardee.data_source;

import com.cardee.data_source.remote.api.cars.request.NewCarData;

public class NewCarRepository implements NewCarDataSource {

    private static NewCarRepository INSTANCE;

    private NewCarRepository() {

    }

    public static NewCarRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewCarRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainSavedCarData(CacheCallback callback) {

    }

    @Override
    public void saveCarData(NewCarData carData, boolean forcePush, Callback callback) {

    }
}
