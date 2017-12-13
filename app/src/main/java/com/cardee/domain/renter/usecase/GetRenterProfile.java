package com.cardee.domain.renter.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RenterProfileDataSource;
import com.cardee.data_source.remote.RemoteRenterProfileDataSource;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.cardee.domain.UseCase;

public class GetRenterProfile implements UseCase<GetRenterProfile.RequestValues, GetRenterProfile.ResponseValues> {

    private final RenterProfileDataSource mRepository;

    public GetRenterProfile() {
        mRepository = RemoteRenterProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.loadRenterProfile(new RenterProfileDataSource.ProfileCallback() {
            @Override
            public void onSuccess(RenterProfile renterProfile) {
                callback.onSuccess(new ResponseValues(renterProfile));
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
        private final RenterProfile renterProfile;

        ResponseValues(RenterProfile ownerProfile) {
            this.renterProfile = ownerProfile;
        }

        public RenterProfile getRenterProfile() {
            return renterProfile;
        }
    }
}
