package com.cardee.auth.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.auth.register.view.RegisterActivity;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends /*AppCompatActivity*/ FragmentActivity implements LoginView {

    private final static int RC_SIGN_IN = 9001;

    @BindView(R.id.et_loginEmail)
    AppCompatEditText loginEmailEdit;

    @BindView(R.id.et_loginPassword)
    TextInputEditText loginPassEdit;

    @BindView(R.id.l_loginPassword)
    TextInputLayout loginPassLayout;

    private LoginPresenter mPresenter;

    private ProgressDialog mProgress;

    private CallbackManager mFacebookCM;

    private LoginButton mButtonFacebook;

    private GoogleApiClient mGoogleClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
        initProgress();
        initFacebookApi();
        initGoogleApi();
    }

    @OnClick(R.id.b_loginGoToRegister)
    public void onGoToRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.b_loginLogin)
    public void onLoginClicked() {
        if (isFieldsNotEmpty()) {
            mPresenter.login(loginEmailEdit.getText().toString(),
                    loginPassEdit.getText().toString());
        }
    }

    @OnClick(R.id.tv_loginForgotPassword)
    public void onForgotPassClicked() {
    }

    @OnClick(R.id.b_loginFacebook)
    public void onFacebookLoginClicked() {
        mButtonFacebook.performClick();
    }

    @OnClick(R.id.b_loginGoogle)
    public void onGoogleLoginClicked() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleClient), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCM.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acc = result.getSignInAccount();
                if (acc != null) {
                    mPresenter.loginSocial(SocialLoginRequest.Provider.GOOGLE,
                            result.getSignInAccount().getIdToken());
                }
            }
        }
    }

    private void initFacebookApi() {
        mFacebookCM = CallbackManager.Factory.create();
        mButtonFacebook = new LoginButton(this);
        mButtonFacebook.registerCallback(mFacebookCM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mPresenter.loginSocial(SocialLoginRequest.Provider.FACEBOOK,
                        loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                showMessage(error.getMessage());
            }
        });

    }

    private void initGoogleApi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestIdToken(getString(R.string.google_web_server_id))
                .build();

        mGoogleClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showMessage(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private boolean isFieldsNotEmpty() {
        String err = getResources().getString(R.string.email_pass_empty_error);
        if (loginEmailEdit.getText().toString().equals("")) {
            loginEmailEdit.setError(err);
            return false;
        } else if (loginPassEdit.getText().toString().equals("")) {
            loginPassLayout.setError(err);
            return false;
        } else {
            return true;
        }
    }

    private void initProgress() {
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getResources().getString(R.string.login_process));
        mProgress.setCancelable(false);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
        } else {
            mProgress.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, OwnerHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
