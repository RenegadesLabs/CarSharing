package com.cardee.renter_home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cardee.R;
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpActivity;


public class RenterBookingsFragment extends Fragment {

    private Button showButton;

    public RenterBookingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RenterBookingsFragment newInstance() {
        RenterBookingsFragment fragment = new RenterBookingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renter_bookings, container, false);
        showButton = root.findViewById(R.id.button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RateRentalExpActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
