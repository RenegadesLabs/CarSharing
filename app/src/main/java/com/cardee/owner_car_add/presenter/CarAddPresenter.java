package com.cardee.owner_car_add.presenter;


import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.validator.NewCarDataValidator;
import com.cardee.owner_car_add.view.CarAddContract;

public class CarAddPresenter extends NewCarPresenter {

    private CarAddContract.View view;
    private CarData carData;

    public CarAddPresenter(CarAddContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onCarDataResponse(CarData carData) {
        if (view == null || carData == null) {
            return;
        }
        this.carData = carData;
        NewCarDataValidator validator = new NewCarDataValidator();
        view.setTypeCompleted(validator.isTypeValid(carData));
        view.setInfoCompleted(validator.isInfoValid(carData));
        view.setImageCompleted(validator.isImageValid(carData));
        view.setLocationCompleted(validator.isLocationValid(carData));
        view.setContactsCompleted(validator.isContactsValid(carData));
        view.setPaymentCompleted(validator.isPaymentValid(carData));
    }

    public void createCar() {
        NewCarDataValidator validator = new NewCarDataValidator();
        if (carData != null && validator.isCarDataValid(carData)) {
            super.saveCar(carData, true);
        } else {
            view.showMessage("Sorry, you haven't finished all the steps");
        }
    }

    @Override
    public void onSaved() {
        if (view != null) {
            view.showProgress(false);
            view.onFinish();
        }
    }
}
