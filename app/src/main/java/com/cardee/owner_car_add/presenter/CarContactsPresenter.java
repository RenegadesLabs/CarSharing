package com.cardee.owner_car_add.presenter;

import com.cardee.owner_car_add.view.NewCarFormsContract;

public class CarContactsPresenter extends NewCarPresenter {

    private NewCarFormsContract.View view;

    public CarContactsPresenter(NewCarFormsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onSaved() {
        if (view != null) {
            view.onFinish();
        }
    }
}
