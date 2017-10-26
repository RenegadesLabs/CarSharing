package com.cardee.auth.register;

import com.cardee.mvp.BaseView;

public interface RegisterContract {

    interface RegisterPresenter {

    }

    interface RegisterView extends BaseView {

        void onLogin();

        void onSignUp();

        void onTakePhoto();

        void onFacebook();

        void onGoogle();

        void onSignUpAsRenter();

        void onSignUpAsOwner();

        void onBackToFirstStep();

        void onTermsOfService();
    }
}
