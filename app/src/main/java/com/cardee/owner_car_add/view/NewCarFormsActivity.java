package com.cardee.owner_car_add.view;

import android.content.Intent;
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
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.view.items.CarContactsFragment;
import com.cardee.owner_car_add.view.items.CarImageFragment;
import com.cardee.owner_car_add.view.items.CarInfoFragment;
import com.cardee.owner_car_add.view.items.CarLocationFragment;
import com.cardee.owner_car_add.view.items.CarPaymentFragment;
import com.cardee.owner_car_add.view.items.CarTypeFragment;
import com.cardee.owner_car_add.view.items.UploadImageListener;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;
import com.cardee.util.display.ActivityHelper;

import java.io.Serializable;

public class NewCarFormsActivity extends AppCompatActivity
        implements NewCarContract.View,
        DetailsChangedListener,
        View.OnClickListener,
        UploadImageListener {

    private static final String TAG = NewCarContract.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 101;

    private TextView titleView;
    private ProgressBar progress;
    private View btnSave;
    private View btnToNext;
    private View btnAllDone;

    private Toast currentToast;

    private SimpleBinder childBinder;
    private NewCarContract.Mode currentMode;

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
            btnSave.setOnClickListener(this);
        }
        btnToNext = findViewById(R.id.btn_to_next);
        btnAllDone = findViewById(R.id.btn_all_done);
        btnToNext.setOnClickListener(this);
        btnAllDone.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.new_car_progress);
        Serializable extra = getIntent().getSerializableExtra(NewCarContract.VIEW_MODE);
        if (extra != null) {
            NewCarContract.Mode mode = (NewCarContract.Mode) extra;
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

    @Override
    public void onBackPressed() {
        if(childBinder!=null){
            Bundle args = new Bundle();
            args.putSerializable(NewCarContract.ACTION, NewCarContract.Action.SAVE);
            childBinder.push(args);
        }
        super.onBackPressed();
    }

    private void setContentOfMode(NewCarContract.Mode mode) {
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
                showFragment(CarLocationFragment.newInstance(null));
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

    private NewCarContract.Mode getNextToMode(NewCarContract.Mode mode) {
        switch (mode) {
            case TYPE:
                return NewCarContract.Mode.INFO;
            case INFO:
                return NewCarContract.Mode.IMAGE;
            case IMAGE:
                return NewCarContract.Mode.LOCATION;
            case LOCATION:
                return NewCarContract.Mode.CONTACT;
            case CONTACT:
                return NewCarContract.Mode.PAYMENT;
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
                    args.putSerializable(NewCarContract.ACTION, NewCarContract.Action.FINISH);
                    childBinder.push(args);
                }
                break;
            case R.id.btn_all_done:
                break;
            case R.id.toolbar_action:
                if (childBinder != null) {
                    Bundle args = new Bundle();
                    args.putSerializable(NewCarContract.ACTION, NewCarContract.Action.SAVE);
                    childBinder.push(args);
                }
                break;
        }
    }

    @Override
    public void onModeDisplayed(NewCarContract.Mode mode) {
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

    private boolean isLastStep(NewCarContract.Mode mode) {
        return mode == NewCarContract.Mode.PAYMENT;
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
        if (currentMode != mode || action == null) {
            throw new IllegalArgumentException("Mode mismatch: " + currentMode + " vs " + mode);
        }
        switch (action) {
            case SAVE:
                finish();
                break;
            case FINISH:
                NewCarContract.Mode nextMode = getNextToMode(mode);
                if (nextMode != null) {
                    setContentOfMode(nextMode);
                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ActivityHelper.PICK_IMAGE:
                    if (data != null && childBinder != null) {
                        Bundle args = new Bundle();
                        args.putSerializable(NewCarContract.ACTION, NewCarContract.Action.UPDATE);
                        args.putParcelable(NewCarContract.URI, data.getData());
                        childBinder.push(args);
                        return;
                    }
                    break;
            }
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
    public void setCarData(CarData carData) {

    }

    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void onImageUpload() {
        ActivityHelper.pickImageIntent(this, ActivityHelper.PICK_IMAGE);
    }
}
