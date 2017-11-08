package com.cardee.owner_car_add.view.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;

public class CarAddItem3Fragment extends CarAddItemFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_item3, container, false);

        return v;
    }

    @Override
    void saveArguments(Bundle b, boolean onNext) {

    }

    @Override
    public void setPassDataCallback(CarAddActivity.CarInfoPassCallback callback) {

    }

    @Override
    public void setViewListener(CarAddView listener) {

    }
}
