package com.cardee.owner_car_add.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.cardee.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarAddActivity extends AppCompatActivity implements CarAddView, View.OnClickListener {

    private Unbinder unbinder;

    private View mActionSave;

    @BindView(R.id.tv_addCarItem1)
    public TextView addCarItem1TV;

    @BindView(R.id.tv_addCarItem2)
    public TextView addCarItem2TV;

    @BindView(R.id.tv_addCarItem3)
    public TextView addCarItem3TV;

    @BindView(R.id.tv_addCarItem4)
    public TextView addCarItem4TV;

    @BindView(R.id.tv_addCarItem5)
    public TextView addCarItem5TV;

    @BindView(R.id.tv_addCarItem6)
    public TextView addCarItem6TV;

    @BindView(R.id.iv_addCarItem1)
    public AppCompatImageView addCarItem1IV;

    @BindView(R.id.iv_addCarItem2)
    public AppCompatImageView addCarItem2IV;

    @BindView(R.id.iv_addCarItem3)
    public AppCompatImageView addCarItem3IV;

    @BindView(R.id.iv_addCarItem4)
    public AppCompatImageView addCarItem4IV;

    @BindView(R.id.iv_addCarItem5)
    public AppCompatImageView addCarItem5IV;

    @BindView(R.id.iv_addCarItem6)
    public AppCompatImageView addCarItem6IV;

    public interface CarInfoPassCallback {
        void onPassData(Bundle b);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_add);
        unbinder = ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            mActionSave = toolbar.findViewById(R.id.toolbar_action);
            mActionSave.setOnClickListener(this);
            mActionSave.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.add_type)
    public void onTypeClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.TYPE);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_info)
    public void onInfoClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.INFO);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_image)
    public void onImageClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.IMAGE);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_location)
    public void onLocationClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.LOCATION);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_contact)
    public void onContactClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.CONTACT);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_payment)
    public void onPaymentClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.PAYMENT);
        openNewCarEditActivity(args);
    }

    private void openNewCarEditActivity(Bundle args) {
        Intent intent = new Intent(this, NewFormsActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSubmit() {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }
}
