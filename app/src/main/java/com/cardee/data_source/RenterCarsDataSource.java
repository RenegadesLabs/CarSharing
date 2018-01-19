package com.cardee.data_source;

import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.data_source.remote.api.offers.response.OffersResponse;

public interface RenterCarsDataSource {

    void obtainCars(Callback callback);

    interface Callback {
        void onSuccess(OfferResponseBody[] response);

        void onError(Error error);
    }
}
