package com.cardee.owner_car_rental_info.terms.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_rental_info.RentalContract;
import com.cardee.owner_car_rental_info.terms.presenter.RentalTermsDepositPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentalTermsDepositActivity extends AppCompatActivity implements View.OnClickListener,
        RentalContract.View {

    public final static String REQ_SECURITY_DEPOSIT = "key_required_security_deposit";
    public final static String SECURITY_DEPOSIT_DESC = "key_security_deposit_description";

    //    @BindView(R.id.sw_depositRequire)
//    public SwitchCompat depositRequireSW;
//    @BindView(R.id.et_depositValue)
//    public AppCompatEditText depositValueET;
    @BindView(R.id.securityDeposit)
    public AppCompatEditText securityDeposit;
    @BindView(R.id.bottomHintText)
    public TextView bottomHintText;

    private ProgressDialog mProgress;
    private RentalTermsDepositPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_security_deposit);
        ButterKnife.bind(this);
        initToolbar();
        initViewState();
        mProgress = DialogHelper
                .getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new RentalTermsDepositPresenter(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.car_rental_terms_deposit);
        toolbar.findViewById(R.id.toolbar_action).setOnClickListener(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initViewState() {
//        depositRequireSW.setChecked(getIntent().getBooleanExtra(REQ_SECURITY_DEPOSIT, false));
//        depositValueET.setText(getIntent().getStringExtra(SECURITY_DEPOSIT_DESC));
        securityDeposit.setText(getIntent().getStringExtra(SECURITY_DEPOSIT_DESC));

        SpannableString span = new SpannableString(getResources().getString(R.string.deposit_info_link));
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                0, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bottomHintText.append(" ");
        bottomHintText.append(span);
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
//                mPresenter.save(depositRequireSW.isChecked(), depositValueET.getText().toString());
                mPresenter.save(securityDeposit.getText().toString());
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
