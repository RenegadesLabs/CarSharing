package com.cardee.domain.user.usecase;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;


public class ChangePassword implements UseCase<ChangePassword.RequestValues, ChangePassword.ResponseValues> {

    private UserDataSource mRepository;

    public ChangePassword() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback callback) {

        String key = values.getKey();
        String pass = values.getPassword();
        String passConfirm = values.getPasswordConfirm();

        if (key == null || pass == null
                || passConfirm == null) {
            return;
        }

        mRepository.changePassword(key, pass, passConfirm, new UserDataSource.Callback() {
            @Override
            public void onSuccess(boolean success) {
                callback.onSuccess(new ResponseValues(success));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final String key;
        private final String password;
        private final String passwordConfirm;

        public RequestValues(@NonNull String key, @NonNull String password, @NonNull String passwordConfirm) {
            this.key = key;
            this.password = password;
            this.passwordConfirm = passwordConfirm;
        }

        public String getKey() {
            return key;
        }
        public String getPassword() {
            return password;
        }
        public String getPasswordConfirm() {
            return passwordConfirm;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final Boolean success;

        public ResponseValues(boolean success) {
            this.success = success;
        }
        public Boolean getSuccess() {
            return success;
        }
    }
}
