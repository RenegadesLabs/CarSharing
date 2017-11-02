package com.cardee.data_source;


import com.cardee.data_source.cache.LocalOwnerCarsDataSource;
import com.cardee.data_source.remote.RemoteOwnerCarsDataSource;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class OwnerCarsRepository implements OwnerCarsDataSource {

    private static OwnerCarsRepository INSTANCE;

    private boolean mDirtyCache = true;

    private final OwnerCarsDataSource mLocalDataSource;
    private final OwnerCarsDataSource mRemoteDataSource;
    private final Map<Integer, CarEntity> mCachedCars;

    private OwnerCarsRepository() {
        mLocalDataSource = LocalOwnerCarsDataSource.getInstance();
        mRemoteDataSource = RemoteOwnerCarsDataSource.getInstance();
        mCachedCars = new LinkedHashMap<>();
    }

    public static OwnerCarsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OwnerCarsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(final Callback callback) {
        if (notDirtyCache()) {
            Collection<CarEntity> values = mCachedCars.values();
            CarEntity[] cars = new CarEntity[values.size()];
            int index = 0;
            for (CarEntity carEntity : values) {
                cars[index] = carEntity;
                index++;
            }
            callback.onSuccess(cars);
            return;
        }

        mRemoteDataSource.obtainCars(new Callback() {
            @Override
            public void onSuccess(CarEntity[] carEntities) {
                callback.onSuccess(carEntities);
                refreshCache(carEntities);
            }

            @Override
            public void onError(Error error) {
                if (error.isConnectionError()) {
                    mDirtyCache = true;
                    obtainCarsLocally(callback, error);
                    return;
                }
                callback.onError(error);
            }
        });
    }

    private void obtainCarsLocally(final Callback callback, final Error defaultError) {
        mLocalDataSource.obtainCars(new Callback() {
            @Override
            public void onSuccess(CarEntity[] carEntities) {
                if (carEntities.length == 0) {
                    callback.onError(defaultError);
                    return;
                }
                callback.onSuccess(carEntities);
            }

            @Override
            public void onError(Error error) {
                callback.onError(defaultError);
            }
        });
    }

    public void refreshCars() {
        mDirtyCache = true;
    }

    private boolean notDirtyCache() {
        return !mCachedCars.isEmpty() && !mDirtyCache;
    }

    private void refreshCache(CarEntity[] cars) {
        mCachedCars.clear();
        for (CarEntity carEntity : cars) {
            mCachedCars.put(carEntity.getCarDetails().getCarId(), carEntity);
        }
        mDirtyCache = false;
    }

    CarEntity getCachedCar(int id){
        return mCachedCars.get(id);
    }
}
