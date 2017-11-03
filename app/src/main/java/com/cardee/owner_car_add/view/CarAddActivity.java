package com.cardee.owner_car_add.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.cardee.R;
import com.cardee.owner_car_add.view.items.CarAddItem1Fragment;
import com.cardee.owner_car_add.view.items.CarAddItem2Fragment;
import com.cardee.owner_car_add.view.items.CarAddItem3Fragment;
import com.cardee.owner_car_add.view.items.CarAddItem4Fragment;
import com.cardee.owner_car_add.view.items.CarAddItem5Fragment;
import com.cardee.owner_car_add.view.items.CarAddItem6Fragment;
import com.cardee.owner_car_add.view.items.CarAddItemFragment;

import butterknife.ButterKnife;

public class CarAddActivity extends AppCompatActivity implements CarAddView, View.OnClickListener {

    private TextView mTitle;

    private View mActionSave;

    private FragmentManager mFragmentManager;

    private CarAddMainFragment mCarAddMainFragment;

    private CarAddActionListener mLister;



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
        if (fragment instanceof CarAddItemFragment) {
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
        if (f instanceof CarAddItemFragment) {
            mCarAddMainFragment.setArguments(f.getArguments());
            replaceFragment(mCarAddMainFragment, R.string.car_add_title);
            return;
        }
        super.onBackPressed();
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
    public void onItem1() {
        CarAddItem1Fragment f = new CarAddItem1Fragment();
        f.setArguments(new Bundle());
        replaceFragment(f, R.string.car_add_vehicle_title);
    }

    @Override
    public void onItem2() {
        replaceFragment(new CarAddItem2Fragment(), R.string.car_add_info_title);
    }

    @Override
    public void onItem3() {
        replaceFragment(new CarAddItem3Fragment(), R.string.car_add_image_title);
    }

    @Override
    public void onItem4() {
        replaceFragment(new CarAddItem4Fragment(), R.string.car_add_location_title);
    }

    @Override
    public void onItem5() {
        replaceFragment(new CarAddItem5Fragment(), R.string.car_add_contact_title);
    }

    @Override
    public void onItem6() {
        replaceFragment(new CarAddItem6Fragment(), R.string.car_add_payment_title);
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
