package com.cardee.domain.owner.usecase;

import android.support.annotation.NonNull;

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

        SocialLoginRequest.Provider provider = values.getProvider();
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
            public void onError(String message) {
                callback.onError(null);
            }
        });

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final String mToken;
        private final SocialLoginRequest.Provider mProvider;

        public RequestValues(@NonNull SocialLoginRequest.Provider provider, @NonNull String token) {
            mToken = token;
            mProvider = provider;
        }

        public String getToken() {
            return mToken;
        }

        public SocialLoginRequest.Provider getProvider() {
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
