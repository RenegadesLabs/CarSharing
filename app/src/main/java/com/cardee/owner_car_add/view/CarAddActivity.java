package com.cardee.owner_car_add.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.cardee.R;
import com.cardee.owner_car_add.view.items.CarAddCarInfoFragment;
import com.cardee.owner_car_add.view.items.CarAddVehicleFragment;
import com.cardee.owner_car_add.view.items.CarAddImageFragment;
import com.cardee.owner_car_add.view.items.CarAddLocationFragment;
import com.cardee.owner_car_add.view.items.CarAddContactInfoFragment;
import com.cardee.owner_car_add.view.items.CarAddPaymentAccountFragment;
import com.cardee.owner_car_add.view.items.CarAddBaseFragment;
import com.cardee.util.display.ActivityHelper;

import java.io.IOException;

public class CarAddActivity extends AppCompatActivity implements CarAddView, View.OnClickListener {

    private TextView mTitle;

    private View mActionSave;

    private FragmentManager mFragmentManager;

    private CarAddMainFragment mCarAddMainFragment;

    private CarAddVehicleFragment mCarAddVehicleFragment;

    private CarAddCarInfoFragment mCarAddCarInfoFragment;

    private CarAddImageFragment mCarAddImageFragment;

    private CarAddActionListener mLister;


    public interface CarInfoPassCallback {
        void onPassData(Bundle b);
    }


    public interface CarAddActionListener {
        void onSaveClicked();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_add);
        initToolbar();
        initFragments();

        mFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, mCarAddMainFragment)
                .commit();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();

        mCarAddMainFragment = new CarAddMainFragment();
        mCarAddMainFragment.setViewListener(this);

        mCarAddVehicleFragment = new CarAddVehicleFragment();
        mCarAddVehicleFragment.setViewListener(this);
        mCarAddVehicleFragment.setPassDataCallback(mCarAddMainFragment.getListener());
        mCarAddVehicleFragment.setArguments(new Bundle());

        mCarAddCarInfoFragment = new CarAddCarInfoFragment();
        mCarAddCarInfoFragment.setViewListener(this);
        mCarAddCarInfoFragment.setPassDataCallback(mCarAddMainFragment.getListener());
        mCarAddCarInfoFragment.setArguments(new Bundle());

        mCarAddImageFragment = new CarAddImageFragment();
        mCarAddImageFragment.setViewListener(this);
        mCarAddImageFragment.setPassDataCallback(mCarAddMainFragment.getListener());;

    }


    private void initToolbar() {
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.car_add_title);
            mActionSave = toolbar.findViewById(R.id.toolbar_action);
            mActionSave.setOnClickListener(this);
            mActionSave.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void replaceFragment(Fragment fragment, int resIdTitle) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        mTitle.setText(resIdTitle);
        if (fragment instanceof CarAddBaseFragment) {
            mActionSave.setVisibility(View.VISIBLE);
        } else {
            mActionSave.setVisibility(View.GONE);
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
        Fragment f = mFragmentManager.findFragmentById(R.id.fragment_container);
        if (f instanceof CarAddBaseFragment) {
            mCarAddMainFragment.setArguments(f.getArguments());
            replaceFragment(mCarAddMainFragment, R.string.car_add_title);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ActivityHelper.PICK_IMAGE:
                if (resultCode == RESULT_OK && data.getData() != null) {
//                    cropImageIntent(data.getData());
                    if (data.getExtras() != null) {
//                        Bundle extras = data.getExtras();
//                        Bitmap bitmap = extras.getParcelable("data");
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            if (mCarAddImageFragment != null && mCarAddImageFragment.isVisible()) {
                                mCarAddImageFragment.setUserPhoto(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
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

    @Override
    public void onVehicleType() {
        replaceFragment(mCarAddVehicleFragment, R.string.car_add_vehicle_title);
    }

    @Override
    public void onCarInfo() {
        replaceFragment(mCarAddCarInfoFragment, R.string.car_add_info_title);
    }

    @Override
    public void onCarImage() {
        replaceFragment(new CarAddImageFragment(), R.string.car_add_image_title);
    }

    @Override
    public void onCarLocation() {
        replaceFragment(new CarAddLocationFragment(), R.string.car_add_location_title);
    }

    @Override
    public void onContactInfo() {
        replaceFragment(new CarAddContactInfoFragment(), R.string.car_add_contact_title);
    }

    @Override
    public void onPaymentAccount() {
        replaceFragment(new CarAddPaymentAccountFragment(), R.string.car_add_payment_title);
    }

    @Override
    public void onUploadPicture() {
        ActivityHelper.pickImageIntent(this, ActivityHelper.PICK_IMAGE);
    }

    @Override
    public void onSubmit() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                if (mLister != null)
                    mLister.onSaveClicked();
                break;
        }
    }

    public void setActionListener(CarAddActionListener listener) {
        mLister = listener;
    }
}
