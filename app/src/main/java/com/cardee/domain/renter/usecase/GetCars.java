package com.cardee.domain.renter.usecase;


import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.RenterCarsRepository;
import com.cardee.data_source.remote.api.offers.response.OffersResponse;
import com.cardee.domain.UseCase;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.domain.renter.entity.mapper.OfferResponseBodyToOfferMapper;

import java.util.List;

public class GetCars implements UseCase<GetCars.RequestValues, GetCars.ResponseValues> {

    private RenterCarsRepository mRepository;

    public GetCars() {
        mRepository = RenterCarsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.obtainCars(new RenterCarsDataSource.Callback() {
            @Override
            public void onSuccess(OffersResponse response) {
                callback.onSuccess(new ResponseValues(response.isSuccessful(),
                        OfferResponseBodyToOfferMapper.transform(response.getOfferResponseBody())));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });

    }

    public static class RequestValues implements UseCase.RequestValues {

        public RequestValues() {
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final boolean success;
        private final List<OfferCar> mOfferCar;

        public ResponseValues(boolean success, List<OfferCar> offerCar) {
            this.success = success;
            mOfferCar = offerCar;
        }

        public boolean isSuccess() {
            return success;
        }

        public List<OfferCar> getOfferCars() {
            return mOfferCar;
        }
    }
}
