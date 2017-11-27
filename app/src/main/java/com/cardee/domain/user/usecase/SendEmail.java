package com.cardee.domain.user.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.domain.UseCase;

import io.reactivex.annotations.NonNull;


public class SendEmail implements UseCase<SendEmail.RequestValues, SendEmail.ResponseValues> {

    private UserDataSource mRepository;

    public SendEmail() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(SendEmail.RequestValues values, final Callback callback) {

        if (values.getEmail() == null) {
            callback.onError(null);
            return;
        }

        mRepository.sendEmailToChangePassword(values.getEmail(), new UserDataSource.Callback() {
            @Override
            public void onSuccess(boolean success) {
                callback.onSuccess(new SendEmail.ResponseValues(success));
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
