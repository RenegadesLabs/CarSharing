package com.cardee.owner_car_add.presenter;


import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.CarAddContract;
import com.cardee.owner_car_add.validator.NewCarDataValidator;

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

        if (isEmpty(carData)) {
            view.onNoSavedCar();
        }
    }

    private boolean isEmpty(CarData carData) {
        return carData.getVehicleType() == null &&
                carData.getInsuranceComprehensive() == null &&
                carData.getInsuranceUnnamedDriver() == null &&
                carData.getInsuranceMinAge() == null &&
                carData.getInsuranceMinYearsDrivingExperience() == null &&
                carData.getInsuranceExpiredDate() == null &&
                carData.getMake() == null &&
                carData.getModel() == null &&
                carData.getManufactureYear() == null &&
                carData.getTitle() == null &&
                carData.getLicencePlateNumber() == null &&
                carData.getSeatingCapacity() == null &&
                carData.getEngineCapacity() == null &&
                carData.getTransmissionId() == null &&
                carData.getBodyType() == null &&
                carData.getImage() == null &&
                carData.getLatitude() == null &&
                carData.getLongitude() == null &&
                carData.getTown() == null &&
                carData.getAddress() == null &&
                carData.getHideExactLocation() == null &&
                carData.getContactName() == null &&
                carData.getContactPhone() == null &&
                carData.getContactEmail() == null;
    }

    public void createCar() {
        NewCarDataValidator validator = new NewCarDataValidator();
        if (carData != null && validator.isCarDataValid(carData)) {
            view.showProgress(true);
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
