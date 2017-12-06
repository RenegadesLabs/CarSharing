package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.request.ChangeEmailRequest;
import com.cardee.domain.UseCase;

public class ChangeEmail implements UseCase<ChangeEmail.RequestValues, ChangeEmail.ResponseValues> {

    private final OwnerProfileDataSource mRepository;

    public ChangeEmail() {
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setEmail(values.getNewEmail());
        mRepository.changeEmail(request, new OwnerProfileDataSource.NoResponseCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues());
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        String newEmail;

        public RequestValues(String newName) {
            this.newEmail = newName;
        }

        public String getNewEmail() {
            return newEmail;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
