package com.cardee.data_source;

import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;

public interface RenterProfileDataSource {

    void loadRenterProfile(RenterProfileDataSource.ProfileCallback callback);

    interface ProfileCallback extends RenterProfileDataSource.BaseCallback {
        void onSuccess(RenterProfile renterProfile);
    }

    interface NoResponseCallback extends RenterProfileDataSource.BaseCallback {
        void onSuccess();
    }

    interface BaseCallback {
        void onError(Error error);
    }
}
