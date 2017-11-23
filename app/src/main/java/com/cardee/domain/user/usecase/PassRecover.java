package com.cardee.domain.user.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;

import io.reactivex.annotations.NonNull;


public class PassRecover implements UseCase<PassRecover.RequestValues, PassRecover.ResponseValues> {

    private UserDataSource mRepository;

    public PassRecover() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(PassRecover.RequestValues values, final Callback callback) {

        if (values.getEmail() == null) {
            callback.onError(null);
            return;
        }

        mRepository.forgotPassword(values.getEmail(), new UserDataSource.Callback() {
            @Override
            public void onSuccess(boolean success) {
                callback.onSuccess(new PassRecover.ResponseValues(success));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });

    }

    public static class RequestValues implements UseCase.RequestValues {

        private final String email;

        public String getEmail() {
            return email;
        }

        public RequestValues(@NonNull String email) {
            this.email = email;
        }

    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private Boolean success;

        public ResponseValues(@NonNull boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
