package com.cardee.owner_car_rental_info.terms.view;

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
import com.cardee.owner_car_rental_info.RentalContract;
import com.cardee.owner_car_rental_info.terms.presenter.RentalTermsRulesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalTermsRulesActivity extends AppCompatActivity implements View.OnClickListener,
        RentalContract.View {

    @BindView(R.id.et_rulesOwnRules)
    public AppCompatEditText ownRulesET;

    private ProgressDialog mProgress;
    private RentalTermsRulesPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_rules);
        ButterKnife.bind(this);
        initToolbar();
        mProgress = DialogHelper.getProgressDialog(this,
                getString(R.string.loading), false);
        mPresenter = new RentalTermsRulesPresenter(this);
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
                mPresenter.save(ownRulesET.getText().toString());
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
