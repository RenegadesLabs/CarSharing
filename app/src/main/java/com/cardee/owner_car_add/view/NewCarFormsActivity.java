package com.cardee.owner_car_add.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_details.view.CarDetailsEditContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.Serializable;

public class NewCarFormsActivity extends AppCompatActivity
        implements NewCarFormsContract.View,
        DetailsChangedListener {

    private TextView mTitleView;
    private View mBtnSave;
    private ProgressBar mProgress;

    private Toast mCurrentToast;

    private SimpleBinder childBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            mTitleView = toolbar.findViewById(R.id.toolbar_title);
            mBtnSave = toolbar.findViewById(R.id.toolbar_action);
            mProgress = (ProgressBar) findViewById(R.id.new_car_progress);
        }
        Serializable extra = getIntent().getSerializableExtra(CarDetailsEditContract.VIEW_MODE);
        if (extra != null) {
            NewCarFormsContract.Mode mode = (NewCarFormsContract.Mode) extra;
            mTitleView.setText(mode.getTitleId());
            setContentOfMode(mode);
            return;
        }
        Toast.makeText(this, "Mode is null", Toast.LENGTH_SHORT).show(); //PLUG
    }

    private void setContentOfMode(NewCarFormsContract.Mode mode) {

    }

    @Override
    public void onBind(SimpleBinder binder) {

    }

    @Override
    public void onNeedPermission(String... permissions) {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }
}
