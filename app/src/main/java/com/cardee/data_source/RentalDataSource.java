package com.cardee.data_source;


import com.cardee.data_source.remote.api.cars.request.DailyRentalDetails;

public interface RentalDataSource {

    void saveDailyRentalDetail(int id, DailyRentalDetails rentalDetails, Callback callback);

    interface Callback {
        void onSuccess();

        void onError(Error error);
    }
}
