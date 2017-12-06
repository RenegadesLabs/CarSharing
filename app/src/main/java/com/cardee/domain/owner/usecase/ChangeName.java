package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource;
import com.cardee.data_source.remote.api.profile.request.ChangeNameRequest;
import com.cardee.domain.UseCase;

public class ChangeName implements UseCase<ChangeName.RequestValues, ChangeName.ResponseValues> {
    private final OwnerProfileDataSource mRepository;

    public ChangeName() {
        mRepository = RemoteOwnerProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        ChangeNameRequest request = new ChangeNameRequest();
        request.setName(values.getNewName());
        mRepository.changeName(request, new OwnerProfileDataSource.NoResponseCallback() {
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
        String newName;

        public RequestValues(String newName) {
            this.newName = newName;
        }

        public String getNewName() {
            return newName;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
