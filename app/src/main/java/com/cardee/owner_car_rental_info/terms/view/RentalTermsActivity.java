package com.cardee.owner_car_rental_info.terms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalTerms;
import com.cardee.owner_car_rental_info.terms.presenter.RentalTermsPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cardee.owner_car_rental_info.terms.view.RentalTermsAdditional.ADDITIONAL_TERMS;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsAdditional.ADD_ONS;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsDepositActivity.REQ_SECURITY_DEPOSIT;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsDepositActivity.SECURITY_DEPOSIT_DESC;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsInsuranceActivity.COMPENSATION_EXCESS;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsInsuranceActivity.COMPENSATION_OTHER;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsRequirementsActivity.DRIVING_EXP;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsRequirementsActivity.MAX_AGE;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsRequirementsActivity.MIN_AGE;
import static com.cardee.owner_car_rental_info.terms.view.RentalTermsRulesActivity.OTHER_RULES;

public class RentalTermsActivity extends AppCompatActivity implements RentalTermsPresenter.View {

    public final static String CAR_ID = "key_car_id";

    private int mCarId;
    private RentalTerms mTerms;
    private RentalTermsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_terms);
        ButterKnife.bind(this);
        initToolbar();
        mCarId = getIntent().getIntExtra(CAR_ID, -1);
        mPresenter = new RentalTermsPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getRentalDetails(mCarId);
    }

    @OnClick(R.id.cl_termsRequirementsContainer)
    public void onRequirementsClicked() {
        Intent i = new Intent(this, RentalTermsRequirementsActivity.class);
        i.putExtra(MIN_AGE, mTerms.getRequiredMinAge());
        i.putExtra(MAX_AGE, mTerms.getRequiredMaxAge());
        i.putExtra(DRIVING_EXP, mTerms.getRequiredDrivingExperience());
        startActivity(i);
    }

    @OnClick(R.id.cl_termsRulesContainer)
    public void onRulesClicked() {
        Intent i = new Intent(this, RentalTermsRulesActivity.class);
        i.putExtra(OTHER_RULES, mTerms.getOtherCarRules());
        startActivity(i);
    }

    @OnClick(R.id.cl_termsDepositContainer)
    public void onDepositClicked() {
        Intent i = new Intent(this, RentalTermsDepositActivity.class);
        i.putExtra(REQ_SECURITY_DEPOSIT, mTerms.getRequiredSecurityDeposit());
        i.putExtra(SECURITY_DEPOSIT_DESC, mTerms.getSecurityDepositDescription());
        startActivity(i);
    }

    @OnClick(R.id.cl_termsInsuranceContainer)
    public void onInsuranceClicked() {
        Intent i = new Intent(this, RentalTermsInsuranceActivity.class);
        i.putExtra(COMPENSATION_EXCESS, mTerms.getCompensationExcess());
        i.putExtra(COMPENSATION_OTHER, mTerms.getCompensationOtherGuidelines());
        startActivity(i);
    }

    @OnClick(R.id.cl_termsAdditionalContainer)
    public void onAdditionalClicked() {
        Intent i = new Intent(this, RentalTermsAdditional.class);
        i.putExtra(ADD_ONS, mTerms.getAddOns());
        i.putExtra(ADDITIONAL_TERMS, mTerms.getAdditionalTerms());
        startActivity(i);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.car_rental_info_terms);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRentalTermsRetrieved(RentalTerms terms) {
        mTerms = terms;
    }
}
