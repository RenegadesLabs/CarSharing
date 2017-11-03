package com.cardee.owner_home.view;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.view.OwnerCarDetailsActivity;
import com.cardee.owner_home.view.helper.BottomNavigationHelper;
import com.cardee.owner_home.view.listener.CarListItemEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.cardee.owner_home.view.modal.AvailabilityMenuFragment;
import com.cardee.owner_home.view.service.FragmentFactory;

public class OwnerHomeActivity extends AppCompatActivity
        implements AHBottomNavigation.OnTabSelectedListener,
        CarListItemEventListener, View.OnClickListener {

    private static final String TAG = OwnerHomeActivity.class.getSimpleName();

    private boolean mHasFragment;
    private TextView mTitle;
    private View mAddCarAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            mTitle = toolbar.findViewById(R.id.toolbar_title);
            mAddCarAction = toolbar.findViewById(R.id.toolbar_action);
            mAddCarAction.setOnClickListener(this);
        }
        AHBottomNavigation bottomMenu = (AHBottomNavigation) findViewById(R.id.bottom_menu);
        BottomNavigationHelper.prepare(bottomMenu);
        bottomMenu.setOnTabSelectedListener(this);
        bottomMenu.setCurrentItem(1);
        bottomMenu.disableItemAtPosition(0); //Just for demo
        bottomMenu.disableItemAtPosition(2); //Just for demo
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected) {
            switch (position) {
                case 0:
                case 3:
                    getSupportActionBar().hide();
                    break;
                case 1:
                    getSupportActionBar().show();
                    mTitle.setText(R.string.title_my_cars);
                    break;
                case 2:
                    getSupportActionBar().show();
                    mTitle.setText(R.string.title_my_bookings);
                    break;
            }
            showFragmentOnPosition(position);
        }
        return true;
    }

    private void showFragmentOnPosition(int position) {
        Class fragmentClazz = null;
        switch (position) {
            case 1:
                fragmentClazz = OwnerCarsFragment.class;
                break;
            default:
                Log.e(TAG, "Position changed to: " + position);
        }

        if (fragmentClazz == null) {
            Log.e(TAG, "No Fragment specified for the position: " + position);
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClazz.getName());
        if (fragment == null) {
            fragment = FragmentFactory.getInstance(fragmentClazz);
        }

        if (fragment == null) {
            Log.e(TAG, "Fragment instance of class " + fragmentClazz.getSimpleName() + " is null");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mHasFragment) {
            transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.e(TAG, fragment.toString());
        mHasFragment = true;
    }

    @Override
    public void onCarItemClick(Car car) {
        Intent intent = new Intent(this, OwnerCarDetailsActivity.class);
        Bundle args = new Bundle();
        args.putInt(OwnerCarDetailsContract.CAR_ID, car.getCarId() == null ? -1 : car.getCarId());
        args.putString(OwnerCarDetailsContract.CAR_NUMBER, car.getLicenceNumber());
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void onHourlyPickerClick(Car car) {
        AvailabilityMenuFragment menuFragment = AvailabilityMenuFragment.getInstance(AvailabilityMenuFragment.Mode.HOURLY);
        menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
    }

    @Override
    public void onDailyPickerClick(Car car) {
        AvailabilityMenuFragment menuFragment = AvailabilityMenuFragment.getInstance(AvailabilityMenuFragment.Mode.DAILY);
        menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
    }

    @Override
    public void onLocationPickerClick(Car car) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                startActivity(new Intent(this, CarAddActivity.class));
                break;
        }
    }
}
