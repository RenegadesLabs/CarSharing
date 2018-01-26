package com.cardee.renter_browse_cars.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;
import com.cardee.renter_browse_cars.adapter.RenterBrowseCarsListAdapter;
import com.cardee.renter_browse_cars.adapter.RenterBrowseCarsSearchListAdapter;
import com.cardee.renter_browse_cars.filter.view.FilterActivity;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListPresenter;
import com.cardee.renter_browse_cars.search_area.view.SearchAreaActivity;
import com.cardee.renter_browse_cars.view.custom.RenterBrowseCarsFloatingView;
import com.cardee.renter_browse_cars.view.custom.listener.CustomRecyclerScrollListener;
import com.cardee.renter_browse_cars_map.BrowseCarsMapActivity;
import com.cardee.settings.Settings;
import com.cardee.settings.SettingsManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;


public class RenterBrowseCarsFragment extends Fragment implements RenterBrowseCarListContract.View,
        View.OnClickListener {

    private final static int LOCATION_REQUEST_CODE = 111;
    private final static int FILTER_REQUEST_CODE = 112;

    private RenterBrowseCarsListAdapter mCarsListAdapter;
    private RenterBrowseCarsSearchListAdapter mSearchListAdapter;
    private RenterBrowseCarListPresenter mPresenter;

    @BindView(R.id.rv_renterBrowseCarsList)
    public RecyclerView mCarsListView;
    @BindView(R.id.lv_renterBrowseCarsSearchList)
    public ListView mSearchListView;
    @BindView(R.id.v_renterBrowseCarsFloating)
    public RenterBrowseCarsFloatingView mFloatingView;
    @BindView(R.id.v_renterBrowseCarsHeader)
    public LinearLayout mHeaderView;
    @BindView(R.id.iv_renterCarsToolbarFavoritesImg)
    public AppCompatImageView mFavsImage;
    @BindView(R.id.toolbar_search)
    public Toolbar mSearchView;
    @BindView(R.id.et_searchCarsInput)
    AppCompatEditText mSearchInput;
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.p_renterBrowseCarsSearch)
    public ProgressBar mSearchProgress;
    @BindView(R.id.p_renterBrowseCars)
    public ProgressBar mProgressBar;
    @BindView(R.id.searchAreaAddress)
    public TextView mSearchAreaAddress;
    @BindView(R.id.tv_browseCarsHeaderRadiusTxt)
    public TextView mSearchAreaRadius;

    private boolean favoritesSelected = false;
    private boolean search = false;
    private BrowseCarsFilter mFilter;

    private Unbinder mUnbinder;

    public static Fragment newInstance() {
        return new RenterBrowseCarsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarsListAdapter = new RenterBrowseCarsListAdapter(getActivity());
        mSearchListAdapter = new RenterBrowseCarsSearchListAdapter(getActivity(), -1, new ArrayList<>());
        Settings settings = SettingsManager.getInstance(getActivity()).obtainSettings();
        mPresenter = new RenterBrowseCarListPresenter(this, settings);
        mCarsListAdapter.subscribe(mPresenter);
        mFilter = new BrowseCarsFilter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_renter_cars, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        addOnScrollListener();
        mCarsListView.setAdapter(mCarsListAdapter);
        mCarsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mCarsListView.setItemAnimator(new DefaultItemAnimator());
        mSearchListView.setAdapter(mSearchListAdapter);
        if (mFilter.getByLocation()) {
            mSearchAreaAddress.setText(mFilter.getAddress());
            String radiusText = String.format(getResources().getString(R.string.cars_browse_search_area_template), mFilter.getRadius());
            if (mFilter.getRadius() == 30) {
                radiusText = getResources().getString(R.string.cars_browse_search_area_default);
            }
            mSearchAreaRadius.setText(radiusText);
        }
        return rootView;
    }

    private void addOnScrollListener() {
        mCarsListView.addOnScrollListener(new CustomRecyclerScrollListener() {
            @Override
            public void show() {
                mHeaderView.animate().translationY(-mHeaderView.getHeight())
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();

                mFloatingView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
            }

            @Override
            public void hide() {

                mHeaderView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();

                mFloatingView.animate().translationY(mFloatingView.getHeight())
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.refresh();
        mPresenter.loadItems();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void setItems(List<OfferCar> cars) {
        mCarsListAdapter.insert(cars);

    }

    @Override
    public void updateItem(OfferCar car) {
        mCarsListAdapter.update(car);
    }

    @Override
    public void removeItem(OfferCar car) {
        mCarsListAdapter.remove(car);
    }

    @Override
    public void openItem(OfferCar car) {

    }

    @Override
    public void showSearchProgress(boolean show) {
        mSearchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setItemsSearchList(List<OfferCar> cars) {
        mSearchListAdapter.update(cars);
    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onConnectionLost() {

    }

    @OnClick({R.id.ll_browseCarsFloatingMapBtn,
            R.id.iv_renterBrowseCarsSearch,
            R.id.b_searchViewCancel,
            R.id.fl_renterCarsToolbarFavorites,
            R.id.ll_browseCarsFloatingSortBtn,
            R.id.fl_renterCarsToolbarFilter,
            R.id.ll_browseCarsHeaderRadius})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_browseCarsFloatingMapBtn:
                startActivity(new Intent(getActivity(), BrowseCarsMapActivity.class));
                break;
            case R.id.iv_renterBrowseCarsSearch:
                toggleSearchView();
                break;
            case R.id.b_searchViewCancel:
                toggleSearchView();
                break;
            case R.id.fl_renterCarsToolbarFavorites:
                toggleShowFavorites();
                break;
            case R.id.ll_browseCarsFloatingSortBtn:
                mPresenter.showSort(getActivity());
                break;
            case R.id.fl_renterCarsToolbarFilter:
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra("filter", mFilter);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
                break;
            case R.id.ll_browseCarsHeaderRadius:
                Intent searchAreaIntent = new Intent(getActivity(), SearchAreaActivity.class);
                startActivityForResult(searchAreaIntent, LOCATION_REQUEST_CODE);
                break;
        }
    }

    private void toggleSearchView() {
        search = !search;
        mToolbar.setVisibility(search ? View.GONE : View.VISIBLE);
        mSearchView.setVisibility(search ? View.VISIBLE : View.GONE);
        if (!search) {
            mSearchListView.setVisibility(View.GONE);
            mSearchInput.setText("");
        }
    }

    private void toggleShowFavorites() {
        favoritesSelected = !favoritesSelected;
        mFavsImage.setImageResource(favoritesSelected ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite);
        if (favoritesSelected) {
            mFilter.setFavorite(favoritesSelected);
            mPresenter.getCarsByFilter(mFilter);
        } else {
            mFilter.setFavorite(null);
            mPresenter.getCarsByFilter(mFilter);
        }
    }

    @OnTextChanged(R.id.et_searchCarsInput)
    public void onTextChanged(CharSequence text) {
        if (text.length() >= 1) {
            mPresenter.searchCars(text.toString());
            if (mSearchListView.getVisibility() == View.GONE) {
                mSearchListView.setVisibility(View.VISIBLE);
            }
            return;
        }
        mSearchListView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (LOCATION_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        String address = data.getStringExtra("address");
                        int radius = data.getIntExtra("radius", 0);
                        LatLng location = data.getParcelableExtra("location");
                        if (location != null) {
                            mSearchAreaAddress.setText(address);
                            String radiusText = String.format(getResources().getString(R.string.cars_browse_search_area_template), radius);
                            if (radius == 30) {
                                radiusText = getResources().getString(R.string.cars_browse_search_area_default);
                            }
                            mSearchAreaRadius.setText(radiusText);

                            mFilter.setByLocation(true);
                            mFilter.setLatitude(location.latitude);
                            mFilter.setLongitude(location.longitude);
                            mFilter.setRadius(radius);
                            mFilter.setAddress(address);

                            mPresenter.getCarsByFilter(mFilter);
                        }
                    }
                }
                break;
            case (FILTER_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        ArrayList<OfferCar> list = data.getParcelableArrayListExtra("cars");
                        mFilter = data.getParcelableExtra("filter");
                        if (list != null) {
                            setItems(list);
                        }
                    }
                }
                break;
        }
    }
}
