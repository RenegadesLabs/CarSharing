package com.cardee.owner_car_details.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.presenter.OwnerCarDetailsPresenter;
import com.cardee.owner_car_details.view.viewholder.OwnerCarDetailViewHolder;

public class OwnerCarDetailsFragment extends Fragment
        implements OwnerCarDetailsContract.View {

    private static final String CAR_ID = "car_id";

    private OwnerCarDetailViewHolder mHolder;
    private OwnerCarDetailsPresenter mPresenter;

    public static Fragment newInstance(Integer carId) {
        OwnerCarDetailsFragment fragment = new OwnerCarDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(CAR_ID, carId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int carId = -1;
        Bundle arguments = getArguments();
        if (arguments != null) {
            carId = arguments.getInt(CAR_ID, -1);
        }
        mPresenter = new OwnerCarDetailsPresenter(this, carId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_car_details, container, false);
        mHolder = new OwnerCarDetailViewHolder(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setCar(Car car) {

    }

    @Override
    public void moveToImages(Bundle args) {

    }

    @Override
    public void moveToSpecs(Bundle args) {

    }

    @Override
    public void moveToLocation(Bundle args) {

    }

    @Override
    public void moveToDescription(Bundle args) {

    }
}
