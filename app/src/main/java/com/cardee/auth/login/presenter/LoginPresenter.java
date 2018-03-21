package com.cardee.auth.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.cardee.R;
import com.cardee.auth.login.view.LoginView;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.CheckUniqueLogin;
import com.cardee.domain.user.usecase.Login;
import com.cardee.domain.user.usecase.SocialLogin;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPresenter {

    private final Login mLoginUseCase;
    private UseCaseExecutor mExecutor;
    private LoginView mView;
    private SharedPreferences mSharedPref;
    private String mPassLengthKey;
    private String mSocialLoggedInKey;


    public LoginPresenter(LoginView view) {
        mLoginUseCase = new Login();
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
    }

    public void loginGoogle(GoogleSignInResult result) {
        GoogleSignInAccount acc = result.getSignInAccount();
        if (acc != null) {
            if (mView != null) {
                mView.showProgress(true);
            }

            String code = acc.getServerAuthCode();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
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
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    if (mView != null) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.auth_error);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
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

    public void checkUniqueLogin(String email) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new CheckUniqueLogin(), new CheckUniqueLogin.RequestValues(email, null),
                new UseCase.Callback<CheckUniqueLogin.ResponseValues>() {
                    @Override
                    public void onSuccess(CheckUniqueLogin.ResponseValues response) {
                        mView.showProgress(false);
                        mView.onValidationSuccess();
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                        mView.logOut();
                    }
                });
    }
}
