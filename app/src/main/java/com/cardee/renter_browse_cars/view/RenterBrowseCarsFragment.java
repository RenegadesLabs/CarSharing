package com.cardee.renter_browse_cars.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cardee.R;
import com.cardee.renter_browse_cars.filter.view.FilterActivity;


public class RenterBrowseCarsFragment extends Fragment {


    public RenterBrowseCarsFragment() {
    }

    public static RenterBrowseCarsFragment newInstance() {
        RenterBrowseCarsFragment fragment = new RenterBrowseCarsFragment();
        return fragment;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renter_cars, container, false);
        ImageView filter = root.findViewById(R.id.filterIcon);
        filter.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), FilterActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
