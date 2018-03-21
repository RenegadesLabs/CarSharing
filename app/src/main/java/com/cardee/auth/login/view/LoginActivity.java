package com.cardee.auth.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.auth.login.presenter.LoginPresenter;
import com.cardee.auth.pass_recover.send_email.SendEmailActivity;
import com.cardee.auth.register.view.RegisterActivity;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.cardee.util.RegexHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity /*FragmentActivity*/ implements LoginView {

    private final static int RC_SIGN_IN = 9001;

    public final static String TAG = LoginActivity.class.getCanonicalName();

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
    private LoginResult mLoginResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
        initProgress();
        initFacebookApi();
        initGoogleApi();
        initEditTexts();
    }

    @OnClick(R.id.b_loginGoToRegister)
    public void onGoToRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.b_loginLogin)
    public void onLoginClicked() {
        if (isFieldsNotEmpty()) {
            if (checkFields()) {
                mPresenter.login(loginEmailEdit.getText().toString(),
                        loginPassEdit.getText().toString());
            }
        }
    }

    @OnClick(R.id.tv_loginForgotPassword)
    public void onForgotPassClicked() {
        startActivity(new Intent(this, SendEmailActivity.class));
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
                mPresenter.loginGoogle(result);
            } else {
                showMessage(R.string.auth_error);
            }
        }
    }

    private void initFacebookApi() {
        mFacebookCM = CallbackManager.Factory.create();
        mButtonFacebook = new LoginButton(this);
        mButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        mButtonFacebook.registerCallback(mFacebookCM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginResult = loginResult;
                setFacebookData(loginResult);
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

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                (object, response) -> {
                    try {
                        String email = response.getJSONObject().getString("email");
                        String firstName = response.getJSONObject().getString("first_name");

                        Log.i("Login" + "Email", email);
                        Log.i("Login" + "FirstName", firstName);

                        mPresenter.checkUniqueLogin(email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,first_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void initGoogleApi() {
        mGoogleClient = CardeeApp.initLoginGoogleApi(this,
                connectionResult -> showMessage(connectionResult.getErrorMessage()));
    }

    private void initEditTexts() {
        initEmail();
        initPass();
    }

    private void initEmail() {
        loginEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    loginEmailEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    loginEmailEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_button_transparent, 0);
                }
            }
        });

        loginEmailEdit.setOnTouchListener((view, motionEvent) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawRight = loginEmailEdit.getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawRight != null) {
                    if (motionEvent.getRawX() >= (loginEmailEdit.getRight() - drawRight.getBounds().width()) - loginEmailEdit.getPaddingEnd()) {
                        loginEmailEdit.setText("");
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private void initPass() {
        loginPassEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!loginPassLayout.isPasswordVisibilityToggleEnabled()) {
                    loginPassLayout.setPasswordVisibilityToggleEnabled(true);
                }
            }
        });
    }

    private boolean isFieldsNotEmpty() {
        String err = getResources().getString(R.string.email_pass_empty_error);
        if (loginEmailEdit.getText().toString().equals("")) {
            loginEmailEdit.setError(err);
            return false;
        } else if (loginPassEdit.getText().toString().equals("")) {
            loginPassLayout.setPasswordVisibilityToggleEnabled(false);
            loginPassEdit.setError(err);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkFields() {
        if (!loginEmailEdit.getText().toString().matches(RegexHelper.INSTANCE.getEmailRegex().getPattern())) {
            loginEmailEdit.setError(getResources().getString(R.string.email_invalid_error));
            return false;
        }
        return true;
    }

    private void initProgress() {
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getResources().getString(R.string.login_process));
        mProgress.setCancelable(false);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (!mProgress.isShowing()) {
                mProgress.show();
            }
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

    @Override
    public void onProceedGoogleLogin(final String accessToken) {
        runOnUiThread(() -> mPresenter.loginSocial(SocialLoginRequest.GOOGLE, accessToken));
    }

    @Override
    public void onValidationSuccess() {
        if (mLoginResult != null) {
            mPresenter.loginSocial(SocialLoginRequest.FACEBOOK,
                    mLoginResult.getAccessToken().getToken());
        }
    }

    @Override
    public void logOut() {
        AccountManager.getInstance(this).onLogout();
    }
}
