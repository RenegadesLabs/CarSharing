package com.cardee.auth.register.presenter;


import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.auth.register.view.RegisterView;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.CheckUniqueLogin;
import com.cardee.domain.owner.usecase.Register;
import com.cardee.domain.user.usecase.SocialLogin;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class RegisterPresenter {

    private RegisterView mView;

    private UseCaseExecutor mExecutor;

    public RegisterPresenter(RegisterView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    public void checkUniqueLogin(final String login, final String password) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new CheckUniqueLogin(), new CheckUniqueLogin.RequestValues(login, password),
                new UseCase.Callback<CheckUniqueLogin.ResponseValues>() {
                    @Override
                    public void onSuccess(CheckUniqueLogin.ResponseValues response) {
                        mView.showProgress(false);
                        mView.onValidationSuccess(login, password);
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }

    public void signUp(String login, String password, String name, File picture, final AccountManager.ACC_STATE accState) {
        mView.showProgress(true);
        mExecutor.execute(new Register(), new Register.RequestValues(login, password, picture, name),
                new UseCase.Callback<Register.ResponseValues>() {
                    @Override
                    public void onSuccess(Register.ResponseValues response) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.signup_registration_success);
                        mView.onRegistrationSuccess(accState);
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }

    public void registerSocial(String provider, String token) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new SocialLogin(), new SocialLogin.RequestValues(provider, token), new UseCase.Callback<SocialLogin.ResponseValues>() {
            @Override
            public void onSuccess(SocialLogin.ResponseValues response) {
                if (response.isSuccess()) {
                    mView.showProgress(false);

                    // TODO: implement for renter also;
                    mView.onRegistrationSuccess(AccountManager.ACC_STATE.OWNER);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(R.string.auth_error);
            }
        });
    }

    public void loginGoogle(GoogleSignInResult result) {
        GoogleSignInAccount acc = result.getSignInAccount();
        if (acc != null) {
            String code = acc.getServerAuthCode();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("grant_type", "authorization_code")
                    .add("client_id", "223677953401-12ltvoram5qhn2bva09bk46fmaopha20.apps.googleusercontent.com")
                    .add("code", code)
                    .add("redirect_uri", "")
                    .add("client_secret", "37ZieJoiEydLH1zdacuyvI2B")
                    .build();
            final Request request = new Request.Builder()
                    .url("https://www.googleapis.com/oauth2/v4/token")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Request request, final IOException e) {

                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        mView.onProceedGoogleLogin(jsonObject.getString("access_token"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setAccountState(AccountManager.ACC_STATE state) {
        AccountManager.getInstance(CardeeApp.context).setCurrentState(state);
    }
}
