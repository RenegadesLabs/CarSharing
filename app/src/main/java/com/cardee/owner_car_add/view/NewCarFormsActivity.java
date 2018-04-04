package com.cardee.owner_car_add.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.modal.SelectPictureFragment;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.view.items.CarContactsFragment;
import com.cardee.owner_car_add.view.items.CarImageFragment;
import com.cardee.owner_car_add.view.items.CarInfoFragment;
import com.cardee.owner_car_add.view.items.CarLocationFragment;
import com.cardee.owner_car_add.view.items.CarPaymentFragment;
import com.cardee.owner_car_add.view.items.CarTypeFragment;
import com.cardee.owner_car_add.view.items.UploadImageListener;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewCarFormsActivity extends AppCompatActivity
        implements NewCarFormsContract.View,
        DetailsChangedListener,
        View.OnClickListener,
        UploadImageListener,
        SelectPictureFragment.DialogOnClickListener {

    private static final String TAG = NewCarFormsContract.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final int IMAGE_REQUEST_CODE = 102;
    private static final int REQUEST_READ_PERMISSION_CODE = 103;
    private static final int REQUEST_WRITE_PERMISSION_CODE = 104;
    private static final int REQUEST_IMAGE_CAPTURE = 105;

    private TextView titleView;
    private ProgressBar progress;
    private View btnSave;
    private View btnToNext;
    private View btnAllDone;

    private Toast currentToast;

    private SimpleBinder childBinder;
    private NewCarFormsContract.Mode currentMode;
    private String currentPhotoPath;

    private boolean isInRootMode = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            titleView = toolbar.findViewById(R.id.toolbar_title);
            btnSave = toolbar.findViewById(R.id.toolbar_action);
            btnSave.setOnClickListener(this);
            btnSave.setVisibility(View.GONE);
        }
        btnToNext = findViewById(R.id.btn_to_next);
        btnAllDone = findViewById(R.id.btn_all_done);
        btnToNext.setOnClickListener(this);
        btnAllDone.setOnClickListener(this);
        progress = findViewById(R.id.new_car_progress);
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

    @Override
    public void onBackPressed() {
        if (childBinder != null) {
            Bundle args = new Bundle();
            args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.SAVE);
            childBinder.push(args);
        }
        super.onBackPressed();
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

    private NewCarFormsContract.Mode getNextToMode(NewCarFormsContract.Mode mode) {
        switch (mode) {
            case TYPE:
                return NewCarFormsContract.Mode.INFO;
            case INFO:
                return NewCarFormsContract.Mode.IMAGE;
            case IMAGE:
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
                    args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.FINISH);
                    childBinder.push(args);
                }
                break;
            case R.id.btn_all_done:
            case R.id.toolbar_action:
                if (childBinder != null) {
                    Bundle args = new Bundle();
                    args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.SAVE);
                    childBinder.push(args);
                }
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
    public void onFinish(NewCarFormsContract.Mode mode, NewCarFormsContract.Action action) {
        if (currentMode != mode || action == null) {
            return;
//            throw new IllegalArgumentException("Mode mismatch: " + currentMode + " vs " + mode);
        }
        switch (action) {
            case SAVE:
                finish();
                break;
            case FINISH:
                NewCarFormsContract.Mode nextMode = getNextToMode(mode);
                if (nextMode != null) {
                    setContentOfMode(nextMode);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PERMISSION_CODE) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onGalleryClicked();
                return;
            }
        }
        if (requestCode == REQUEST_WRITE_PERMISSION_CODE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onCameraClicked();
                return;
            }
        }
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
            Bundle args = new Bundle();
            args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.UPDATE);
            childBinder.push(args);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    if (data != null && childBinder != null) {
                        Bundle args = new Bundle();
                        args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.UPDATE);
                        args.putParcelable(NewCarFormsContract.URI, data.getData());
                        childBinder.push(args);
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    if (childBinder != null) {
                        Bundle args = new Bundle();
                        args.putSerializable(NewCarFormsContract.ACTION, NewCarFormsContract.Action.UPDATE);
                        args.putParcelable(NewCarFormsContract.URI, Uri.parse(currentPhotoPath));
                        childBinder.push(args);
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
    public void onNoSavedCar() {

    }

    @Override
    public void onImageUpload() {
        SelectPictureFragment menu = new SelectPictureFragment();
        menu.show(getSupportFragmentManager(), menu.getTag());
        menu.setListener(this);
//        ActivityHelper.pickImageIntent(this, ActivityHelper.PICK_IMAGE);
    }

    @Override
    public void onCameraClicked() {
        if (!hasWritePermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION_CODE);
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onGalleryClicked() {
        if (!hasReadPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_PERMISSION_CODE);
            return;
        }
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMAGE_REQUEST_CODE);
    }

    private boolean hasReadPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWritePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
