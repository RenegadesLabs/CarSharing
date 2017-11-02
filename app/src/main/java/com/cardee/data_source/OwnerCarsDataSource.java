package com.cardee.data_source;


import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

public interface OwnerCarsDataSource {


    void obtainCars(Callback callback);


    interface Callback {
        void onSuccess(CarEntity[] carEntities);

        void onError(Error error);
    }
}
