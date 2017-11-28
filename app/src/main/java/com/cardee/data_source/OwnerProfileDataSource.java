package com.cardee.data_source;

import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;

public interface OwnerProfileDataSource {

    void loadOwnerProfile(Callback callback);

    interface Callback {
        void onSuccess(OwnerProfile ownerProfile);

        void onError(Error error);
    }
}
