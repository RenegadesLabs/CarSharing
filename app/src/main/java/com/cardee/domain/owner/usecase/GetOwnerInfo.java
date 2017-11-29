package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.mapper.CarEntityToCarMapper;

import java.util.List;

public class GetOwnerInfo implements UseCase<GetOwnerInfo.RequestValues, GetOwnerInfo.ResponseValues> {

    private final OwnerProfileDataSource mRepository;
    private final CarEntityToCarMapper mMapper;

    public GetOwnerInfo() {
        mMapper = new CarEntityToCarMapper();
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.loadOwnerProfile(new OwnerProfileDataSource.Callback() {
            @Override
            public void onSuccess(OwnerProfile ownerProfile) {
                CarEntity[] carEntities = ownerProfile.getCars();
                List<Car> cars = mMapper.transform(carEntities);
                GetOwnerInfo.ResponseValues responseValues = new GetOwnerInfo.ResponseValues(ownerProfile, cars);

                callback.onSuccess(responseValues);
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
        private final OwnerProfile ownerProfile;
        private final List<Car> mCars;

        ResponseValues(OwnerProfile ownerProfile, List<Car> mCars) {
            this.ownerProfile = ownerProfile;
            this.mCars = mCars;
        }

        public OwnerProfile getOwnerProfile() {
            return ownerProfile;
        }

        public List<Car> getCars() {
            return mCars;
        }
    }

}
