package com.cardee.owner_car_add.presenter;

import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.view.NewCarContract;

public class CarContactsPresenter extends NewCarPresenter {

    private NewCarContract.View view;
    private CarData carData;

    public CarContactsPresenter(NewCarContract.View view) {
        super(view);
        this.view = view;
    }

    public void saveContactInfo(String name, String phone, String email) {
        if (carData != null) {
            carData = carData.newBuilder()
                    .setContactName(name)
                    .setContactPhone(phone)
                    .setContactEmail(email)
                    .build();
            super.saveCar(carData, false);
        }
    }

    @Override
    public void onCarDataResponse(CarData carData) {
        super.onCarDataResponse(carData);
        this.carData = carData;
    }

    @Override
    public void onSaved() {
        if (view != null) {
            view.onFinish();
        }
    }
}
