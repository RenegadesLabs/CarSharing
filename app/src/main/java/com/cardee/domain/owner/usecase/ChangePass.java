package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.request.PassChangeRequest;
import com.cardee.domain.UseCase;

public class ChangePass implements UseCase<ChangePass.RequestValues, ChangePass.ResponseValues> {
    private final OwnerProfileDataSource mRepository;

    public ChangePass() {
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        PassChangeRequest request = new PassChangeRequest();
        request.setOldPass(values.getOldPass());
        request.setNewPass(values.getNewPass());
        mRepository.changePassword(request, new OwnerProfileDataSource.NoResponseCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(null);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final String oldPass;
        private final String newPass;

        public RequestValues(String oldPass, String newPass) {
            this.oldPass = oldPass;
            this.newPass = newPass;
        }

        public String getOldPass() {
            return oldPass;
        }

        public String getNewPass() {
            return newPass;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
