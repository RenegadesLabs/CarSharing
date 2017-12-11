package com.cardee.data_source;


import android.util.LruCache;

import com.cardee.data_source.cache.LocalOwnerCarDataSource;
import com.cardee.data_source.remote.RemoteOwnerCarDataSource;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

public class OwnerCarRepository implements OwnerCarDataSource {

    private static OwnerCarRepository INSTANCE;
    private static final int MAX_CACHE_SIZE = 10;

    private final OwnerCarsRepository mCarsRepository;
    private final OwnerCarDataSource mLocalDataSource;
    private final OwnerCarDataSource mRemoteDataSource;
    private final LruCache<Integer, CarResponseBody> mCache;

    private static int mCarId;

    private OwnerCarRepository() {
        mCarsRepository = OwnerCarsRepository.getInstance();
        mLocalDataSource = LocalOwnerCarDataSource.getInstance();
        mRemoteDataSource = RemoteOwnerCarDataSource.getInstance();
        mCache = new LruCache<>(MAX_CACHE_SIZE);
    }

    public static OwnerCarRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OwnerCarRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCar(int id, final Callback callback) {
        CarResponseBody cachedCar = mCache.get(id);
        if (cachedCar != null) {
            callback.onSuccess(cachedCar);
            return;
        }

        CarEntity cachedCarEntity = mCarsRepository.getCachedCar(id);
        if (cachedCarEntity != null) {
            callback.onSuccess(CarResponseBody.from(cachedCarEntity));
        }
        mRemoteDataSource.obtainCar(id, new Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                callback.onSuccess(carResponse);
                mCache.put(carResponse.getCarDetails().getCarId(), carResponse);
                mCarId = carResponse.getCarDetails().getCarId();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static int currentCarId() {
        return mCarId;
    }

    CarResponseBody getCachedCar(Integer id) {
        return mCache.get(id);
    }

    void refresh(Integer id) {
        if (id == null) {
            return;
        }
        if (mCache.get(id) != null) {
            mCache.remove(id);
        }
    }
}
