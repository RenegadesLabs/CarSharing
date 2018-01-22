package com.cardee.data_source;

import com.cardee.data_source.cache.LocalRenterCarsDataSource;
import com.cardee.data_source.remote.RemoteRenterCarsDataSource;
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RenterCarsRepository implements RenterCarsDataSource {

    private final RemoteRenterCarsDataSource mRemoteDataSource;
    private final LocalRenterCarsDataSource mLocalDataSource;

    private final Map<Integer, OfferResponseBody> mCachedCars;

    private boolean mDirtyCache = true;

    private static RenterCarsRepository INSTANCE;

    public static RenterCarsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RenterCarsRepository();
        }
        return INSTANCE;
    }


    public RenterCarsRepository() {
        mRemoteDataSource = RemoteRenterCarsDataSource.getInstance();
        mLocalDataSource = LocalRenterCarsDataSource.getInstance();
        mCachedCars = new LinkedHashMap<>();
    }



    @Override
    public void obtainCars(OffersCallback offersCallback) {
        if (notDirtyCache()) {
            Collection<OfferResponseBody> values = mCachedCars.values();
            OfferResponseBody[] cars = new OfferResponseBody[values.size()];
            int index = 0;
            for (OfferResponseBody carEntity : values) {
                cars[index] = carEntity;
                index++;
            }
            offersCallback.onSuccess(cars);
        }

        mRemoteDataSource.obtainCars(new OffersCallback() {

            @Override
            public void onSuccess(OfferResponseBody[] response) {
                offersCallback.onSuccess(response);
                refreshCache(response);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Error error) {
                offersCallback.onError(error);
            }
        });

    }

    @Override
    public void addCarToFavorites(int carId, Callback callback) {
        mRemoteDataSource.addCarToFavorites(carId, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public void refreshCars() {
        mDirtyCache = true;
    }

    private boolean notDirtyCache() {
        return !mCachedCars.isEmpty() && !mDirtyCache;
    }

    private void refreshCache(OfferResponseBody[] response) {
        mCachedCars.clear();
        for (OfferResponseBody responseBody : response) {
            mCachedCars.put(responseBody.getCarDetailEntity().getCarId(), responseBody);
        }
        mDirtyCache = false;
    }

    public OfferResponseBody getCachedCar(int id) {
        return mCachedCars.get(id);
    }
}
