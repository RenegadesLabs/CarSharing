package com.cardee.owner_car_rental_terms.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.modal.DoublePickerMenuFragment;
import com.cardee.custom.modal.PickerMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerCarRentalTermsRequirementsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_requirementsAge)
    public TextView ageRangeTV;

    @BindView(R.id.tv_requirementsExperience)
    public TextView drivingExpTV;

    private String[] mRange = new String[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_requirements);
        ButterKnife.bind(this);
        initToolbar();
        initViewsState();
    }

    @OnClick(R.id.cl_requirementsAgeContainer)
    public void onAgeRangeClicked() {
        DoublePickerMenuFragment menu = DoublePickerMenuFragment.getInstance("18", "98",
                DoublePickerMenuFragment.Mode.YEARS_RANGE);
        menu.show(getSupportFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new DoublePickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value1, String value2) {
                mRange[0] = value1;
                mRange[1] = value2;
                invalidateAgeRangeText();
            }

            @Override
            public void onFail() {
                Toast.makeText(OwnerCarRentalTermsRequirementsActivity.this,
                        R.string.car_rental_terms_requirements_age_range_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.cl_requirementsExpContainer)
    public void onExperienceClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance("1", PickerMenuFragment.Mode.DRIVING_EXPERIENCE);
        menu.show(getSupportFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                setDrivingExperienceText(value);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.car_rental_terms_requirements);
        toolbar.findViewById(R.id.toolbar_action).setOnClickListener(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initViewsState() {
        mRange[0] = "18";
        mRange[1] = "98";
        invalidateAgeRangeText();
        setDrivingExperienceText("1");
    }

    private void invalidateAgeRangeText() {
        String range = mRange[0] + " " + getString(R.string.to) +
                " " + mRange[1] + " yrs old";
        ageRangeTV.setText(range);
    }

    private void setDrivingExperienceText(String exp) {
        String experience = "at least " + exp + " years";
        drivingExpTV.setText(experience);
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
