package com.cardee.data_source.cache;

import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody;
import com.cardee.domain.bookings.entity.BookCarState;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.domain.renter.entity.FilterRequest;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class LocalRenterCarsDataSource implements RenterCarsDataSource {

    private static LocalRenterCarsDataSource INSTANCE;
    private BrowseCarsFilter mFilter;
    private BookCarState mBookState;
    private OfferByIdResponseBody mOfferByIdResponseBody;

    private LocalRenterCarsDataSource() {
        mFilter = new BrowseCarsFilter();
        mBookState = new BookCarState();
    }

    public static LocalRenterCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalRenterCarsDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void obtainCars(OffersCallback offersCallback) {

    }

    @Override
    public Disposable obtainCarsByFilter(FilterRequest filter, OffersCallback callback) {
        return null;
    }

    @Override
    public void addCarToFavorites(int carId, NoDataCallback callback) {

    }

    @Override
    public void getFavorites(boolean isFavorite, OffersCallback offersCallback) {

    }

    @Override
    public void searchCars(String searchCriteria, OffersCallback offersCallback) {

    }

    @Override
    public void saveFilter(BrowseCarsFilter filter) {
        mFilter = filter;
    }

    @Override
    public BrowseCarsFilter getFilter() {
        return mFilter;
    }

    @Override
    public Disposable getOfferById(int id, OfferCallback offerCallback) {
        return Observable.just(mOfferByIdResponseBody == null ? new OfferByIdResponseBody() : mOfferByIdResponseBody)
                .subscribeWith(new DisposableObserver<OfferByIdResponseBody>() {
                    @Override
                    public void onNext(OfferByIdResponseBody offerByIdResponseBody) {
                        offerCallback.onSuccess(offerByIdResponseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveOfferById(OfferByIdResponseBody responseBody) {
        mOfferByIdResponseBody = responseBody;
    }

    @Override
    public BookCarState getBookState() {
        return mBookState;
    }

    @Override
    public void saveBookState(BookCarState state) {
        mBookState = state;
    }

    public void getSorted(String sortBy, OffersCallback offersCallback) {

    }
}
