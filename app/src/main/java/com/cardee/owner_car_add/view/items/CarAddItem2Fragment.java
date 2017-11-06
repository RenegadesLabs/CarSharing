package com.cardee.owner_car_add.view.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarAddItem2Fragment extends CarAddItemFragment implements CarAddActivity.CarAddActionListener {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_item2, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        ((CarAddActivity) getActivity()).setActionListener(this);
        return v;
    }

    @Override
    public void onSaveClicked() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
