package com.cardee.domain.renter.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.RenterCarsRepository;
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.domain.renter.entity.mapper.OfferResponseBodyToOfferMapper;

import java.util.List;


public class SearchCars implements UseCase<SearchCars.RequestValues, SearchCars.ResponseValues> {

    private final RenterCarsRepository mRepository;

    public SearchCars() {
        mRepository = RenterCarsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.searchCars(values.getSearchCriteria(), new RenterCarsDataSource.OffersCallback() {
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
        private final String mSearchCriteria;

        public RequestValues(String searchCriteria) {
            mSearchCriteria = searchCriteria;
        }

        public String getSearchCriteria() {
            return mSearchCriteria;
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
