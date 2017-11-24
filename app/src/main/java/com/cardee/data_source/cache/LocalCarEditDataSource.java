package com.cardee.data_source.cache;


import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.remote.api.cars.request.NewCarData;

public class LocalCarEditDataSource implements CarEditDataSource {

    private static LocalCarEditDataSource INSTANCE;

    private LocalCarEditDataSource() {

    }

    public static LocalCarEditDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalCarEditDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void updateLocation(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {

    }

    @Override
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {

    }
}
