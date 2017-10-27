package com.cardee.auth.register.view;


import com.cardee.mvp.BaseView;

public interface RegisterView extends BaseView {
    void onLogin();

    void onSignUp(String login, String password);

    void onFacebook();

    void onGoogle();

    void onTermsOfService();

    void onTakePhoto();

    void onBackToFirstStep();

    void onSignUpAsOwner();

    void onSignUpAsRenter();

    void onValidationSuccess();

    void onRegistrationSuccess();
}
