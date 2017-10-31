package com.cardee.owner_car_details.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_details.OwnerCarDetailsContract;

public class OwnerCarDetailsFragment extends Fragment
        implements OwnerCarDetailsContract.View {

    private static final String CAR_ID = "car_id";

    public static Fragment newInstance(Integer carId) {
        OwnerCarDetailsFragment fragment = new OwnerCarDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_car_details, container, false);
        return rootView;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }
}
