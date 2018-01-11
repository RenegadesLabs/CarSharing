package com.cardee.domain.renter.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RenterProfileDataSource;
import com.cardee.data_source.remote.RemoteRenterProfileDataSource;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.cardee.domain.UseCase;

public class GetRenterById implements UseCase<GetRenterById.RequestValues, GetRenterById.ResponseValues> {

    private final RenterProfileDataSource mRepository;

    public GetRenterById() {
        mRepository = RemoteRenterProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.loadRenterById(values.getId(), new RenterProfileDataSource.ProfileCallback() {
            @Override
            public void onSuccess(RenterProfile renterProfile) {
                callback.onSuccess(new GetRenterById.ResponseValues(renterProfile));
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
        private final RenterProfile renterProfile;

        ResponseValues(RenterProfile ownerProfile) {
            this.renterProfile = ownerProfile;
        }

        public RenterProfile getRenterProfile() {
            return renterProfile;
        }
    }
}
