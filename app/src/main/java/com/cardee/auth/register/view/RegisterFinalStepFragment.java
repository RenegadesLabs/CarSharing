package com.cardee.auth.register.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.util.glide.CircleTransform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class RegisterFinalStepFragment extends Fragment {

    public final static String TAG = "RegisterFinalStepFragment";

    private RegisterView mViewListener;

    private Unbinder mUnbinder;

    @BindView(R.id.iv_registerUserPhoto)
    AppCompatImageView regUserPhoto;

    @BindView(R.id.fl_takePhoto)
    FrameLayout regTakePhotoView;

    @BindView(R.id.tiet_registerName)
    TextInputEditText regUserName;

    @BindView(R.id.b_registerAsRenter)
    AppCompatButton regAsRenterButton;

    @BindView(R.id.b_registerAsOwner)
    AppCompatButton regAsOwnerButton;

    private final static String REG_USER_PIC = "reg_user_pic";

    private final static String REG_USER_NAME = "reg_user_name";

    private byte[] mPictureByteArray = null;

    private String mNameText = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register2, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        setButtonsActivated(false);
        return v;
    }

    public void setViewListener(RegisterView listener) {
        mViewListener = listener;
    }

    private void setButtonsActivated(boolean activated) {
        if (activated) {
            regAsOwnerButton.setAlpha(1);
            regAsOwnerButton.setClickable(true);
            regAsRenterButton.setAlpha(1);
            regAsRenterButton.setClickable(true);
            return;
        }
        regAsOwnerButton.setAlpha(.7f);
        regAsOwnerButton.setClickable(false);
        regAsRenterButton.setAlpha(.7f);
        regAsRenterButton.setClickable(false);
    }

    @OnClick(R.id.b_registerBackToFirstStep)
    public void onBackToFirstStepClicked() {
        if (mViewListener != null)
            mViewListener.onBackToFirstStep();
    }

    @OnClick(R.id.iv_registerUserPhoto)
    public void onTakePhotoClicked() {
        if (mViewListener != null)
            mViewListener.onTakePhoto();
    }

    @OnClick(R.id.b_registerAsOwner)
    public void onRegisterAsOwnerClicked() {
        if (mViewListener != null)
            mViewListener.onSignUpAsOwner(regUserName.getText().toString(), convertByteArrayToBase64());
    }

    @OnClick(R.id.b_registerAsRenter)
    public void onRegisterAsRenterClicked() {
        if (mViewListener != null)
            mViewListener.onSignUpAsRenter(regUserName.getText().toString(), convertByteArrayToBase64());
    }

    @OnTextChanged(R.id.tiet_registerName)
    public void onTextChanged(CharSequence text) {
        if (text.length() > 0) {
            setButtonsActivated(true);
            return;
        }
        setButtonsActivated(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPictureByteArray = null;
    }

    public void setUserPhoto(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        mPictureByteArray = stream.toByteArray();
        setImage(mPictureByteArray);
    }

    private void setImage(byte[] pictureByteArray) {
        Glide.with((RegisterActivity) getActivity())
                .load(pictureByteArray)
                .placeholder(R.drawable.placeholder_user_img)
                .override(300, 300)
                .centerCrop()
                .transform(new CircleTransform(getActivity()))
                .into(regUserPhoto);

        if (!regUserName.getText().toString().isEmpty()) {
            setButtonsActivated(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mPictureByteArray = savedInstanceState.getByteArray(REG_USER_PIC);
            if (mPictureByteArray != null) {
                setImage(mPictureByteArray);
            }

            mNameText = savedInstanceState.getString(REG_USER_NAME);
            if (mNameText != null && !mNameText.isEmpty()) {
                regUserName.setText(mNameText);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPictureByteArray != null) {
            outState.putByteArray(REG_USER_PIC, mPictureByteArray);
        }

        mNameText = regUserName.getText().toString();
        if (!mNameText.isEmpty()) {
            outState.putString(REG_USER_NAME, mNameText);
        }
    }

    private File convertByteArrayToBase64() {
        if (mPictureByteArray != null) {
            FileOutputStream fos;
            File f = null;
            try {
                f = new File(getActivity().getCacheDir(), "picture");
                fos = new FileOutputStream(f);
                fos.write(mPictureByteArray);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        }
        return null;
//        return Base64.encodeToString(mPictureByteArray, Base64.DEFAULT);
    }
}
