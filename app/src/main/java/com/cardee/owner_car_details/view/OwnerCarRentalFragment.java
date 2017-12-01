package com.cardee.owner_car_details.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_rental_terms.view.RentalTermsActivity;

public class OwnerCarRentalFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private static final String CAR_ID = "car_id";

    private final int[] mTabTitleIds =
            {R.string.car_rental_info_hourly,
                    R.string.car_rental_info_daily};

    private TabLayout mTabLayout;
    private ViewPager mPager;

    public static Fragment newInstance(Integer carId) {
        OwnerCarRentalFragment fragment = new OwnerCarRentalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_car_rental, container, false);
        initPages(rootView, new String[]{getString(mTabTitleIds[0], mTabTitleIds[1])});
        return rootView;
    }

    private void initPages(View root, String[] tabTitles) {
        mTabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
        mPager = (ViewPager) root.findViewById(R.id.rental_pager);
        mPager.setAdapter(new PagerAdapter());
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.getTabAt(1).select();
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter implements View.OnClickListener {

        public PagerAdapter() {}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ContentPagerEnum contentPagerEnum = ContentPagerEnum.values()[position];
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(contentPagerEnum.getLayoutResId(), container, false);
            layout.findViewById(R.id.cl_rentalTermsContainer).setOnClickListener(this);
            container.addView(layout);
            return layout;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            ContentPagerEnum customPagerEnum = ContentPagerEnum.values()[position];
            return getActivity().getString(customPagerEnum.getTitleResId());
        }

        @Override
        public int getCount() {
            return ContentPagerEnum.values().length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cl_rentalTermsContainer:
                    getActivity().startActivity(new Intent(getActivity(),
                                    RentalTermsActivity.class));
                    break;
            }
        }
    }

    public enum ContentPagerEnum {

        HOURLY(R.string.car_rental_info_hourly, R.layout.view_rental_hourly),
        DAILY(R.string.car_rental_info_daily, R.layout.view_rental_daily);

        private int mTitleResId;
        private int mLayoutResId;

        ContentPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }
}
