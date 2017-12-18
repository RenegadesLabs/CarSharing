package com.cardee.auth.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.auth.login.view.LoginView;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.user.usecase.AuthFcmToken;
import com.cardee.domain.user.usecase.Login;
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

import java.io.IOException;

public class LoginPresenter {

    private final Login mLoginUseCase;
    private final AuthFcmToken mAuthFcmUseCase;
    private UseCaseExecutor mExecutor;
    private LoginView mView;
    private SharedPreferences mSharedPref;
    private String mPassLengthKey;
    private String mSocialLoggedInKey;


    public LoginPresenter(LoginView view) {
        mLoginUseCase = new Login();
        mAuthFcmUseCase = new AuthFcmToken();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
        Context context = (Context) mView;
        mPassLengthKey = context.getString(R.string.pass_length_key);
        mSocialLoggedInKey = context.getString(R.string.social_logged_key);
        mSharedPref = (context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE));
    }

    public void login(String login, final String password) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(mLoginUseCase, new Login.RequestValues(login, password), new UseCase.Callback<Login.ResponseValues>() {
            @Override
            public void onSuccess(Login.ResponseValues response) {
                if (response.isSuccess()) {
                    mView.showProgress(false);
                    mView.onLoginSuccess();
                    saveSharedPreferences(password);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                if (error.getErrorType() == Error.Type.WRONG_CREDENTIALS) {
                    mView.showMessage(R.string.auth_wrong_cred);
                } else {
                    mView.showMessage(R.string.auth_error);
                }
            }
        });

        pushFcmTokenIfNeeded();
    }

    private void saveSharedPreferences(String password) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        if (password == null) {
            editor.putInt(mPassLengthKey, 0);
            editor.putBoolean(mSocialLoggedInKey, true);
        } else {
            editor.putInt(mPassLengthKey, password.length());
            editor.putBoolean(mSocialLoggedInKey, false);
        }
        editor.apply();
    }

    public void loginSocial(String provider, String token) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new SocialLogin(), new SocialLogin.RequestValues(provider, token), new UseCase.Callback<SocialLogin.ResponseValues>() {
            @Override
            public void onSuccess(SocialLogin.ResponseValues response) {
                if (response.isSuccess()) {
                    mView.showProgress(false);
                    mView.onLoginSuccess();
                    saveSharedPreferences(null);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(R.string.auth_error);
            }
        });

        pushFcmTokenIfNeeded();
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

        pushFcmTokenIfNeeded();
    }

    private void pushFcmTokenIfNeeded() {
        if (AccountManager.getInstance(CardeeApp.context).isFcmTokenAuthenticated()) {
            return;
        }

//        String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        mExecutor.execute(mAuthFcmUseCase, new AuthFcmToken.RequestValues(fcmToken), new UseCase.Callback<AuthFcmToken.ResponseValues>() {
//            @Override
//            public void onSuccess(AuthFcmToken.ResponseValues response) {
//            }
//
//            @Override
//            public void onError(Error error) {
//            }
//        });
    }
}
