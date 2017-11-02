package com.cardee.domain.owner.usecase;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;

public class CheckUniqueLogin implements UseCase<CheckUniqueLogin.RequestValues, CheckUniqueLogin.ResponseValues> {

    private UserDataSource mRepository;

    public CheckUniqueLogin() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        String login = values.getLogin();
        String password = values.getPassword();

        if (login == null || password == null) {
            return;
        }

        mRepository.checkUniqueLogin(login, password, new UserDataSource.Callback() {
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

        public RequestValues(@NonNull String login, @NonNull String password) {
            mLogin = login;
            mPassword = password;
        }

        public String getLogin() {
            return mLogin;
        }

        public String getPassword() {
            return mPassword;
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
