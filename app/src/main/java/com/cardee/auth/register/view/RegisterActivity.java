package com.cardee.auth.register.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cardee.R;
import com.cardee.auth.login.LoginActivity;
import com.cardee.auth.register.RegisterContract;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    private final int PICK_IMAGE = 1;
    private final int CROP_IMAGE = 2;

    private RegisterFirstStepFragment mFirstStepFragment;
    private RegisterFinalStepFragment mFinalStepFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFragments();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();

        mFirstStepFragment = new RegisterFirstStepFragment();
        mFirstStepFragment.setViewListener(this);

        mFinalStepFragment = new RegisterFinalStepFragment();
        mFinalStepFragment.setViewListener(this);

        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment)
                .commit();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void onLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSignUp() {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFinalStepFragment)
                .commit();
    }

    @Override
    public void onTakePhoto() {
        pickImageIntent();
    }

    @Override
    public void onBackToFirstStep() {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment)
                .commit();
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

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK && data.getData() != null) {
                    cropImageIntent(data.getData());
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
        }
    }

    private void pickImageIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
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
    public void onTermsOfService() {

    }

    @Override
    public void onFacebook() {

    }

    @Override
    public void onGoogle() {

    }

    @Override
    public void onSignUpAsRenter() {

    }

    @Override
    public void onSignUpAsOwner() {

    }
}
