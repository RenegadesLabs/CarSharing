package com.cardee.owner_car_details.view;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_add.view.NewCarContract;
import com.cardee.owner_car_add.view.items.CarLocationFragment;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.Serializable;

public class CarDetailsEditActivity extends AppCompatActivity
        implements CarDetailsEditContract.View,
        DetailsChangedListener,
        View.OnClickListener {

    private static final String TAG = CarDetailsEditActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 101;

    private TextView mTitleView;
    private View mBtnSave;
    private ProgressBar mProgress;

    private Toast mCurrentToast;

    private SimpleBinder childBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details_edit);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            mTitleView = toolbar.findViewById(R.id.toolbar_title);
            mBtnSave = toolbar.findViewById(R.id.toolbar_action);
            mBtnSave.setOnClickListener(this);
        }
        mProgress = (ProgressBar) findViewById(R.id.details_progress);
        int carId = getIntent().getIntExtra(NewCarContract.CAR_ID, -1);
        Serializable extra = getIntent().getSerializableExtra(NewCarContract.VIEW_MODE);
        if (extra != null) {
            NewCarContract.Mode mode = (NewCarContract.Mode) extra;
            setContentOfMode(mode, carId == -1 ? null : carId);
            return;
        }
        Toast.makeText(this, "Mode is null", Toast.LENGTH_SHORT).show(); //PLUG
    }

    private void setContentOfMode(NewCarContract.Mode mode, Integer carId) {
        Bundle args = getIntent().getExtras();
        if (args == null) {
            return;
        }
        switch (mode) {
            case LOCATION:
                showFragment(CarLocationFragment.newInstance(carId)); //add valid car ID
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
    public void onModeDisplayed(NewCarContract.Mode mode) {
        mTitleView.setText(mode.getTitleId());
    }

    @Override
    public void onBind(SimpleBinder binder) {
        childBinder = binder;
    }

    @Override
    public void onNeedPermission(String... permissions) {
        if (permissions == null) {
            Log.e(TAG, "At least one permission should be passed as an argument");
            return;
        }
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onFinish(NewCarContract.Mode mode, NewCarContract.Action action) {
        switch (action) {
            case SAVE:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (childBinder == null) {
                Log.e(TAG, "There is no binder to notify");
            }
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    //TODO implement permission denied behaviour
                    return;
                }
            }
            childBinder.push(null);
        }
    }

    @Override
    public void showProgress(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                if (childBinder != null) {
                    Bundle args = new Bundle();
                    args.putSerializable(NewCarContract.ACTION, NewCarContract.Action.SAVE);
                    childBinder.push(args);
                }
                break;
        }
    }
}
