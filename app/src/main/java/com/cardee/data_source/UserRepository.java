package com.cardee.data_source;


import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.auth.Authentication;
import com.cardee.data_source.remote.api.auth.request.LoginRequest;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;
import com.cardee.domain.owner.usecase.SocialLogin;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;


public class UserRepository implements UserDataSource {

    public final static String WRONG_CREDENTIALS = "HTTP 422 UNPROCESSABLE ENTITY";

    private static UserRepository INSTANCE;
    private Authentication api;

    private UserRepository() {
        api = CardeeApp.retrofit.create(Authentication.class);
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    @Override
    public void login(String login, String password, final UserDataSource.Callback callback) {

        LoginRequest req = new LoginRequest();
        req.setLogin(login);
        req.setPassword(password);
        Observable<BaseAuthResponse> ob = api.login(req);
        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
            @Override
            public void onNext(BaseAuthResponse baseAuthResponse) {
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                if (WRONG_CREDENTIALS.equals(e.getMessage())) {
                    callback.onError(new Error(Error.Type.WRONG_CREDENTIALS, e.getMessage()));
                    return;
                }
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void loginSocial(SocialLoginRequest.Provider provider, String token, final Callback callback) {

        SocialLoginRequest req = new SocialLoginRequest();
        req.setProvider(provider);
        req.setToken(token);
        Observable<BaseAuthResponse> ob = api.loginSocial(req);
        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
            @Override
            public void onNext(BaseAuthResponse baseAuthResponse) {
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                if (WRONG_CREDENTIALS.equals(e.getMessage())) {
                    callback.onError(new Error(Error.Type.WRONG_CREDENTIALS, e.getMessage()));
                    return;
                }
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
