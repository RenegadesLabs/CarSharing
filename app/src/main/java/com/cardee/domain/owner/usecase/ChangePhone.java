package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.request.ChangePhoneRequest;
import com.cardee.domain.UseCase;

public class ChangePhone implements UseCase<ChangePhone.RequestValues, ChangePhone.ResponseValues> {
    private final OwnerProfileDataSource mRepository;

    public ChangePhone() {
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        ChangePhoneRequest request = new ChangePhoneRequest();
        request.setPhone(values.getNewPhone());
        mRepository.changePhone(request, new OwnerProfileDataSource.NoResponseCallback() {
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
        String newPhone;

        public RequestValues(String newName) {
            this.newPhone = newName;
        }

        public String getNewPhone() {
            return newPhone;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
