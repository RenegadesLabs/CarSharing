package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.owner_car_details.presenter.RentalDetailsPresenter;
import com.cardee.owner_car_details.view.adapter.OnTabSelectedAdapter;
import com.cardee.owner_car_details.view.listener.ChildProgressListener;
import com.cardee.owner_car_details.view.viewholder.BaseViewHolder;
import com.cardee.owner_car_details.view.viewholder.DailyRentalViewHolder;
import com.cardee.owner_car_details.view.viewholder.HourlyRentalViewHolder;

import java.util.HashMap;
import java.util.Map;

public class OwnerCarRentalFragment extends Fragment implements ChildProgressListener {

    private static final String CAR_ID = "car_id";
    public final static String MODE = "key_rental_mode";

    public final static int HOURLY = 0;
    public final static int DAILY = 1;

    private final int[] mTabTitleIds = {R.string.car_rental_info_hourly, R.string.car_rental_info_daily};

    private TabLayout mTabLayout;
    private ViewPager mPager;
    private ContentPage currentPage;
    private Map<ContentPage, BaseViewHolder<RentalDetails>> views;
    private RentalDetailsPresenter presenter;

    public static Fragment newInstance(Integer carId) {
        OwnerCarRentalFragment fragment = new OwnerCarRentalFragment();
        Bundle args = new Bundle();
        args.putInt(CAR_ID, carId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_car_rental, container, false);
        views = new HashMap<>();
        initPages(rootView, new String[]{getString(mTabTitleIds[0], mTabTitleIds[1])});
        Bundle args = getArguments();
        presenter = new RentalDetailsPresenter(args.containsKey(CAR_ID) ? args.getInt(CAR_ID) : -1);
        return rootView;
    }

    private void initPages(View root, String[] tabTitles) {
        mTabLayout = root.findViewById(R.id.tab_layout);
        mPager = root.findViewById(R.id.rental_pager);
        mPager.setAdapter(new RentalPagerAdapter());
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.getTabAt(1).select();
        mTabLayout.addOnTabSelectedListener(new OnTabSelectedAdapter() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                currentPage = ContentPage.values()[position];
            }
        });
    }

    @Override
    public void onChildProgressShow(boolean show) {

    }

    private class RentalPagerAdapter extends PagerAdapter {

        public RentalPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ContentPage contentPage = ContentPage.values()[position];
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(contentPage.getLayoutResId(), container, false);
            BaseViewHolder<RentalDetails> holder;
            if (ContentPage.DAILY.equals(contentPage)) {
                holder = new DailyRentalViewHolder(layout, getActivity());
                ((DailyRentalViewHolder) holder).setProgressListener(OwnerCarRentalFragment.this);
            } else {
                holder = new HourlyRentalViewHolder(layout, getActivity());
                ((HourlyRentalViewHolder) holder).setProgressListener(OwnerCarRentalFragment.this);
            }
            views.put(contentPage, holder);
            container.addView(layout);
            return layout;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            ContentPage customPagerEnum = ContentPage.values()[position];
            return getActivity().getString(customPagerEnum.getTitleResId());
        }

        @Override
        public int getCount() {
            return ContentPage.values().length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public enum ContentPage {

        HOURLY(R.string.car_rental_info_hourly, R.layout.view_rental_hourly),
        DAILY(R.string.car_rental_info_daily, R.layout.view_rental_daily);

        private int mTitleResId;
        private int mLayoutResId;

        ContentPage(int titleResId, int layoutResId) {
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
