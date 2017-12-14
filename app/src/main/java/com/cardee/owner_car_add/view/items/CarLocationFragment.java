package com.cardee.owner_car_add.view.items;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
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
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.presenter.CarLocationPresenter;
import com.cardee.owner_car_add.view.NewCarFormsContract;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class CarLocationFragment extends Fragment
        implements OnMapReadyCallback,
        NewCarFormsContract.View,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = CarLocationFragment.class.getSimpleName();

    private static final int SEARCH_ADDRESS_REQUEST_CODE = 101;
    private static final int RESULT_OK = -1;
    private static final String CAR_ID = "_car_id";

    private TextView carLocationAddress;
    private MapView mapView;
    private HideAnimationDelegate hideDelegate;
    private GoogleApiClient apiClient;
    private GoogleMap map;
    private Marker currentLocationMarker;
    private Address currentAddress;

    private CarLocationPresenter presenter;
    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;
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
                case UPDATE:
                    showCurrentLocationIfPermitted();
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
        carLocationAddress.setOnClickListener(this);
        hideDelegate = new HideAnimationDelegate(rootView.findViewById(R.id.address_view));
        FloatingActionButton btnMyLocation = rootView.findViewById(R.id.btn_my_location);
        btnMyLocation.setOnClickListener(this);
        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        parentListener.showProgress(false);
        presenter.init();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                fetchLocationAddress(latLng);
                moveMapToLocation(latLng);
            }
        });
    }

    public void showCurrentLocationIfPermitted() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            parentListener.onNeedPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        if (apiClient.isConnected()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            fetchLocationAddress(latLng);
            moveMapToLocation(latLng);
        }
    }

    private void moveMapToLocation(final LatLng location) {
        if (map == null) {
            Log.e(TAG, "GoogleMap is not instantiated");
            return;
        }
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap()))
                .position(location);
        currentLocationMarker = map.addMarker(markerOptions);
        CameraPosition position = new CameraPosition.Builder()
                .target(location)
                .zoom(15)
                .build();
        CameraUpdate focus = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(focus);
    }

    private Bitmap getMarkerBitmap() {
        int height = 108;
        int width = 108;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_car_marker);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private void fetchLocationAddress(LatLng location) {
        Intent fetchLocationIntent = new Intent(getActivity(), FetchAddressService.class);
        fetchLocationIntent.putExtra(FetchAddressService.LOCATION, location);
        fetchLocationIntent.putExtra(FetchAddressService.RECEIVER, addressReceiver);
        getActivity().startService(fetchLocationIntent);
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
                showCurrentLocationIfPermitted();
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
                renderLocation(place.getLatLng());
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
        renderLocation(location);
    }

    private void renderLocation(LatLng location) {
        moveMapToLocation(location);
        fetchLocationAddress(location);
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
                currentAddress = address;
                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressBuilder.append(address.getAddressLine(i));
                    if (i != address.getMaxAddressLineIndex()) {
                        addressBuilder.append(" ");
                    }
                }
                carLocationAddress.setText(addressBuilder.toString());
            }
        }
    }
}
