package com.cardee.data_source;


import com.cardee.data_source.cache.LocalCarEditDataSource;
import com.cardee.data_source.remote.RemoteCarEditDataSource;
import com.cardee.data_source.remote.api.cars.request.NewCarData;

public class CarEditRepository implements CarEditDataSource {

    private static CarEditRepository INSTANCE;

    private final CarEditDataSource localDataSource;
    private final CarEditDataSource remoteDataSource;

    private CarEditRepository() {
        localDataSource = LocalCarEditDataSource.getInstance();
        remoteDataSource = RemoteCarEditDataSource.getInstance();
    }

    public static CarEditRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarEditRepository();
        }
        return INSTANCE;
    }

    @Override
    public void updateLocation(final Integer id, NewCarData carData, final CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        remoteDataSource.updateLocation(id, carData, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        remoteDataSource.updateInfo(id, carData, callback);
    }
}
