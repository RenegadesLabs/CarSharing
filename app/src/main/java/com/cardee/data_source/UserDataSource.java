package com.cardee.data_source;

import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.domain.owner.usecase.Register;

public interface UserDataSource {

    void login(String login, String password, Callback callback);

    void loginSocial(SocialLoginRequest.Provider provider, String token, Callback callback);

    void checkUniqueLogin(String login, String password, Callback callback);

    void register(Register.RequestValues registerValues, Callback callback);

    interface Callback {

        void onSuccess(boolean success);

        void onError(Error error);
    }
}
