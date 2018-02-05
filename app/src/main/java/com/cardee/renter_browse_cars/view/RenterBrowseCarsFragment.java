package com.cardee.renter_browse_cars.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.renter_availability_filter.AvailabilityDialogActivity;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;
import com.cardee.renter_browse_cars.adapter.RenterBrowseCarsListAdapter;
import com.cardee.renter_browse_cars.filter.view.FilterActivity;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListPresenter;
import com.cardee.renter_browse_cars.search_area.view.SearchAreaActivity;
import com.cardee.renter_browse_cars.view.custom.RenterBrowseCarsFloatingView;
import com.cardee.renter_browse_cars.view.custom.listener.CustomRecyclerScrollListener;
import com.cardee.renter_browse_cars_map.BrowseCarsMapActivity;
import com.cardee.settings.Settings;
import com.cardee.settings.SettingsManager;
import com.cardee.util.AvailabilityFromFilterDelegate;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

import static android.content.Context.LOCATION_SERVICE;


public class RenterBrowseCarsFragment extends Fragment implements RenterBrowseCarListContract.View,
        View.OnClickListener, LocationListener {

    private final static int LOCATION_REQUEST_CODE = 111;
    private final static int FILTER_REQUEST_CODE = 112;
    private final static int PERMISSIONS_REQUEST_ACCESS_LOCATION = 101;
    private final String TAG = this.getClass().getSimpleName();
    private final static int AVAILABILITY_REQUEST_CODE = 113;

    private RenterBrowseCarsListAdapter mCarsListAdapter;
    private RenterBrowseCarListPresenter mPresenter;
    private AvailabilityFromFilterDelegate delegate;

    @BindView(R.id.rv_renterBrowseCarsList)
    public RecyclerView mCarsListView;
    @BindView(R.id.v_renterBrowseCarsFloating)
    public RenterBrowseCarsFloatingView mFloatingView;
    @BindView(R.id.tv_browseCarsFloatingSortText)
    public TextView mSortText;
    @BindView(R.id.v_renterBrowseCarsHeader)
    public FrameLayout mHeaderView;
    @BindView(R.id.iv_renterCarsToolbarFavoritesImg)
    public AppCompatImageView mFavsImage;
    @BindView(R.id.toolbar_search)
    public Toolbar mSearchView;
    @BindView(R.id.et_searchCarsInput)
    public AppCompatEditText mSearchInput;
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
    @BindView(R.id.ll_browseCarsHeaderRadius)
    public ConstraintLayout mSearchAreaContainer;
    @BindView(R.id.tv_browseCarsPeriod)
    public TextView availabilityPeriod;
    @BindView(R.id.tv_browseCarsPeriodSubtitle)
    public TextView availabilityValue;

    private boolean favoritesSelected = false;
    private boolean search = false;
    private int bottomViewOffset;
    private BrowseCarsFilter mFilter;

    private Unbinder mUnbinder;
    private Settings mSettings;

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
        mSettings = SettingsManager.getInstance(getActivity()).obtainSettings();
        mPresenter = new RenterBrowseCarListPresenter(this, mSettings);
        mCarsListAdapter.subscribe(mPresenter);
        mFilter = mPresenter.getFilter();
        delegate = new AvailabilityFromFilterDelegate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomViewOffset = getActivity().getResources().getDimensionPixelSize(R.dimen.floating_view_bottom_margin);
        View rootView = inflater.inflate(R.layout.fragment_renter_cars, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        addOnScrollListener();
        mSortText.setText(mSettings.getSortOffers() == null ? R.string.booking_sort_title : mSettings.getSortOffers().getTitleId());
        mCarsListView.setAdapter(mCarsListAdapter);
        mCarsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mCarsListView.setItemAnimator(new DefaultItemAnimator());
        if (mFilter.getByLocation()) {
            mSearchAreaAddress.setText(mFilter.getAddress());
            int radiusInKm = mFilter.getRadius() / 1000;
            String radiusText = String.format(getResources().getString(R.string.cars_browse_search_area_template), radiusInKm);
            if (radiusInKm == 30) {
                radiusText = getResources().getString(R.string.cars_browse_search_area_default);
            }
            mSearchAreaRadius.setText(radiusText);
        }
        refreshAvailabilityTitle(mFilter);
        return rootView;
    }

    private void addOnScrollListener() {
        mCarsListView.addOnScrollListener(new CustomRecyclerScrollListener() {
            @Override
            public void show() {
                mFloatingView
                        .animate()
                        .translationY(mFloatingView.getHeight() + bottomViewOffset)
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }

            @Override
            public void hide() {
                mFloatingView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.refresh();
        mPresenter.getCarsByFilter(mFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mToolbar.setVisibility(View.VISIBLE);
        mSearchView.setVisibility(View.GONE);
        mSearchInput.setText("");
        mPresenter.saveFilter(mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgress(boolean show) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
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
    public void setSortValue(String value) {
        mSortText.setText(value);
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
    public void onUnauthorized() {

    }

    @Override
    public void onConnectionLost() {

    }

    @Override
    public void checkLocationPermission() {
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }
    }

    private void getCurrentLocation() {
        Location location = null;

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        if (locationManager != null) {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        if (!gpsEnabled && !networkEnabled) {
            Log.d(TAG, "getCurrentLocation: no provider is enabled");
        } else {
            if (networkEnabled) {
                if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
                        || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

            if (gpsEnabled) {
                if (location == null) {
                    if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                            || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }

            if (location != null) {
                BrowseCarsFilter fil = mPresenter.getFilter();
                if (!fil.getByLocation()) {
                    fil.setByLocation(true);
                    fil.setRadius(30000); // Within SG
                }
                fil.setLatitude(location.getLatitude());
                fil.setLongitude(location.getLongitude());
                mFilter = fil;
                mPresenter.saveFilter(fil);
            }
            mPresenter.continueSetSort(RenterBrowseCarListContract.Sort.DISTANCE);
        }
    }

    @OnClick({R.id.ll_browseCarsHeaderPeriod,
            R.id.ll_browseCarsFloatingMapBtn,
            R.id.iv_renterBrowseCarsSearch,
            R.id.b_searchViewCancel,
            R.id.fl_renterCarsToolbarFavorites,
            R.id.ll_browseCarsFloatingSortBtn,
            R.id.ll_renterBrowseCarsType,
            R.id.fl_renterCarsToolbarFilter,
            R.id.ll_browseCarsHeaderRadius})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_browseCarsHeaderPeriod:
                Intent availabilityIntent = new Intent(getActivity(), AvailabilityDialogActivity.class);
                startActivityForResult(availabilityIntent, AVAILABILITY_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.enter_up, 0);
                break;
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
                startActivityForResult(intent, FILTER_REQUEST_CODE);
                break;
            case R.id.ll_browseCarsHeaderRadius:
                Intent searchAreaIntent = new Intent(getActivity(), SearchAreaActivity.class);
                startActivityForResult(searchAreaIntent, LOCATION_REQUEST_CODE);
                break;
            case R.id.ll_renterBrowseCarsType:
                mPresenter.showType(getActivity());
                break;
        }
    }

    private void toggleSearchView() {
        search = !search;
        mToolbar.setVisibility(search ? View.GONE : View.VISIBLE);
        mSearchView.setVisibility(search ? View.VISIBLE : View.GONE);
        if (!search) {
            mSearchInput.setText("");
            mPresenter.getCarsByFilter(mPresenter.getFilter());
        }
    }

    private void toggleShowFavorites() {
        favoritesSelected = !favoritesSelected;
        mFavsImage.setImageResource(favoritesSelected ? R.drawable.ic_favorites_toolbar_filled : R.drawable.ic_favorites_toolbar);
        mFilter = mPresenter.getFilter();
        if (favoritesSelected) {
            mFilter.setFavorite(favoritesSelected);
        } else {
            mFilter.setFavorite(null);
        }
        mPresenter.saveFilter(mFilter);
        mPresenter.getCarsByFilter(mFilter);
    }

    @OnTextChanged(R.id.et_searchCarsInput)
    public void onTextChanged(CharSequence text) {
        if (text.length() >= 1) {
            mPresenter.searchCars(text.toString());
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
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
                            mSearchAreaAddress.requestLayout();
                            String radiusText = String.format(getResources().getString(R.string.cars_browse_search_area_template), radius);
                            if (radius == 30) {
                                radiusText = getResources().getString(R.string.cars_browse_search_area_default);
                            }
                            mSearchAreaRadius.setText(radiusText);

                            mFilter.setByLocation(true);
                            mFilter.setLatitude(location.latitude);
                            mFilter.setLongitude(location.longitude);
                            mFilter.setRadius(radius * 1000);
                            mFilter.setAddress(address);

                            mPresenter.saveFilter(mFilter);
                            mPresenter.getCarsByFilter(mFilter);
                        }

                    }
                }
                break;
            case (FILTER_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        ArrayList<OfferCar> list = data.getParcelableArrayListExtra("cars");
                        if (list != null) {
                            setItems(list);
                        }
                        mFilter = mPresenter.getFilter();
                        if (mFilter.getByLocation()) {
                            String address = mFilter.getAddress();
                            if (address.equals("")) {
                                address = getResources().getString(R.string.current);
                            }
                            mSearchAreaAddress.setText(address);
                            mSearchAreaAddress.requestLayout();
                            int radiusInKm = mFilter.getRadius() / 1000;
                            String radiusText = String.format(getResources().getString(R.string.cars_browse_search_area_template), radiusInKm);
                            if (radiusInKm == 30) {
                                radiusText = getResources().getString(R.string.cars_browse_search_area_default);
                            }
                            mSearchAreaRadius.setText(radiusText);
                        }
                    }
                }
                break;
            case AVAILABILITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mFilter = mPresenter.getFilter();
                    refreshAvailabilityTitle(mFilter);
                    mPresenter.getCarsByFilter(mFilter);
                }
                break;
        }
    }

    private void refreshAvailabilityTitle(BrowseCarsFilter filter) {
        delegate.onSetDateRangeTitle(availabilityPeriod, availabilityValue, filter);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
