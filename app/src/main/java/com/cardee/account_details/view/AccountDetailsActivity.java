package com.cardee.account_details.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.account_details.adapter.CardsAdapter;
import com.cardee.account_details.presenter.AccountDetailsPresenter;
import com.cardee.account_verify.credit_card.CreditCardActivity;
import com.cardee.account_verify.view.VerifyAccountActivity;
import com.cardee.auth.preview.PreviewActivity;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.owner_change_pass.view.ChangePassActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountDetailsActivity extends AppCompatActivity implements AccountDetailsView {

    private AccountDetailsPresenter mPresenter;
    private Toast mCurrentToast;

    @BindView(R.id.account_container)
    View mContainer;

    @BindView(R.id.name_card)
    CardView mNameCard;

    @BindView(R.id.profile_name)
    TextView mProfileName;

    @BindView(R.id.email_card)
    CardView mEmailCard;

    @BindView(R.id.profile_email)
    TextView mProfileEmail;

    @BindView(R.id.phone_card)
    CardView mPhoneCard;

    @BindView(R.id.profile_phone)
    TextView mProfilePhone;

    @BindView(R.id.pass_card)
    CardView mPassCard;

    @BindView(R.id.profile_pass)
    TextView mProfilePass;

    @BindView(R.id.verify_card)
    CardView mVerifyCard;

    @BindView(R.id.log_out_card)
    CardView mLogOutCard;

    @BindView(R.id.add_card_card)
    CardView mAddCard;

    @BindView(R.id.cardsList)
    RecyclerView cardsList;

    @BindView(R.id.account_progress)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_account_details);
        ButterKnife.bind(this);

        initToolBar();
        initPresenter();
        initCardsList();

        mPresenter.getOwnerInfo();
        mPresenter.getCards();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.readPassFromSharedPref();
    }

    private void initPresenter() {
        mPresenter = new AccountDetailsPresenter(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initCardsList() {
        cardsList.setHasFixedSize(true);
        CardsAdapter adapter = new CardsAdapter(this);
        cardsList.setAdapter(adapter);
        mPresenter.setAdapter(adapter);
        cardsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick(R.id.name_card)
    public void nameClicked() {
        mPresenter.showChangeNameDialog(this);
    }

    @OnClick(R.id.email_card)
    public void emailClicked() {
        mPresenter.showChangeEmailDialog(this);
    }

    @OnClick(R.id.phone_card)
    public void phoneClicked() {
        mPresenter.showChangePhoneDialog(this);
    }

    @OnClick(R.id.pass_card)
    public void passClicked() {
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.verify_card)
    public void verifyClicked() {
        Intent intent = new Intent(this, VerifyAccountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.log_out_card)
    public void logOutClicked() {
        AccountManager.getInstance(this).onLogout();
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.add_card_card)
    public void addCardClicked() {
        Intent intent = new Intent(this, CreditCardActivity.class);
        intent.putExtra("isVerify", false);
        startActivity(intent);
    }

    @Override
    public void setName(String name) {
        mProfileName.setText(name);
    }

    @Override
    public void setEmail(String email) {
        mProfileEmail.setText(email);
    }

    @Override
    public void setPhone(String phone) {
        mProfilePhone.setText(phone);
    }

    @Override
    public void setPass(String password) {
        mProfilePass.setText(password);
    }

    @Override
    public void setPassChangeState(boolean clickable) {
        if (clickable) {
            mPassCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    passClicked();
                }
            });
        } else {
            mPassCard.setOnClickListener(null);
        }
    }

    @Override
    public void hidePassword() {
        mPassCard.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
