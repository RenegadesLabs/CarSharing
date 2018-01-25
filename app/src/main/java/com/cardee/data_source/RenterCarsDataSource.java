package com.cardee.data_source;


import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.renter.entity.FilterRequest;

import io.reactivex.disposables.Disposable;

public interface RenterCarsDataSource {

    void obtainCars(OffersCallback offersCallback);

    Disposable obtainCarsByFilter(FilterRequest filter, Callback callback);

    interface Callback {
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
