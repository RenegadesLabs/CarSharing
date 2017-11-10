package com.cardee.owner_car_add.presenter;


import com.cardee.domain.owner.entity.NewCar;
import com.cardee.owner_car_add.validator.NewCarDataValidator;
import com.cardee.owner_car_add.view.CarAddContract;

public class CarAddPresenter extends NewCarPresenter {

    private CarAddContract.View view;

    public CarAddPresenter(CarAddContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onCarDataResponse(NewCar carData) {
        if (view == null || carData == null) {
            return;
        }
        NewCarDataValidator validator = new NewCarDataValidator();
        view.setTypeCompleted(validator.isTypeValid(carData));
        view.setInfoCompleted(validator.isInfoValid(carData));
        view.setImageCompleted(validator.isImageValid(carData));
        view.setLocationCompleted(validator.isLocationValid(carData));
        view.setContactsCompleted(validator.isContactsValid(carData));
        view.setPaymentCompleted(validator.isPaymentValid(carData));
    }
}
