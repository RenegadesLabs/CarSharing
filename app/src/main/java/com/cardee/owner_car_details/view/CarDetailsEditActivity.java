package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;

import java.io.Serializable;

public class CarDetailsEditActivity extends AppCompatActivity implements CarDetailsEditContract.View {


    private TextView mTitleView;
    private View mBtnSave;

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
            setContentOfMode((CarDetailsEditContract.Mode) extra);
            return;
        }

        Toast.makeText(this, "Mode is null", Toast.LENGTH_SHORT).show(); //PLUG
    }

    private void setContentOfMode(CarDetailsEditContract.Mode mode) {
        switch (mode) {
            case LOCATION:

                break;
            default:
                Toast.makeText(this, "Mode is not supported yet", Toast.LENGTH_SHORT).show(); //PLUG
        }
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    private void showLocationFragment() {

    }

    private void showFragment(Fragment fragment) {

    }
}
