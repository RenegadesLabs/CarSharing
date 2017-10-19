package com.cardee.auth.register;

import com.cardee.mvp.BaseView;

public interface RegisterContract {

    interface RegisterPresenter {

    }

    interface RegisterView extends BaseView {

        void onLogin();

        void onSignUp();

        void onFacebook();

        void onGoogle();

        void onBackToFirstStep();
    }
}
