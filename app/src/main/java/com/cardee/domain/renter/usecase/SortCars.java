package com.cardee.domain.renter.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.RenterCarsRepository;
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.domain.renter.entity.mapper.OfferResponseBodyToOfferMapper;

import java.util.List;


public class SortCars implements UseCase<SortCars.RequestValues, SortCars.ResponseValues> {

    private final RenterCarsRepository mRepository;

    public SortCars() {
        mRepository = RenterCarsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.getSorted(values.getSortBy(), new RenterCarsDataSource.OffersCallback() {
            @Override
            public void onSuccess(OfferResponseBody[] response) {
                callback.onSuccess(new ResponseValues(OfferResponseBodyToOfferMapper.transform(response)));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final String mSortBy;

        public RequestValues(String sortBy) {
            mSortBy = sortBy;
        }

        public String getSortBy() {
            return mSortBy;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final List<OfferCar> mOfferCar;

        public ResponseValues(List<OfferCar> offerCar) {
            mOfferCar = offerCar;
        }

        public List<OfferCar> getOfferCars() {
            return mOfferCar;
        }
    }
}
