package com.cardee.owner_car_rental_terms.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_rental_terms.RentalTermsContract;
import com.cardee.owner_car_rental_terms.presenter.RentalTermsRulesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalTermsRulesActivity extends AppCompatActivity implements View.OnClickListener,
        RentalTermsContract.View {

    @BindView(R.id.tv_allowSmoking)
    public CheckedTextView allowSmokingTV;
    @BindView(R.id.tv_disallowSmoking)
    public CheckedTextView disallowSmokingTV;
    @BindView(R.id.tv_allowMalaysia)
    public CheckedTextView allowMalaysiaTV;
    @BindView(R.id.tv_disallowMalaysia)
    public CheckedTextView disallowMalaysiaTV;
    @BindView(R.id.tv_allowDogs)
    public CheckedTextView allowDogsTV;
    @BindView(R.id.tv_disallowDogs)
    public CheckedTextView disallowDogsTV;
    @BindView(R.id.et_rulesOwnRules)
    public AppCompatEditText ownRulesET;

    private ProgressDialog mProgress;
    private RentalTermsRulesPresenter mPresenter;
    private CarRuleEntity.Rule[] mRules = new CarRuleEntity.Rule[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_rules);
        ButterKnife.bind(this);
        initToolbar();
        initState();
        mProgress = DialogHelper.getProgressDialog(this,
                getString(R.string.loading), false);
        mPresenter = new RentalTermsRulesPresenter(this);
    }

    @OnClick(R.id.tv_allowSmoking)
    public void onAllowSmokingClicked(View view) {
        allowSmokingTV.setChecked(true);
        toggleAllowDisallow(allowSmokingTV, disallowSmokingTV);
        setRule(0, true);
    }

    @OnClick(R.id.tv_disallowSmoking)
    public void onDisallowSmokingClicked(View view) {
        disallowSmokingTV.setChecked(true);
        toggleAllowDisallow(disallowSmokingTV, allowSmokingTV);
        setRule(0, false);
    }

    @OnClick(R.id.tv_allowMalaysia)
    public void onAllowMalaysiaClicked(View view) {
        allowMalaysiaTV.setChecked(true);
        toggleAllowDisallow(allowMalaysiaTV, disallowMalaysiaTV);
        setRule(1, true);
    }

    @OnClick(R.id.tv_disallowMalaysia)
    public void onDisallowMalaysiaClicked(View view) {
        disallowMalaysiaTV.setChecked(true);
        toggleAllowDisallow(disallowMalaysiaTV, allowMalaysiaTV);
        setRule(1, false);
    }

    @OnClick(R.id.tv_allowDogs)
    public void onAllowDogsClicked(View view) {
        allowDogsTV.setChecked(true);
        toggleAllowDisallow(allowDogsTV, disallowDogsTV);
        setRule(2, true);
    }

    @OnClick(R.id.tv_disallowDogs)
    public void onDisallowDogsClicked(View view) {
        disallowDogsTV.setChecked(true);
        toggleAllowDisallow(disallowDogsTV, allowDogsTV);
        setRule(2, false);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.car_rental_terms_rules);
        toolbar.findViewById(R.id.toolbar_action).setOnClickListener(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initState() {
        disallowSmokingTV.setChecked(true);
        toggleAllowDisallow(disallowSmokingTV, allowSmokingTV);
        setRule(0, false);
        disallowMalaysiaTV.setChecked(true);
        toggleAllowDisallow(disallowMalaysiaTV, allowMalaysiaTV);
        setRule(1, false);
        disallowDogsTV.setChecked(true);
        toggleAllowDisallow(disallowDogsTV, allowDogsTV);
        setRule(2, false);
    }

    private void toggleAllowDisallow(CheckedTextView tv1, CheckedTextView tv2) {
        tv2.setChecked(false);
        if (tv1.isChecked()) {
            tv1.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv2.setTextColor(getResources().getColor(R.color.text_subtitle));
            return;
        }
        tv1.setTextColor(getResources().getColor(R.color.text_subtitle));
    }

    private void setRule(int item, boolean allow) {
        mRules[item] = new CarRuleEntity.Rule(item + 1, allow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                mPresenter.save(mRules, ownRulesET.getText().toString());
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
            return;
        }
        mProgress.dismiss();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        onBackPressed();
        finish();
    }
}
