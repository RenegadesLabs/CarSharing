package com.cardee.owner_car_add.view.items;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.presenter.CarLocationPresenter;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_details.service.FetchAddressService;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;
import com.cardee.service.delegate.HideAnimationDelegate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class CarLocationFragment extends Fragment
        implements OnMapReadyCallback,
        NewCarFormsContract.View,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = CarLocationFragment.class.getSimpleName();

    @DrawableRes
    private final int myCurrentLocationIcon = R.drawable.ic_my_location;
    @DrawableRes
    private final int anyOtherLocationIcon = R.drawable.ic_other_location;

    private static final int SEARCH_ADDRESS_REQUEST_CODE = 101;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 105;
    private static final int LOCATION_PERMISSION_MOVE_REQUEST_CODE = 106;
    private static final int ADDRESS_BY_LOCATION_CODE = 201;
    private static final int MY_ADDRESS_BY_LOCATION_CODE = 202;
    private static final int MY_ADDRESS_BY_LOCATION_UPDATE_CODE = 203;
    private static final int RESULT_OK = -1;
    private static final String CAR_ID = "_car_id";
    private static final int MARKER_ANIMATION_FULL_DURATION = 250;
    private static final int MARKER_ANIMATION_SHORT_DURATION = 150;

    private TextView carLocationAddress;
    private MapView mapView;
    private FloatingActionButton btnMyLocation;
    private HideAnimationDelegate hideDelegate;
    private GoogleApiClient apiClient;
    private GoogleMap map;
    private View locationMarker;
    private View targetMarker;
    private Point center;
    private float markerStartY;
    private float markerFinishY;
    private ObjectAnimator markerAnimator;
    private CarLocationPresenter presenter;
    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;
    private Address currentAddress;
    private String currentAddressString;
    private String myCurrentAddressString;

    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarFormsContract.Action action = (NewCarFormsContract.Action)
                    args.getSerializable(NewCarFormsContract.ACTION);
            if (action == null) {
                return;
            }
            pendingAction = action;
            switch (action) {
                case SAVE:
                case FINISH:
                    presenter.saveLocation(currentAddress);
                    break;
            }
        }
    };
    private AddressResultReceiver addressReceiver;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static Fragment newInstance(Integer carId) {
        Bundle args = new Bundle();
        if (carId != null) {
            args.putInt(CAR_ID, carId);
        }
        CarLocationFragment fragment = new CarLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        Bundle args = getArguments();
        Integer carId = args.containsKey(CAR_ID) ? args.getInt(CAR_ID) : null;
        presenter = new CarLocationPresenter(this, carId);
        addressReceiver = new AddressResultReceiver(handler);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_location, container, false);
        parentListener.showProgress(true);
        carLocationAddress = rootView.findViewById(R.id.car_location_address);
        locationMarker = rootView.findViewById(R.id.location_marker);
        targetMarker = rootView.findViewById(R.id.location_marker_target);
        carLocationAddress.setOnClickListener(this);
        hideDelegate = new HideAnimationDelegate(rootView.findViewById(R.id.address_view));
        btnMyLocation = rootView.findViewById(R.id.btn_my_location);
        btnMyLocation.setOnClickListener(this);
        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        initAnimationValues(rootView);
        return rootView;
    }

    private void initAnimationValues(View rootView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int top = targetMarker.getTop();
                        int bottom = targetMarker.getBottom();
                        int left = targetMarker.getLeft();
                        int right = targetMarker.getRight();
                        center = new Point(left + (right - left) / 2, top + (bottom - top) / 2);
                        markerStartY = locationMarker.getY();
                        markerFinishY = markerStartY - (float) locationMarker.getHeight() * 1.2f;
                        markerAnimator = ObjectAnimator.ofFloat(locationMarker, "y", markerStartY, markerFinishY);
                        markerAnimator.setInterpolator(new DecelerateInterpolator());
                    }
                }
        );
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        parentListener.showProgress(false);
        LatLng myCurrentLocation = obtainMyCurrentLocation();
        if (myCurrentLocation == null) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            requestAddressByLocation(myCurrentLocation, MY_ADDRESS_BY_LOCATION_CODE);
        }
        presenter.init();
        googleMap.setOnCameraIdleListener(() -> {
            if (center != null) {
                if (markerMoved()) {
                    markerMoveDown();
                }
                LatLng latLng = googleMap.getProjection().fromScreenLocation(center);
                requestAddressByLocation(latLng, ADDRESS_BY_LOCATION_CODE);
            }
        });
        googleMap.setOnCameraMoveStartedListener(i -> {
            if (markerAnimator != null) {
                markerMoveUp();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 1 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            LatLng myCurrentLocation = obtainMyCurrentLocation();
            if (myCurrentLocation == null) {
                return;
            }
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                requestAddressByLocation(myCurrentLocation, MY_ADDRESS_BY_LOCATION_CODE);
            } else if (requestCode == LOCATION_PERMISSION_MOVE_REQUEST_CODE) {
                updateCurrent(myCurrentLocation, MY_ADDRESS_BY_LOCATION_UPDATE_CODE);
            }
        }
    }

    private void updateCurrent(LatLng location, int addressRequestCode) {
        moveMapToLocation(location);
        requestAddressByLocation(location, addressRequestCode);
    }

    private boolean markerMoved() {
        float y = locationMarker.getY();
        return Math.abs(y - markerStartY) > 0;
    }

    private void markerMoveUp() {
        if (markerAnimator.isRunning()) {
            markerAnimator.cancel();
            float y = locationMarker.getY();
            markerAnimator.setFloatValues(y, markerFinishY);
            markerAnimator.setDuration(MARKER_ANIMATION_SHORT_DURATION);
        } else {
            markerAnimator.setFloatValues(markerStartY, markerFinishY);
            markerAnimator.setDuration(MARKER_ANIMATION_FULL_DURATION);
        }
        markerAnimator.start();
    }

    private void markerMoveDown() {
        if (markerAnimator.isRunning()) {
            markerAnimator.cancel();
            float y = locationMarker.getY();
            markerAnimator.setFloatValues(y, markerStartY);
            markerAnimator.setDuration(MARKER_ANIMATION_SHORT_DURATION);
        } else {
            markerAnimator.setFloatValues(markerFinishY, markerStartY);
            markerAnimator.setDuration(MARKER_ANIMATION_FULL_DURATION);
        }
        markerAnimator.start();
    }

    private void moveToMyCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (apiClient.isConnected()) {
                Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                updateCurrent(latLng, MY_ADDRESS_BY_LOCATION_UPDATE_CODE);
            }
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_MOVE_REQUEST_CODE);
        }
    }

    private LatLng obtainMyCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (apiClient.isConnected()) {
                Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
        return null;
    }

    private void moveMapToLocation(final LatLng location) {
        if (map == null) {
            Log.e(TAG, "GoogleMap is not instantiated");
            return;
        }
        CameraPosition position = new CameraPosition.Builder()
                .target(location)
                .zoom(15)
                .build();
        CameraUpdate focus = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(focus);
    }

    private void requestAddressByLocation(LatLng location, int requestCode) {
        Intent requestLocationIntent = new Intent(getActivity(), FetchAddressService.class);
        requestLocationIntent.putExtra(FetchAddressService.LOCATION, location);
        requestLocationIntent.putExtra(FetchAddressService.RECEIVER, addressReceiver);
        requestLocationIntent.putExtra(FetchAddressService.REQUEST_CODE, requestCode);
        getActivity().startService(requestLocationIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onBind(binder);
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.LOCATION);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        apiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        apiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        parentListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_location:
                moveToMyCurrentLocation();
                break;
            case R.id.car_location_address:
                if (hideDelegate.isAnimating()) {
                    return;
                }
                try {
                    Intent intent = new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
                    startActivityForResult(intent, SEARCH_ADDRESS_REQUEST_CODE);
                    if (!hideDelegate.isHidden()) {
                        hideDelegate.hide();
                    }
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_ADDRESS_REQUEST_CODE) {
            if (hideDelegate.isHidden()) {
                hideDelegate.show();
            }
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                updateCurrent(place.getLatLng(), ADDRESS_BY_LOCATION_CODE);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.e(TAG, status.getStatusMessage());
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        parentListener.showProgress(show);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void setCarData(CarData carData) {
        LatLng location = new LatLng(carData.getLatitude(), carData.getLongitude());
        updateCurrent(location, ADDRESS_BY_LOCATION_CODE);
    }

    private void markAsMyCurrentLocation(boolean myCurrentLocation) {
        btnMyLocation.setImageResource(myCurrentLocation ? myCurrentLocationIcon : anyOtherLocationIcon);
    }

    private boolean isMyCurrentLocation() {
        return currentAddressString != null &&
                myCurrentAddressString != null &&
                currentAddressString.equalsIgnoreCase(myCurrentAddressString);
    }

    @Override
    public void onFinish() {
        if (parentListener != null) {
            parentListener.onFinish(NewCarFormsContract.Mode.LOCATION, pendingAction);
        }
    }

    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == FetchAddressService.CODE_SUCCESS) {
                Address address = resultData.getParcelable(FetchAddressService.ADDRESS);
                int requestCode = resultData.getInt(FetchAddressService.REQUEST_CODE);
                currentAddress = address;
                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressBuilder.append(address.getAddressLine(i));
                    if (i != address.getMaxAddressLineIndex()) {
                        addressBuilder.append(" ");
                    }
                }
                String addressString = addressBuilder.toString();
                switch (requestCode) {
                    case MY_ADDRESS_BY_LOCATION_UPDATE_CODE:
                        myCurrentAddressString = addressString;
                    case ADDRESS_BY_LOCATION_CODE:
                        carLocationAddress.setText(addressString);
                        currentAddressString = addressString;
                        break;
                    case MY_ADDRESS_BY_LOCATION_CODE:
                        myCurrentAddressString = addressString;
                        break;
                }
            }
            markAsMyCurrentLocation(isMyCurrentLocation());
        }
    }
}
