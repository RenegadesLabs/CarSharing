package com.cardee.domain.owner.usecase;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;

import java.io.File;

public class Register implements UseCase<Register.RequestValues, Register.ResponseValues> {

    private UserDataSource mRepository;

    public Register() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {

        if (values.getLogin() == null || values.getPassword() == null
                || values.getImage() == null || values.getUserName() == null) {
            return;
        }

        mRepository.register(values, new UserDataSource.Callback() {
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

    public static class RequestValues implements UseCase.RequestValues {
        private final String mLogin;
        private final String mPassword;
        private final File mImage;
        private final String mUserName;

        public RequestValues(@NonNull String login, @NonNull String password, @NonNull File image, @NonNull String userName) {
            mLogin = login;
            mPassword = password;
            mImage = image;
            mUserName = userName;
        }

        public String getLogin() {
            return mLogin;
        }

        public String getPassword() {
            return mPassword;
        }

        public File getImage() {
            return mImage;
        }

        public String getUserName() {
            return mUserName;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private Boolean mSuccess;

        public ResponseValues(Boolean success) {
            mSuccess = success;
        }

        public boolean isSuccess() {
            return mSuccess != null && mSuccess;
        }
    }
}
