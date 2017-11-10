package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.NewCarRepository;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.NewCar;
import com.cardee.domain.owner.entity.mapper.NewCarDataMapper;

public class GetSavedCar implements UseCase<GetSavedCar.RequestValues, GetSavedCar.ResponseValues> {

    private final NewCarRepository repository;
    private final NewCarDataMapper mapper;

    public GetSavedCar() {
        repository = NewCarRepository.getInstance();
        mapper = new NewCarDataMapper();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        repository.obtainSavedCarData(new NewCarDataSource.CacheCallback() {
            @Override
            public void onSuccess(NewCarData carData) {
                callback.onSuccess(new ResponseValues(mapper.reverse(carData)));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final NewCar carData;

        public ResponseValues(NewCar carData) {
            this.carData = carData;
        }

        public NewCar getCarData() {
            return carData;
        }
    }
}
