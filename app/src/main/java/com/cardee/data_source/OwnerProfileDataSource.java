package com.cardee.data_source;

import com.cardee.data_source.remote.api.profile.request.OwnerNoteRequest;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;

public interface OwnerProfileDataSource {

    void loadOwnerProfile(ProfileCallback callback);

    void changeNote(OwnerNoteRequest ownerNoteRequest, OnChangeNoteCallback callback);

    interface ProfileCallback extends BaseCallback {
        void onSuccess(OwnerProfile ownerProfile);
    }

    interface OnChangeNoteCallback extends BaseCallback {
        void onSuccess();
    }

    interface BaseCallback {
        void onError(Error error);
    }
}
