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

public class GetOwnerInfoById implements UseCase<GetOwnerInfoById.RequestValues, GetOwnerInfoById.ResponseValues> {

    private final OwnerProfileDataSource mRepository;
    private final CarEntityToCarMapper mMapper;

    public GetOwnerInfoById() {
        mMapper = new CarEntityToCarMapper();
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.getOwnerProfileById(values.getProfileId(), new OwnerProfileDataSource.ProfileCallback() {
            @Override
            public void onSuccess(OwnerProfile ownerProfile) {
                CarEntity[] carEntities = ownerProfile.getCars();
                List<Car> cars = mMapper.transform(carEntities);
                ResponseValues responseValues = new ResponseValues(ownerProfile, cars);
                callback.onSuccess(responseValues);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        int profileId;

        public RequestValues(int profileId) {
            this.profileId = profileId;
        }

        public int getProfileId() {
            return profileId;
        }
    }

    public static class ResponseValues extends GetOwnerInfo.ResponseValues {
        ResponseValues(OwnerProfile ownerProfile, List<Car> mCars) {
            super(ownerProfile, mCars);
        }
    }
}
