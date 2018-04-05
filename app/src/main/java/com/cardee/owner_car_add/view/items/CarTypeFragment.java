package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.mvp.BaseView;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.presenter.CarTypePresenter;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

public class CarTypeFragment extends Fragment implements NewCarFormsContract.View {

    private static final String TAG = CarTypeFragment.class.getSimpleName();

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
    @BindView(R.id.fl_vehiclePersonal)
    public View personalBlock;
    @BindView(R.id.fl_vehiclePrivate)
    public View privateBlock;
    @BindView(R.id.fl_vehicleCommercial)
    public View commercialBlock;
    private NewCarFormsContract.Action pendingAction;
    private Integer value;

    private DetailsChangedListener parentListener;
    private CarTypePresenter presenter;
    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarFormsContract.Action action = (NewCarFormsContract.Action)
                    args.getSerializable(NewCarFormsContract.ACTION);
            if (action == null) {
                return;
            }
            pendingAction = action;
            switch (action) {
                case SAVE:
                case FINISH:
                    onSave();
                    break;
            }
        }
    };

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
        presenter = new CarTypePresenter(this);
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
        showMessage("Coming soon");
//        view.requestFocus();
    }

    @OnFocusChange(R.id.fl_vehiclePersonal)
    public void onPersonalFocused(boolean isFocused) {
        if (isFocused) {
            value = 1;
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
            value = 2;
            vehiclePrivateIV.setImageResource(R.drawable.ic_grab_uber);
            vehiclePrivateTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            return;
        }
        vehiclePrivateIV.setImageResource(R.drawable.ic_grab);
        vehiclePrivateTV.setTextColor(getResources().getColor(R.color.add_car_item_vehicle_inactive));
    }

    @OnFocusChange(R.id.fl_vehicleCommercial)
    public void onCommercialFocused(boolean isFocused) {
        if (isFocused) {
            showMessage("Coming soon");
//            value = 3;
//            vehicleCommercialIV.setImageResource(R.drawable.ic_truck_active);
//            vehicleCommercialTV.setTextColor(getResources().getColor(R.color.colorPrimary));
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

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.TYPE);
        parentListener.onBind(binder);
        presenter.init();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        ((BaseView) getActivity()).showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        ((BaseView) getActivity()).showMessage(messageId);
    }

    public void onSave() {
        presenter.saveVehicleType(value);
    }

    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarFormsContract.Mode.TYPE, pendingAction);
    }

    @Override
    public void onNoSavedCar() {

    }

    @Override
    public void setCarData(CarData carData) {
        if (carData != null && carData.getVehicleType() != null) {
            switch (carData.getVehicleType()) {
                case 1:
                    personalBlock.requestFocus();
                    break;
                case 2:
                    privateBlock.requestFocus();
                    break;
                case 3:
                    commercialBlock.requestFocus();
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parentListener = null;
        presenter.onDestroy();
        presenter = null;
    }
}
