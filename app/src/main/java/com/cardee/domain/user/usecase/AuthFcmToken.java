package com.cardee.domain.user.usecase;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.UserDataSource;
import com.cardee.data_source.UserRepository;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;

public class AuthFcmToken implements UseCase<AuthFcmToken.RequestValues, AuthFcmToken.ResponseValues> {

    private final UserRepository mRepository;

    public AuthFcmToken() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.sendFcmToken(values.getFcmToken(), new UserDataSource.Callback() {
            @Override
            public void onSuccess(boolean success) {
                AccountManager.getInstance(CardeeApp.context).saveFcmAuthAction();
                callback.onSuccess(new ResponseValues(true));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final String mFcmToken;

        public RequestValues(String fcmToken) {
            mFcmToken = fcmToken;
        }

        public String getFcmToken() {
            return mFcmToken;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final boolean mIsFcmAuthenticated;

        public ResponseValues(boolean isFcmAuthenticated) {
            mIsFcmAuthenticated = isFcmAuthenticated;
        }

        public boolean isFcmAuthenticated() {
            return mIsFcmAuthenticated;
        }
    }

}
