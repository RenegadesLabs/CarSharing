package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;

public class OwnerCarRentalFragment extends Fragment {

    private static final String CAR_ID = "car_id";

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
        return rootView;
    }
}
