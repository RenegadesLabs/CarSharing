package com.cardee.data_source;

import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.renter.entity.FilterRequest;

public interface RenterCarsDataSource {

    void obtainCars(Callback callback);

    void obtainCarsByFilter(FilterRequest filter, Callback callback);

    interface Callback {
        void onSuccess(OfferResponseBody[] response);

        void onError(Error error);
    }
}
