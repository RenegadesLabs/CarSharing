package com.cardee.auth.register.view;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.auth.login.view.LoginActivity;
import com.cardee.auth.register.presenter.RegisterPresenter;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.cardee.renter_home.view.RenterHomeActivity;
import com.cardee.util.display.ActivityHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    //    private final int PICK_IMAGE = 1;
    private static final int RC_SIGN_IN = 9001;
    private final int CROP_IMAGE = 2;

    private RegisterPresenter mPresenter;

    private RegisterFirstStepFragment mFirstStepFragment;
    private RegisterFinalStepFragment mFinalStepFragment;
    private FragmentManager mFragmentManager;

    private CallbackManager mFacebookCM;

    private LoginButton mButtonFacebook;

    private ProgressDialog mProgress;

    private String mLogin, mPass, mName;

    private GoogleApiClient mGoogleClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFragments();
        initFacebookButton();
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        initGoogleApi();
    }

    private void initFragments() {
        mPresenter = new RegisterPresenter(this);

        mFragmentManager = getSupportFragmentManager();

        mFirstStepFragment = new RegisterFirstStepFragment();
        mFirstStepFragment.setViewListener(this);

        mFinalStepFragment = new RegisterFinalStepFragment();
        mFinalStepFragment.setViewListener(this);

        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment)
                .commit();
    }

    private void initFacebookButton() {
        mFacebookCM = CallbackManager.Factory.create();
        mButtonFacebook = new LoginButton(this);
        mButtonFacebook.registerCallback(mFacebookCM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mPresenter.registerSocial(SocialLoginRequest.FACEBOOK,
                        loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(RegisterActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                showMessage(error.getMessage());
            }
        });
    }

    private void initGoogleApi() {
        mGoogleClient = CardeeApp.initLoginGoogleApi(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                showMessage(connectionResult.getErrorMessage());
            }
        });
    }

    private void cropImageIntent(Uri imgUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            cropIntent.setDataAndType(imgUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_IMAGE);

        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment finalF = mFragmentManager.findFragmentByTag(RegisterFinalStepFragment.TAG);
        if (finalF != null && finalF.isVisible()) {
            onBackToFirstStep();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCM.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ActivityHelper.PICK_IMAGE:
                if (resultCode == RESULT_OK && data.getData() != null) {
                    if (data.getExtras() != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            if (mFinalStepFragment != null && mFinalStepFragment.isVisible()) {
                                mFinalStepFragment.setUserPhoto(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case CROP_IMAGE:
                if (data.getExtras() != null) {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = extras.getParcelable("data");
                    if (mFinalStepFragment != null && mFinalStepFragment.isVisible()) {
                        mFinalStepFragment.setUserPhoto(bitmap);
                    }
                }
                break;
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    mPresenter.loginGoogle(result);
                }
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
            return;
        }
        mProgress.dismiss();
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
    public void onLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSignUp(String login, String password) {
        mPresenter.checkUniqueLogin(login, password);
    }

    @Override
    public void onTakePhoto() {
        ActivityHelper.pickImageIntent(this, ActivityHelper.PICK_IMAGE);
    }

    @Override
    public void onBackToFirstStep() {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment, RegisterFirstStepFragment.TAG)
                .commit();
    }


    @Override
    public void onTermsOfService() {

    }

    @Override
    public void onFacebook() {
        mButtonFacebook.performClick();
    }

    @Override
    public void onGoogle() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleClient), RC_SIGN_IN);
    }

    @Override
    public void onProceedGoogleLogin(final String accessToken) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.registerSocial(SocialLoginRequest.GOOGLE, accessToken);
            }
        });
    }

    @Override
    public void onSignUpAsRenter(String name, File picture) {
        mPresenter.setAccountState(AccountManager.ACC_STATE.RENTER);
        mPresenter.signUp(mLogin, mPass, name, picture, AccountManager.ACC_STATE.RENTER);
    }

    @Override
    public void onSignUpAsOwner(String name, File picture) {
        mPresenter.setAccountState(AccountManager.ACC_STATE.OWNER);
        mPresenter.signUp(mLogin, mPass, name, picture, AccountManager.ACC_STATE.OWNER);
    }

    @Override
    public void onValidationSuccess(String login, String password) {
        mLogin = login;
        mPass = password;
        ActivityHelper.hideSoftKeyboard(this);
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFinalStepFragment, RegisterFinalStepFragment.TAG)
                .commit();
    }

    @Override
    public void onRegistrationSuccess(AccountManager.ACC_STATE accState) {
        Intent intent = null;
        switch (accState) {
            case OWNER:
                intent = new Intent(this, OwnerHomeActivity.class);
                break;
            case RENTER:
                intent = new Intent(this, RenterHomeActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
