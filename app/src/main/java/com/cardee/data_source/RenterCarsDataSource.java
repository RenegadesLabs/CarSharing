package com.cardee.data_source;


import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;

public interface RenterCarsDataSource {

    void obtainCars(OffersCallback offersCallback);

    void addCarToFavorites(int carId, Callback callback);

    void getFavorites(boolean isFavorite, OffersCallback offersCallback);

    void searchCars(String searchCriteria, OffersCallback offersCallback);

    interface OffersCallback extends Callback {
        void onSuccess(OfferResponseBody[] response);

        void onError(Error error);
    }

    interface Callback {
        void onSuccess();
        void onError(Error error);
    }
}
