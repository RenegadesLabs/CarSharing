package com.cardee.auth.register.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @BindView(R.id.tv_registerTermsOfService)
    TextView regTermsOfServiceTV;

    private Unbinder mUnbinder;

    private RegisterContract.RegisterView mViewListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register1, container, false);

        mUnbinder = ButterKnife.bind(this, v);
        setTermsOfServiceText();

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

    @OnClick(R.id.b_registerFacebook)
    public void onFacebookSignInClicked() {
        if (mViewListener != null) {
            mViewListener.onFacebook();
        }
    }

    @OnClick(R.id.b_registerGoogle)
    public void onGoogleSignInClicked() {
        if (mViewListener != null) {
            mViewListener.onGoogle();
        }
    }

    private void setTermsOfServiceText() {
        String text = getString(R.string.signup_terms_by) + "\n";
        String termsLink = getString(R.string.signup_terms_terms);
        String ampersand = " & ";
        String privacyLink = getString(R.string.signup_terms_privacy);

        int linkTermsStart = text.length();
        int linkTermsEnd = linkTermsStart + termsLink.length();
        int linkPrivacyStart = linkTermsEnd + ampersand.length();
        int linkPrivacyEnd = linkPrivacyStart + privacyLink.length();

        ClickableSpan linkTermsSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                synchronized (this) {
                    if (mViewListener != null) {
                        mViewListener.onTermsOfService();
                    }
                }
            }
        };

        ClickableSpan linkPrivacySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                synchronized (this) {
                    if (mViewListener != null) {
                        mViewListener.onTermsOfService();
                    }
                }
            }
        };
        SpannableString userTermsOfServiceText = new SpannableString(text + termsLink + ampersand + privacyLink);
        userTermsOfServiceText.setSpan(linkTermsSpan, linkTermsStart, linkTermsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userTermsOfServiceText.setSpan(linkPrivacySpan, linkPrivacyStart, linkPrivacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        regTermsOfServiceTV.setText(userTermsOfServiceText);
        regTermsOfServiceTV.setMovementMethod(LinkMovementMethod.getInstance());
        regTermsOfServiceTV.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
