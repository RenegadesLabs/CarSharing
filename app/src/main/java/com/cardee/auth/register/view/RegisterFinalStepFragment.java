package com.cardee.auth.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.auth.register.RegisterContract;

public class RegisterFinalStepFragment extends Fragment {

    private RegisterContract.RegisterView mViewListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setViewListener(RegisterContract.RegisterView listener) {
        mViewListener = listener;
    }
}
