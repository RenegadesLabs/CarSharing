package com.cardee.data_source;


import com.cardee.data_source.remote.api.cars.response.CarResponseBody;

public interface OwnerCarDataSource {

    void obtainCar(int id, Callback callback);

    interface Callback {

        void onSuccess(CarResponseBody carResponse);

        void onError(Error error);
    }
}
