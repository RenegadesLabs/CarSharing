package com.cardee.owner_car_rental_terms.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.cardee.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class OwnerCarRentalTermsRulesActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_rules);
        ButterKnife.bind(this);
        initToolbar();
    }

    @OnClick(R.id.tv_allowSmoking)
    public void onAllowSmokingClicked(View view) {
        allowSmokingTV.setChecked(true);
        toggleAllowDisallow(allowSmokingTV, disallowSmokingTV);
    }

    @OnClick(R.id.tv_disallowSmoking)
    public void onDisallowSmokingClicked(View view) {
        disallowSmokingTV.setChecked(true);
        toggleAllowDisallow(disallowSmokingTV, allowSmokingTV);
    }

    @OnClick(R.id.tv_allowMalaysia)
    public void onAllowMalaysiaClicked(View view) {
        allowMalaysiaTV.setChecked(true);
        toggleAllowDisallow(allowMalaysiaTV, disallowMalaysiaTV);
    }

    @OnClick(R.id.tv_disallowMalaysia)
    public void onDisallowMalaysiaClicked(View view) {
        disallowMalaysiaTV.setChecked(true);
        toggleAllowDisallow(disallowMalaysiaTV, allowMalaysiaTV);
    }

    @OnClick(R.id.tv_allowDogs)
    public void onAllowDogsClicked(View view) {
        allowDogsTV.setChecked(true);
        toggleAllowDisallow(allowDogsTV, disallowDogsTV);
    }

    @OnClick(R.id.tv_disallowDogs)
    public void onDisallowDogsClicked(View view) {
        disallowDogsTV.setChecked(true);
        toggleAllowDisallow(disallowDogsTV, allowDogsTV);
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

    private void toggleAllowDisallow(CheckedTextView tv1, CheckedTextView tv2) {
        tv2.setChecked(false);
        if (tv1.isChecked()) {
            tv1.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv2.setTextColor(getResources().getColor(R.color.text_subtitle));
            return;
        }
        tv1.setTextColor(getResources().getColor(R.color.text_subtitle));
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
                // TODO: 11/29/17 On Save Clicked
                break;
        }
    }
}
