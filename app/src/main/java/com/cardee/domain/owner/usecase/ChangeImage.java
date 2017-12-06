package com.cardee.domain.owner.usecase;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;

import java.io.File;

public class ChangeImage implements UseCase<ChangeImage.RequestValues, ChangeImage.ResponseValues> {

    private UserDataSource mRepository;

    public ChangeImage() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.setProfilePicture(values, new UserDataSource.Callback() {
            @Override
            public void onSuccess(boolean success) {
                callback.onSuccess(new ResponseValues(success));
            }

            @Override
            public void onError(Error error) {
                callback.onError(new Error(error.getErrorType(), error.getMessage()));
            }
        });
    }

    public static class RequestValues extends Register.RequestValues {
        public RequestValues(String login, String password, @NonNull File image, String userName) {
            super(login, password, image, userName);
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private boolean mSuccess;

        public ResponseValues(boolean success) {
            mSuccess = success;
        }

        public boolean isSuccess() {
            return mSuccess;
        }
    }
}
