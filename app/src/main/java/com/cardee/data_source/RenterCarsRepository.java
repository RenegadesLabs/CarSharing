package com.cardee.data_source;

import com.cardee.data_source.cache.LocalRenterCarsDataSource;
import com.cardee.data_source.remote.RemoteRenterCarsDataSource;
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.renter.entity.FilterRequest;

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
    public void obtainCars(Callback callback) {
        if (notDirtyCache()) {
            Collection<OfferResponseBody> values = mCachedCars.values();
            OfferResponseBody[] cars = new OfferResponseBody[values.size()];
            int index = 0;
            for (OfferResponseBody carEntity : values) {
                cars[index] = carEntity;
                index++;
            }
            callback.onSuccess(cars);
        }

        mRemoteDataSource.obtainCars(new Callback() {

            @Override
            public void onSuccess(OfferResponseBody[] response) {
                callback.onSuccess(response);
                refreshCache(response);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });

    }

    @Override
    public void obtainCarsByFilter(FilterRequest filterRequest, Callback callback) {
        mRemoteDataSource.obtainCarsByFilter(filterRequest, new Callback() {
            @Override
            public void onSuccess(OfferResponseBody[] response) {
                callback.onSuccess(response);
                refreshCache(response);
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
