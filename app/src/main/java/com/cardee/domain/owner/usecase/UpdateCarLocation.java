package com.cardee.domain.owner.usecase;


import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.entity.mapper.NewCarDataMapper;

public class UpdateCarLocation implements UseCase<UpdateCarLocation.RequestValues, UpdateCarLocation.ResponseValues> {

    private final CarEditRepository repository;
    private final NewCarDataMapper mapper;

    public UpdateCarLocation() {
        repository = CarEditRepository.getInstance();
        mapper = new NewCarDataMapper();
    }


    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        repository.updateLocation(values.getCarId(),
                mapper.transform(values.getCarData()),
                new CarEditDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Integer carId;
        private final CarData carData;

        public RequestValues(Integer carId, CarData carData) {
            this.carId = carId;
            this.carData = carData;
        }

        public CarData getCarData() {
            return carData;
        }

        public Integer getCarId() {
            return carId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean success;

        public ResponseValues(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
