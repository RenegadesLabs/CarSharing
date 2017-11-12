package com.cardee.domain.owner.usecase;


import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.entity.mapper.CarDetailsToCarDataMapper;

public class GetCarData implements UseCase<GetCarData.RequestValues, GetCarData.ResponseValues> {

    private final OwnerCarRepository repository;
    private final CarDetailsToCarDataMapper mapper;

    public GetCarData() {
        repository = OwnerCarRepository.getInstance();
        mapper = new CarDetailsToCarDataMapper();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getId();
        repository.obtainCar(id, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                ResponseValues responseValues = new ResponseValues(mapper.transform(carResponse.getCarDetails()));
                callback.onSuccess(responseValues);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int id;

        public RequestValues(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final CarData carData;

        public ResponseValues(CarData carData) {
            this.carData = carData;
        }

        public CarData getCarData() {
            return carData;
        }
    }
}
