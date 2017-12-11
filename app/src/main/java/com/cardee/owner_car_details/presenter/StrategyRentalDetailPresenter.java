package com.cardee.owner_car_details.presenter;

import android.widget.CompoundButton;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.owner_car_details.RentalDetailsContract;

public class StrategyRentalDetailPresenter
        implements RentalDetailsContract.Presenter, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = StrategyRentalDetailPresenter.class.getSimpleName();

    public enum Strategy {
        DAILY, HOURLY
    }

    private final Strategy strategy;
    private RentalDetailsContract.ControlView view;
    private RentalDetails rentalDetails;

    public StrategyRentalDetailPresenter(RentalDetailsContract.ControlView view, Strategy strategy) {
        this.strategy = strategy;
        this.view = view;
    }

    @Override
    public void init() {

    }

    public void onBind(RentalDetails rentalDetails) {
        this.rentalDetails = rentalDetails;
        if (view != null) {
            view.setData(rentalDetails);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_rentalInstant:
            case R.id.sw_rentalDelivery:
            case R.id.sw_rentalCash:
                if (view != null) {
                    view.showMessage("Checked: " + b);
                }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
