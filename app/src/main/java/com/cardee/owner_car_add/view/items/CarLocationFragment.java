package com.cardee.owner_car_add.view.items;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.service.FetchAddressService;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarLocationFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = CarLocationFragment.class.getSimpleName();

    private static final String CAR_LAT = "_car_lat";
    private static final String CAR_LNG = "_car_lng";

    private Double currentLat;
    private Double currentLng;
    private LatLng defaultLocation = new LatLng(1.323174, 103.890894);

    private TextView carLocationAddress;
    private MapView mapView;
    private FloatingActionButton btnMyLocation;
    private GoogleApiClient apiClient;
    private GoogleMap map;
    private Marker currentLocationMarker;

    private DetailsChangedListener parentListener;
    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            showCurrentLocationIfPermitted();
        }
    };
    private AddressResultReceiver addressReceiver;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static Fragment newInstance(double lat, double lng) {
        Bundle args = new Bundle();
        args.putDouble(CAR_LAT, lat);
        args.putDouble(CAR_LNG, lng);
        return newInstance(args);
    }

    public static Fragment newInstance() {
        return newInstance(null);
    }

    private static Fragment newInstance(Bundle args) {
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
        Bundle args = getArguments();
        apiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if (args != null) {
            currentLat = args.getDouble(CAR_LAT);
            currentLng = args.getDouble(CAR_LNG);
        }
        addressReceiver = new AddressResultReceiver(handler);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_location, container, false);
        parentListener.showProgress(true);
        carLocationAddress = rootView.findViewById(R.id.car_location_address);
        btnMyLocation = rootView.findViewById(R.id.btn_my_location);
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
        if (currentLat == null || currentLng == null) {
            moveMapToLocation(defaultLocation);
            fetchLocationAddress(defaultLocation);
        } else {
            LatLng location = new LatLng(currentLat, currentLng);
            moveMapToLocation(location);
            fetchLocationAddress(location);
        }
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
                .position(location);
        currentLocationMarker = map.addMarker(markerOptions);
        CameraPosition position = new CameraPosition.Builder()
                .target(location)
                .zoom(15)
                .build();
        CameraUpdate focus = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(focus);
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
        }
    }

    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String address = resultData.getString(FetchAddressService.ADDRESS, "");
            if (resultCode == FetchAddressService.CODE_SUCCESS) {
                carLocationAddress.setText(address);
            }
        }
    }
}
