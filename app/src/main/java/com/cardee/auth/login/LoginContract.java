package com.cardee.auth.login;

import com.cardee.mvp.BaseView;

public interface LoginContract {

    interface LoginPresenter {

    }

    interface LoginView extends BaseView {
        void onLogin();

        void onFacebook();

        void onGoogle();
    }
}
