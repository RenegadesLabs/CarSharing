package com.cardee.owner_car_rental_info.terms.view;

import android.app.ProgressDialog;
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
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_rental_info.RentalContract;
import com.cardee.owner_car_rental_info.terms.presenter.RentalTermsRequirementsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalTermsRequirementsActivity extends AppCompatActivity implements View.OnClickListener,
        RentalContract.View {

    @BindView(R.id.tv_requirementsAge)
    public TextView ageRangeTV;

    @BindView(R.id.tv_requirementsExperience)
    public TextView drivingExpTV;

    private String[] mRange = new String[2];

    private String mExperience;

    private ProgressDialog mProgress;

    private RentalTermsRequirementsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_requirements);
        ButterKnife.bind(this);
        initToolbar();
        initViewsState();
        mProgress = DialogHelper
                .getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new RentalTermsRequirementsPresenter(this);
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
                Toast.makeText(RentalTermsRequirementsActivity.this,
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
                mExperience = value;
                invalidateExperienceText();
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
        mExperience = "1";
        invalidateAgeRangeText();
        invalidateExperienceText();
    }

    private void invalidateAgeRangeText() {
        String range = mRange[0] + " " + getString(R.string.to) +
                " " + mRange[1] + " yrs old";
        ageRangeTV.setText(range);
    }

    private void invalidateExperienceText() {
        String experience = "at least " + mExperience + " years";
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
                mPresenter.save(Integer.parseInt(mRange[0]),
                        Integer.parseInt(mRange[1]), Integer.parseInt(mExperience));
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
