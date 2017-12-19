package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.domain.UseCase;

public class GetCarDescription
        implements UseCase<GetCarDescription.RequestValues, GetCarDescription.ResponseValues> {

    private final OwnerCarRepository repository;

    public GetCarDescription() {
        repository = OwnerCarRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int carId = values.getCarId();
        if (carId == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + carId));
            return;
        }
        repository.obtainCar(carId, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                CarDetailsEntity details = carResponse.getCarDetails();
                if (details == null) {
                    callback.onError(new Error(Error.Type.SERVER, "Empty response"));
                    return;
                }
                callback.onSuccess(new ResponseValues(details.getDescription()));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int carId;

        public RequestValues(int carId) {
            this.carId = carId;
        }

        public int getCarId() {
            return carId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final String carDescription;

        public ResponseValues(String carDescription) {
            this.carDescription = carDescription;
        }

        public String getCarDescription() {
            return carDescription;
        }
    }
}
