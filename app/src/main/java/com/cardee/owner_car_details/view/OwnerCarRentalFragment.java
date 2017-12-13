package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.owner_car_details.RentalDetailsContract;
import com.cardee.owner_car_details.presenter.RentalDetailsPresenter;
import com.cardee.owner_car_details.view.adapter.OnTabSelectedAdapter;
import com.cardee.owner_car_details.view.listener.ChildProgressListener;
import com.cardee.owner_car_details.view.viewholder.BaseViewHolder;
import com.cardee.owner_car_details.view.viewholder.DailyRentalViewHolder;
import com.cardee.owner_car_details.view.viewholder.HourlyRentalViewHolder;

import java.util.HashMap;
import java.util.Map;

public class OwnerCarRentalFragment extends Fragment
        implements ChildProgressListener, RentalDetailsContract.View {

    private static final String CAR_ID = "car_id";
    public final static String MODE = "key_rental_mode";
    public final static int HOURLY = 0;
    public final static int DAILY = 1;
    private final int[] mTabTitleIds = {R.string.car_rental_info_hourly, R.string.car_rental_info_daily};

    private TabLayout mTabLayout;
    private ViewPager mPager;
    private View progressLayout;
    private ContentPage currentPage;
    private Map<ContentPage, BaseViewHolder<RentalDetails>> views;
    private RentalDetailsPresenter presenter;
    private Toast currentToast;
    private SwitchCompat dailySwitch;
    private SwitchCompat hourlySwitch;

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
        Bundle args = getArguments();
        presenter = new RentalDetailsPresenter(this, args.containsKey(CAR_ID) ? args.getInt(CAR_ID) : -1);
        views = new HashMap<>();
        initPages(rootView, new String[]{getString(mTabTitleIds[0], mTabTitleIds[1])});
        progressLayout = rootView.findViewById(R.id.progress_layout);
        dailySwitch = rootView.findViewById(R.id.sw_rentalMainDaily);
        hourlySwitch = rootView.findViewById(R.id.sw_rentalMainHourly);
        dailySwitch.setOnCheckedChangeListener(presenter);
        hourlySwitch.setOnCheckedChangeListener(presenter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
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
                hourlySwitch.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                dailySwitch.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void setData(RentalDetails rentalDetails) {
        BaseViewHolder<RentalDetails> pageView;
        pageView = views.get(ContentPage.DAILY);
        if (pageView != null) {
            pageView.bind(rentalDetails);
        }
        pageView = views.get(ContentPage.HOURLY);
        if (pageView != null) {
            pageView.bind(rentalDetails);
        }
        dailySwitch.setChecked(rentalDetails.isAvailableDaily());
        hourlySwitch.setChecked(rentalDetails.isAvailableHourly());
        presenter.setRentalDetails(rentalDetails);
    }

    @Override
    public void onDailyChange(boolean available) {
        if (available ^ dailySwitch.isChecked()) {
            dailySwitch.setOnCheckedChangeListener(null);
            dailySwitch.setChecked(available);
            dailySwitch.setOnCheckedChangeListener(presenter);
        }
    }

    @Override
    public void onHourlyChange(boolean available) {
        if (available ^ hourlySwitch.isChecked()) {
            dailySwitch.setOnCheckedChangeListener(null);
            dailySwitch.setChecked(available);
            dailySwitch.setOnCheckedChangeListener(presenter);
        }
    }

    @Override
    public void onChildProgressShow(boolean show) {
        showProgress(show);
    }

    @Override
    public void showProgress(boolean show) {
        progressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        getView().setAlpha(show ? .5f : 1f);
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }


    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
                holder = new DailyRentalViewHolder(layout, (AppCompatActivity) getActivity());
                ((DailyRentalViewHolder) holder).setProgressListener(OwnerCarRentalFragment.this);
            } else {
                holder = new HourlyRentalViewHolder(layout, (AppCompatActivity) getActivity());
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
