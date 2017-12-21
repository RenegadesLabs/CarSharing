package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.mapper.CarResponseToCarMapper;

public class GetCar implements UseCase<GetCar.RequestValues, GetCar.ResponseValues> {

    private final OwnerCarRepository mRepository;
    private final CarResponseToCarMapper mMapper;

    public GetCar() {
        mRepository = OwnerCarRepository.getInstance();
        mMapper = new CarResponseToCarMapper();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.OTHER, "Wrong id: " + id));
            return;
        }

        mRepository.obtainCar(id, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                ResponseValues responseValues = new ResponseValues(mMapper.transform(carResponse));
                callback.onSuccess(responseValues);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int mId;

        public RequestValues(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final Car mCar;

        public ResponseValues(Car car) {
            mCar = car;
        }

        public Car getCar() {
            return mCar;
        }

        public boolean isSuccessful() {
            return mCar != null;
        }
    }
}
