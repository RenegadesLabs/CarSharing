package com.cardee.domain.user.usecase;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.domain.UseCase;

public class SocialLogin implements UseCase<SocialLogin.RequestValues, SocialLogin.ResponseValues> {

    private UserDataSource mRepository;

    public SocialLogin() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(SocialLogin.RequestValues values, final Callback<SocialLogin.ResponseValues> callback) {

        String provider = values.getProvider();
        String token = values.getToken();
        /*"ya29.GlvwBDMSXnqNLg2IDIE5ZWdsmygKvFwqSQBbjWdC2wmBqJTiOfKFyzVIFDd9WGZ6fGtU585EcCttee_vodGeaaY_swQ-uMwt4iwIoGxVU5zPxuB-1YxBV92yMhrg"*/


        if (provider == null || token == null) {
            callback.onError(null);
        }

        mRepository.loginSocial(provider, token, new UserDataSource.Callback() {
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
        private final String mToken;
        private final String mProvider;

        public RequestValues(@NonNull String provider, @NonNull String token) {
            mToken = token;
            mProvider = provider;
        }

        public String getToken() {
            return mToken;
        }

        public String getProvider() {
            return mProvider;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final Boolean mSuccess;

        ResponseValues(Boolean success) {
            mSuccess = success;
        }

        public boolean isSuccess() {
            return mSuccess != null && mSuccess;
        }
    }
}
