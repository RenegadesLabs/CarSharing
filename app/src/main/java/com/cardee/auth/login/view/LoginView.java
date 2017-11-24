package com.cardee.auth.login.view;

import com.cardee.mvp.BaseView;

public interface LoginView extends BaseView {

    void onLoginSuccess();

    void onProceedGoogleLogin(String accessToken);

}
