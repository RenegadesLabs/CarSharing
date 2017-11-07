package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarLocationFragment;

import java.io.Serializable;

public class CarDetailsEditActivity extends AppCompatActivity implements CarDetailsEditContract.View {

    private TextView mTitleView;
    private View mBtnSave;

    private Toast mCurrentToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details_edit);

        if (getSupportActionBar() != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            mTitleView = toolbar.findViewById(R.id.toolbar_title);
            mBtnSave = toolbar.findViewById(R.id.toolbar_action);
        }

        Serializable extra = getIntent().getSerializableExtra(CarDetailsEditContract.VIEW_MODE);
        if (extra != null) {
            CarDetailsEditContract.Mode mode = (CarDetailsEditContract.Mode) extra;
            mTitleView.setText(mode.getTitleId());
            setContentOfMode(mode);
            return;
        }

        Toast.makeText(this, "Mode is null", Toast.LENGTH_SHORT).show(); //PLUG
    }

    private void setContentOfMode(CarDetailsEditContract.Mode mode) {
        Bundle args = getIntent().getExtras();
        if (args == null) {
            return;
        }
        switch (mode) {
            case LOCATION:
                if (!args.containsKey(CarDetailsEditContract.CAR_LAT) || !args.containsKey(CarDetailsEditContract.CAR_LNG)) {
                    showFragment(CarLocationFragment.newInstance());
                    break;
                }
                float lat = args.getFloat(CarDetailsEditContract.CAR_LAT);
                float lng = args.getFloat(CarDetailsEditContract.CAR_LNG);
                showFragment(CarLocationFragment.newInstance(lat, lng));
                break;
            default:
                Toast.makeText(this, "Mode is not supported yet", Toast.LENGTH_SHORT).show(); //PLUG
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.getClass().getName())
                .commit();
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
