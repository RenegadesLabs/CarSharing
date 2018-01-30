package com.cardee.data_source;


import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.domain.renter.entity.FilterRequest;

import io.reactivex.disposables.Disposable;

public interface RenterCarsDataSource {

    void obtainCars(OffersCallback offersCallback);

    Disposable obtainCarsByFilter(FilterRequest filter, OffersCallback callback);

    void addCarToFavorites(int carId, NoDataCallback callback);

    void getFavorites(boolean isFavorite, OffersCallback offersCallback);

    void searchCars(String searchCriteria, OffersCallback offersCallback);

    void getSorted(String sortBy, OffersCallback offersCallback);

    void saveFilter(BrowseCarsFilter filter);

    BrowseCarsFilter getFilter();

    interface OffersCallback extends Callback {
        void onSuccess(OfferResponseBody[] response);
    }

    interface Callback {
        void onError(Error error);
    }

    interface NoDataCallback extends Callback {
        void onSuccess();
    }
}
