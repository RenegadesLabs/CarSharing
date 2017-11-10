package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

public class CarTypeFragment extends CarAddBaseFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.iv_vehiclePersonal)
    public AppCompatImageView vehiclePersonalIV;

    @BindView(R.id.tv_vehiclePersonal)
    public TextView vehiclePersonalTV;

    @BindView(R.id.iv_vehiclePrivate)
    public AppCompatImageView vehiclePrivateIV;

    @BindView(R.id.tv_vehiclePrivate)
    public TextView vehiclePrivateTV;

    @BindView(R.id.iv_vehicleCommercial)
    public AppCompatImageView vehicleCommercialIV;

    @BindView(R.id.tv_vehicleCommercial)
    public TextView vehicleCommercialTV;

    private String mValue;

    private DetailsChangedListener parentListener;

    private CarAddActivity.CarInfoPassCallback mPassDataCallback;

    public static Fragment newInstance() {
        Fragment fragment = new CarTypeFragment();
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
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_vehicle_type, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.fl_vehiclePersonal)
    public void onPersonalClicked(View view) {
        view.requestFocus();
    }

    @OnClick(R.id.fl_vehiclePrivate)
    public void onPrivateClicked(View view) {
        view.requestFocus();
    }

    @OnClick(R.id.fl_vehicleCommercial)
    public void onCommercialClicked(View view) {
        view.requestFocus();
    }

    @OnFocusChange(R.id.fl_vehiclePersonal)
    public void onPersonalFocused(boolean isFocused) {
        if (isFocused) {
            mValue = getString(R.string.car_add_vehicle_personal);
            vehiclePersonalIV.setImageResource(R.drawable.ic_car_active);
            vehiclePersonalTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            return;
        }
        vehiclePersonalIV.setImageResource(R.drawable.ic_car_inactive);
        vehiclePersonalTV.setTextColor(getResources().getColor(R.color.add_car_item_vehicle_inactive));
    }

    @OnFocusChange(R.id.fl_vehiclePrivate)
    public void onPrivateFocused(boolean isFocused) {
        if (isFocused) {
            mValue = getString(R.string.car_add_vehicle_private);
            vehiclePrivateIV.setImageResource(R.drawable.ic_private_hire_active);
            vehiclePrivateTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            return;
        }
        vehiclePrivateIV.setImageResource(R.drawable.ic_private_hire_inactive);
        vehiclePrivateTV.setTextColor(getResources().getColor(R.color.add_car_item_vehicle_inactive));
    }

    @OnFocusChange(R.id.fl_vehicleCommercial)
    public void onCommercialFocused(boolean isFocused) {
        if (isFocused) {
            mValue = getString(R.string.car_add_vehicle_commercial);
            vehicleCommercialIV.setImageResource(R.drawable.ic_truck_active);
            vehicleCommercialTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            return;
        }
        vehicleCommercialIV.setImageResource(R.drawable.ic_truck_inactive);
        vehicleCommercialTV.setTextColor(getResources().getColor(R.color.add_car_item_vehicle_inactive));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    //    @Override
    public void onSaveClicked() {
        saveArguments(new Bundle(), false);
        getActivity().onBackPressed();
    }

    @Override
    void saveArguments(Bundle b, boolean onNext) {
        b.putInt(CarAddBaseFragment.FRAGMENT_NUMBER, 0);
        b.putString(CarAddBaseFragment.FRAGMENT_VALUE, mValue);
//        getArguments().putAll(b);
        if (mPassDataCallback == null)
            return;
        mPassDataCallback.onPassData(b);
    }

    @Override
    public void setPassDataCallback(CarAddActivity.CarInfoPassCallback callback) {
        mPassDataCallback = callback;
    }

    @Override
    public void setViewListener(CarAddView listener) {
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.TYPE);
    }
}
