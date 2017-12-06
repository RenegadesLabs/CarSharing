package com.cardee.owner_car_rental_info.terms.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_rental_info.terms.RentalTermsContract;
import com.cardee.owner_car_rental_info.terms.presenter.RentalTermsAdditionalPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentalTermsAdditional extends AppCompatActivity implements View.OnClickListener,
        RentalTermsContract.View {

    @BindView(R.id.et_additionalTerms)
    public AppCompatEditText additionalTermsET;

    private ProgressDialog mProgress;
    private RentalTermsAdditionalPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_additional);
        ButterKnife.bind(this);
        initToolbar();
        mProgress = DialogHelper.getProgressDialog(this,
                getString(R.string.loading), false);
        mPresenter = new RentalTermsAdditionalPresenter(this);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.car_rental_terms_additional);
        toolbar.findViewById(R.id.toolbar_action).setOnClickListener(this);
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                if (additionalTermsET.getText().toString().isEmpty()) {
                    showMessage(R.string.nothing_to_save);
                    return;
                }
                mPresenter.save(additionalTermsET.getText().toString());
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
