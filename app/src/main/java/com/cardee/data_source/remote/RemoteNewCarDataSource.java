package com.cardee.data_source.remote;


import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.remote.api.cars.request.NewCarData;

public class RemoteNewCarDataSource implements NewCarDataSource {

    private static RemoteNewCarDataSource INSTANCE;

    private RemoteNewCarDataSource() {

    }

    public static RemoteNewCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteNewCarDataSource();
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
