package com.cardee.data_source;

import com.cardee.data_source.remote.api.profile.request.ChangeEmailRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNameRequest;
import com.cardee.data_source.remote.api.profile.request.ChangePhoneRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.request.PassChangeRequest;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;

public interface OwnerProfileDataSource {

    void loadOwnerProfile(ProfileCallback callback);

    void getOwnerProfileById(int profileId, ProfileCallback callback);

    void changeNote(ChangeNoteRequest changeNoteRequest, NoResponseCallback callback);

    void changePassword(PassChangeRequest request, NoResponseCallback callback);

    void changeName(ChangeNameRequest request, NoResponseCallback callback);

    void changeEmail(ChangeEmailRequest request, NoResponseCallback callback);

    void changePhone(ChangePhoneRequest request, NoResponseCallback callback);

    interface ProfileCallback extends BaseCallback {
        void onSuccess(OwnerProfile ownerProfile);
    }

    interface NoResponseCallback extends BaseCallback {
        void onSuccess();
    }

    interface BaseCallback {
        void onError(Error error);
    }
}
