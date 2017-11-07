package com.cardee.owner_car_add.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;

public class CarLocationFragment extends Fragment {

    private static final String TAG = CarLocationFragment.class.getSimpleName();

    private static final String CAR_LAT = "_car_lat";
    private static final String CAR_LNG = "_car_lng";

    private float mLat;
    private float mLng;

    public static Fragment newInstance(float lat, float lng) {
        Bundle args = new Bundle();
        args.putFloat(CAR_LAT, lat);
        args.putFloat(CAR_LNG, lng);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mLat = args.getFloat(CAR_LAT);
            mLng = args.getFloat(CAR_LNG);
            return;
        }
        getCurrentLocationAsync();
    }

    public void getCurrentLocationAsync() {
        Log.e(TAG, "Getting current");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_location, container, false);

        return rootView;
    }
}
