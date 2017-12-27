package com.cardee.renter_bookings.rate_rental_exp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cardee.R;
import com.cardee.renter_bookings.rate_rental_exp.presenter.RateRentalExpPresenter;

import butterknife.ButterKnife;

public class RateRentalExpActivity extends AppCompatActivity implements RateRentalExpView {

    private RateRentalExpPresenter mPresenter;
    private boolean mHasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_rental_exp);
        ButterKnife.bind(this);

        mPresenter = new RateRentalExpPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showFirstFragment();
    }

    private void showFirstFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FirstRateFragment.class.getName());
        if (fragment == null) {
            fragment = FirstRateFragment.newInstance();
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
        mHasFragment = true;
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
