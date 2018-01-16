package com.cardee.data_source;

import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;

public interface RenterProfileDataSource {

    void loadRenterProfile(RenterProfileDataSource.ProfileCallback callback);

    void changeRenterNote(ChangeNoteRequest changeNoteRequest, NoResponseCallback callback);

    void loadRenterById(int id, ProfileCallback profileCallback);

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
