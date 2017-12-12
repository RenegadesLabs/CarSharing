package com.cardee.owner_cardee.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_cardee.presenter.OwnerCardeePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerCardeeActivity extends AppCompatActivity implements OwnerCardeeView {

    private OwnerCardeePresenter mPresenter;
    private Toast mCurrentToast;

    @BindView(R.id.container)
    LinearLayout mContainer;

    @BindView(R.id.cardee_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.mobile1)
    TextView mMobileFirst;

    @BindView(R.id.mobile2)
    TextView mMobileSecond;

    @BindView(R.id.email)
    TextView mEmail;

    @BindView(R.id.website)
    TextView mWebsite;

    @BindView(R.id.faq)
    TextView mFaq;

    @BindView(R.id.et_feedback)
    AppCompatEditText mFeedback;

    @BindView(R.id.b_submit)
    AppCompatButton mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_cardee);
        ButterKnife.bind(this);

        initToolBar();
        mPresenter = new OwnerCardeePresenter(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.mobile1)
    public void onFirstMobileClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getResources().getString(R.string.mobile_one)));
        startActivity(intent);
    }

    @OnClick(R.id.mobile2)
    public void onSecondMobileClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getResources().getString(R.string.mobile_two)));
        startActivity(intent);
    }

    @OnClick(R.id.email)
    public void onEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.cardee_email)});
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Send with:"));
    }

    @OnClick(R.id.website)
    public void onWebsiteClicked() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.http)
                + getResources().getString(R.string.cardee_website)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.faq)
    public void onFaqClicked() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.http)
                + getResources().getString(R.string.cardee_faq)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.b_submit)
    public void onSubmitClicked() {
        String feedback = mFeedback.getText().toString();
        if (feedback != null && !feedback.isEmpty()) {
            mPresenter.onSubmitCliceked(feedback);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
