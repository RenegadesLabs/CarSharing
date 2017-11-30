package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.custom.modal.DetailsMoreMenuFragment;

public class OwnerCarDetailsActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private static final String TAG = OwnerCarDetailsActivity.class.getSimpleName();

    private int mCarId;
    private TabLayout mTabs;
    private ViewPager mPager;
    private TextView mTitle;
    private View mMoreAction;
    private View mBtnRequests;

    private int[] mTabTitleIds = {
            R.string.car_details_tab_rental,
            R.string.car_details_tab_car,
            R.string.car_details_tab_reviews};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_details);

        mCarId = getIntent().getIntExtra(OwnerCarDetailsContract.CAR_ID, -1);
        if (mCarId == -1) {
            Log.e(TAG, "Car ID is not specified");
            finish(); //Exit if there is no CAR_ID argument in intent
            return;
        }
        String[] tabTitles = {getString(mTabTitleIds[0]), getString(mTabTitleIds[1]), getString(mTabTitleIds[2])};
        String title = getIntent().getStringExtra(OwnerCarDetailsContract.CAR_NUMBER);
        initContent(title, tabTitles);
    }

    private void initContent(String title, String[] tabTitles) {
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(title);
            mMoreAction = toolbar.findViewById(R.id.toolbar_action);
            mMoreAction.setOnClickListener(this);
            mBtnRequests = findViewById(R.id.button_requests);
        }
        initPages(tabTitles);
    }

    private void initPages(String[] tabTitles) {
        mTabs = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.car_details_pager);
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabTitles));
        mTabs.setupWithViewPager(mPager);
        mTabs.getTabAt(1).select();
        mTabs.addOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 2:
                mBtnRequests.setVisibility(View.GONE);
                break;
            default:
                if (mBtnRequests.getVisibility() != View.VISIBLE) {
                    mBtnRequests.setVisibility(View.VISIBLE);
                }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                DetailsMoreMenuFragment menu = new DetailsMoreMenuFragment();
                menu.show(getSupportFragmentManager(), menu.getTag());
                break;
        }
    }


    private class PagerAdapter extends FragmentStatePagerAdapter {

        private int mTabCount;
        private String[] mTitles;

        public PagerAdapter(FragmentManager fm, String[] tabTitles) {
            super(fm);
            mTabCount = tabTitles.length;
            mTitles = tabTitles;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = OwnerCarRentalFragment.newInstance(mCarId);
                    break;
                case 1:
                    fragment = OwnerCarDetailsFragment.newInstance(mCarId);
                    break;
                case 2:
                    fragment = OwnerCarReviewsFragment.newInstance(mCarId);
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mTabCount;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
