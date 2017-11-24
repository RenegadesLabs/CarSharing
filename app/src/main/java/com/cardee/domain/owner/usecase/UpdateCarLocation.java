package com.cardee.domain.owner.usecase;


import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.CarData;

public class UpdateCarLocation implements UseCase<UpdateCarLocation.RequestValues, UpdateCarLocation.ResponseValues> {


    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Integer carId;
        private final CarData carData;

        public RequestValues(Integer carId, CarData carData) {
            this.carId = carId;
            this.carData = carData;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
