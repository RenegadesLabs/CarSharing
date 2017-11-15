package com.cardee.data_source;


import com.cardee.data_source.remote.api.cars.request.NewCarData;

public interface CarEditDataSource {

    void updateLocation(Integer id, NewCarData carData, Callback callback);

    void updateInfo(Integer id, NewCarData carData, Callback callback);


    interface Callback {
        void onSuccess();

        void onError(Error error);
    }
}
