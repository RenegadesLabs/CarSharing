package com.cardee.auth.register;


import android.graphics.Bitmap;

import com.cardee.auth.register.view.RegisterFragmentActionListener;

public class RegisterPresenterImpl implements RegisterContract.RegisterPresenter {

    private RegisterFragmentActionListener mFActionListener;

    private RegisterPresenterImpl(RegisterFragmentActionListener fActionListener) {
        mFActionListener = fActionListener;
    }

    public void onImageCropped(Bitmap bmp) {
        if (mFActionListener != null)
            mFActionListener.onImageCropped(bmp);
    }
}
