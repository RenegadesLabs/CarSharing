package com.cardee.auth.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.auth.register.RegisterContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFirstStepFragment extends Fragment {

    public final static String TAG = "RegisterFirstStepFragment";

    @BindView(R.id.et_emailRegister)
    AppCompatEditText regEmailEdit;

    @BindView(R.id.et_passwordRegister)
    AppCompatEditText regPassEdit;

    private Unbinder mUnbinder;

    private RegisterContract.RegisterView mViewListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register1, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        return v;
    }

    public void setViewListener(RegisterContract.RegisterView listener) {
        mViewListener = listener;
    }

    @OnClick(R.id.b_registerGoToLogin)
    public void onGoToLoginClicked() {
        if (mViewListener != null)
            mViewListener.onLogin();
    }

    @OnClick(R.id.b_registerSignup)
    public void onSignUpClicked() {
        if (mViewListener != null)
            mViewListener.onSignUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
