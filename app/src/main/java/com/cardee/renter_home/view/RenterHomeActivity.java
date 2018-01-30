package com.cardee.renter_home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.cardee.R;
import com.cardee.owner_home.view.helper.BottomNavigationHelper;
import com.cardee.owner_home.view.service.FragmentFactory;
import com.cardee.renter_browse_cars.view.RenterBrowseCarsFragment;
import com.cardee.renter_home.view.listener.RenterMoreTabEventListener;

public class RenterHomeActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, RenterMoreTabEventListener {

    private static final String TAG = RenterHomeActivity.class.getSimpleName();
    private boolean mHasFragment;
    private ProgressBar mProgress;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_home);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
        mProgress = (ProgressBar) findViewById(R.id.home_progress);
        AHBottomNavigation bottomMenu = (AHBottomNavigation) findViewById(R.id.bottom_menu);
        BottomNavigationHelper.prepareForRenter(bottomMenu);
        bottomMenu.setOnTabSelectedListener(this);
        bottomMenu.disableItemAtPosition(1); //Just for demo
        bottomMenu.disableItemAtPosition(2); //Just for demo
        bottomMenu.setCurrentItem(0);

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
//        if (!wasSelected) {
        switch (position) {
            case 1:
            case 2:
            case 3:
                getSupportActionBar().hide();
                break;
            case 0:
                getSupportActionBar().hide();
                break;
        }
        showFragmentOnPosition(position);
//        }
        return true;
    }

    private void showFragmentOnPosition(int position) {
        Class fragmentClazz = null;
        switch (position) {
            case 0:
                fragmentClazz = RenterBrowseCarsFragment.class;
                break;
            case 1:
                fragmentClazz = RenterBookingsFragment.class;
                break;
            case 3:
                fragmentClazz = RenterProfileFragment.class;
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
    public void onStartLoading() {
        mProgress.setVisibility(View.VISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        }, 5000); //hide progress bar if there is no response for 5 seconds
    }

    @Override
    public void onStopLoading() {
        mProgress.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
