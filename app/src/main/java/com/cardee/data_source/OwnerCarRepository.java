package com.cardee.data_source;


import android.util.LruCache;

import com.cardee.data_source.cache.LocalOwnerCarDataSource;
import com.cardee.data_source.remote.RemoteOwnerCarDataSource;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

import static com.cardee.data_source.Error.Type.SERVER;

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
        }

        CarEntity cachedCarEntity = mCarsRepository.getCachedCar(id);
        if (cachedCarEntity != null) {
            callback.onSuccess(CarResponseBody.from(cachedCarEntity));
        }
        mRemoteDataSource.obtainCar(id, new Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                Integer id = carResponse.getCarDetails().getCarId();
                if (id != null) {
                    callback.onSuccess(carResponse);
                    mCache.put(carResponse.getCarDetails().getCarId(), carResponse);
                    mCarId = carResponse.getCarDetails().getCarId();
                } else {
                    callback.onError(new Error(SERVER, "car_id is null!"));
                }
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

    public CarResponseBody getCachedCar(Integer id) {
        return mCache.get(id);
    }

    public void refresh(Integer id) {
        if (id == null) {
            return;
        }
        if (mCache.get(id) != null) {
            mCache.remove(id);
        }
    }
}
