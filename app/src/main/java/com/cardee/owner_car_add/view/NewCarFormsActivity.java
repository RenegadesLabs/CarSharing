package com.cardee.owner_car_add.view;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.NewCar;
import com.cardee.owner_car_add.view.items.CarContactsFragment;
import com.cardee.owner_car_add.view.items.CarImageFragment;
import com.cardee.owner_car_add.view.items.CarInfoFragment;
import com.cardee.owner_car_add.view.items.CarLocationFragment;
import com.cardee.owner_car_add.view.items.CarPaymentFragment;
import com.cardee.owner_car_add.view.items.CarTypeFragment;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.Serializable;

public class NewCarFormsActivity extends AppCompatActivity
        implements NewCarFormsContract.View,
        DetailsChangedListener,
        View.OnClickListener {

    private static final String TAG = NewCarFormsContract.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 101;

    private TextView titleView;
    private ProgressBar progress;
    private View btnSave;
    private View btnToNext;
    private View btnAllDone;

    private Toast currentToast;

    private SimpleBinder childBinder;
    private NewCarFormsContract.Mode currentMode;

    private boolean isInRootMode = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            titleView = toolbar.findViewById(R.id.toolbar_title);
            btnSave = toolbar.findViewById(R.id.toolbar_action);
        }
        btnToNext = findViewById(R.id.btn_to_next);
        btnAllDone = findViewById(R.id.btn_all_done);
        btnToNext.setOnClickListener(this);
        btnAllDone.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.new_car_progress);
        Serializable extra = getIntent().getSerializableExtra(NewCarFormsContract.VIEW_MODE);
        if (extra != null) {
            NewCarFormsContract.Mode mode = (NewCarFormsContract.Mode) extra;
            setContentOfMode(mode);
            return;
        }
        Toast.makeText(this, "Mode is null", Toast.LENGTH_SHORT).show(); //PLUG
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

    private void setContentOfMode(NewCarFormsContract.Mode mode) {
        switch (mode) {
            case TYPE:
                showFragment(CarTypeFragment.newInstance());
                break;
            case INFO:
                showFragment(CarInfoFragment.newInstance());
                break;
            case IMAGE:
                showFragment(CarImageFragment.newInstance());
                break;
            case LOCATION:
                showFragment(CarLocationFragment.newInstance());
                break;
            case CONTACT:
                showFragment(CarContactsFragment.newInstance());
                break;
            case PAYMENT:
                showFragment(CarPaymentFragment.newInstance());
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isInRootMode) {
            transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right);
            transaction.addToBackStack(null);
        } else {
            isInRootMode = false;
        }
        transaction.replace(R.id.fragment_container, fragment).commit();
    }

    private NewCarFormsContract.Mode getNextToMode(NewCarFormsContract.Mode mode) {
        switch (mode) {
            case TYPE:
                return NewCarFormsContract.Mode.INFO;
            case INFO:
                return NewCarFormsContract.Mode.IMAGE;
            case IMAGE:
                return NewCarFormsContract.Mode.LOCATION;
            case LOCATION:
                return NewCarFormsContract.Mode.CONTACT;
            case CONTACT:
                return NewCarFormsContract.Mode.PAYMENT;
            default:
                return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_next:
                if (childBinder != null) {
                    Bundle args = new Bundle();
                    args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.PUSH);
                }
                break;
            case R.id.btn_all_done:
                break;
        }
    }

    @Override
    public void onModeDisplayed(NewCarFormsContract.Mode mode) {
        currentMode = mode;
        if (isLastStep(currentMode)) {
            btnAllDone.setVisibility(View.VISIBLE);
            btnToNext.setVisibility(View.GONE);
        } else {
            btnAllDone.setVisibility(View.GONE);
            btnToNext.setVisibility(View.VISIBLE);
        }
        if (titleView != null) {
            titleView.setText(mode.getTitleId());
        }
    }

    private boolean isLastStep(NewCarFormsContract.Mode mode) {
        return mode == NewCarFormsContract.Mode.PAYMENT;
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
    public void onFinish(NewCarFormsContract.Mode mode) {
        if (currentMode != mode) {
            throw new IllegalArgumentException("Mode mismatch: " + currentMode + " vs " + mode);
        }
        NewCarFormsContract.Mode nextMode = getNextToMode(mode);
        if (nextMode != null) {
            setContentOfMode(nextMode);
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

    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void setCarData(NewCar carData) {

    }
}
