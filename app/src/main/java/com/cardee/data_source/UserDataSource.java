package com.cardee.data_source;

import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;

public interface UserDataSource {

    void login(String login, String password, Callback callback);

    void loginSocial(SocialLoginRequest.Provider provider, String token, Callback callback);

    interface Callback {

        void onSuccess(boolean success);

        void onError(String message);
    }
}
