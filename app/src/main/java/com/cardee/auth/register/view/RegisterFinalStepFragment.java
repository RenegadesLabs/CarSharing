package com.cardee.auth.register.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
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
    FrameLayout takePhotoView;


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

    @OnClick(R.id.fl_takePhoto)
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
        takePhotoView.setVisibility(View.INVISIBLE);
        regUserPhoto.setVisibility(View.VISIBLE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(getActivity())
                .load(stream.toByteArray())
                .transform(new CircleTransform(getActivity()))
                .into(regUserPhoto);
    }
}
