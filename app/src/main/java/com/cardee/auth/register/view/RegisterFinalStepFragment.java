package com.cardee.auth.register.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cardee.R;
import com.cardee.auth.register.RegisterContract;
import com.cardee.util.glide.CircleTransform;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFinalStepFragment extends Fragment {

    public final static String TAG = "RegisterFinalStepFragment";

    private RegisterContract.RegisterView mViewListener;

    private Unbinder mUnbinder;

    @BindView(R.id.iv_registerUserPhoto)
    AppCompatImageView regUserPhoto;

    @BindView(R.id.fl_takePhoto)
    FrameLayout regTakePhotoView;

    @BindView(R.id.tiet_registerName)
    TextInputEditText regUserName;

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

        return v;
    }

    public void setViewListener(RegisterContract.RegisterView listener) {
        mViewListener = listener;
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
            mViewListener.onSignUpAsOwner();
    }

    @OnClick(R.id.b_registerAsRenter)
    public void onRegisterAsRenterClicked() {
        if (mViewListener != null)
            mViewListener.onSignUpAsRenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void setUserPhoto(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        mPictureByteArray = stream.toByteArray();
        setImage(mPictureByteArray);
    }

    private void setImage(byte[] pictureByteArray) {
        Glide.with((RegisterActivity) getActivity())
                .load(pictureByteArray)
                .placeholder(R.drawable.placeholder_user_img)
                .transform(new CircleTransform(getActivity()))
                .into(regUserPhoto);
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
}
