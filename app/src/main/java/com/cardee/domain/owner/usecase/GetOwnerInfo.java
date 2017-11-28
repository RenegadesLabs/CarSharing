package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.domain.UseCase;

public class GetOwnerInfo implements UseCase<GetOwnerInfo.RequestValues, GetOwnerInfo.ResponseValues> {

    private OwnerProfileDataSource mRepository;

    public GetOwnerInfo() {
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.loadOwnerProfile(new OwnerProfileDataSource.Callback() {
            @Override
            public void onSuccess(OwnerProfile ownerProfile) {
                callback.onSuccess(new ResponseValues(ownerProfile));
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
        private OwnerProfile ownerProfile;

        ResponseValues(OwnerProfile ownerProfile) {
            this.ownerProfile = ownerProfile;
        }

        public OwnerProfile getOwnerProfile() {
            return ownerProfile;
        }
    }

}
