package com.cardee.owner_car_rental_info.terms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cardee.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_terms);
        ButterKnife.bind(this);
        initToolbar();
    }

    @OnClick(R.id.cl_termsRequirementsContainer)
    public void onRequirementsClicked() {
        startActivity(new Intent(this, RentalTermsRequirementsActivity.class));
    }

    @OnClick(R.id.cl_termsRulesContainer)
    public void onRulesClicked() {
        startActivity(new Intent(this, RentalTermsRulesActivity.class));
    }

    @OnClick(R.id.cl_termsDepositContainer)
    public void onDepositClicked() {
        startActivity(new Intent(this, RentalTermsDepositActivity.class));
    }

    @OnClick(R.id.cl_termsInsuranceContainer)
    public void onInsuranceClicked() {
        startActivity(new Intent(this, RentalTermsInsuranceActivity.class));
    }

    @OnClick(R.id.cl_termsAdditionalContainer)
    public void onAdditionalClicked() {
        startActivity(new Intent(this, RentalTermsAdditional.class));
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
}
