package com.cardee.renter_bookings.rate_rental_exp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;

public class FirstRateFragment extends Fragment {

    public FirstRateFragment() {
    }

    public static FirstRateFragment newInstance() {
        FirstRateFragment fragment = new FirstRateFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renter_rate, container, false);


        return root;
    }
}
