package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarsDataSource;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.mapper.CarEntityToCarMapper;

import java.util.List;

public class GetCars implements UseCase<GetCars.RequestValues, GetCars.ResponseValues> {

    private final CarEntityToCarMapper mMapper;
    private final OwnerCarsRepository mRepository;

    public GetCars() {
        mMapper = new CarEntityToCarMapper();
        mRepository = OwnerCarsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback callback) {
        boolean forceRefresh = values.needForceRefresh();
        if (forceRefresh) {
            mRepository.refreshCars();
        }
        mRepository.obtainCars(new OwnerCarsDataSource.Callback() {
            @Override
            public void onSuccess(CarEntity[] carEntities) {
                List<Car> cars = mMapper.transform(carEntities);
                ResponseValues responseValues = new ResponseValues(cars);
                callback.onSuccess(responseValues);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }


    public static class RequestValues implements UseCase.RequestValues {

        private final boolean mForceRefresh;

        public RequestValues(boolean forceRefresh) {
            mForceRefresh = forceRefresh;
        }

        public boolean needForceRefresh() {
            return mForceRefresh;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Car> mCars;

        public ResponseValues(List<Car> cars) {
            mCars = cars;
        }

        public List<Car> getCars() {
            return mCars;
        }

        public boolean isSuccessful() {
            return mCars != null;
        }
    }
}
