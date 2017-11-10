package com.cardee.owner_car_add.validator;

import android.support.annotation.NonNull;

import com.cardee.domain.owner.entity.NewCar;

public class NewCarDataValidator {

    public NewCarDataValidator() {

    }

    public boolean isTypeValid(@NonNull NewCar carData) {
        return carData.getVehicleType() != null;
    }

    public boolean isInfoValid(@NonNull NewCar carData) {
        return carData.getMake() != null &&
                carData.getModel() != null &&
                carData.getManufactureYear() != null &&
                carData.getTitle() != null &&
                carData.getLicencePlateNumber() != null &&
                carData.getSeatingCapacity() != null &&
                carData.getEngineCapacity() != null &&
                carData.getTransmissionId() != null &&
                carData.getBodyType() != null;
    }

    public boolean isImageValid(@NonNull NewCar carData) {
        return carData.getImage() != null;
    }

    public boolean isLocationValid(@NonNull NewCar carData) {
        return carData.getLatitude() != null &&
                carData.getLongitude() != null &&
                carData.getTown() != null &&
                carData.getAddress() != null &&
                carData.getHideExactLocation() != null;
    }

    public boolean isContactsValid(@NonNull NewCar carData) {
        return carData.getContactName() != null &&
                carData.getContactPhone() != null &&
                carData.getContactEmail() != null;
    }

    public boolean isPaymentValid(@NonNull NewCar carData) {

        return false;
    }

    public boolean isCarDataValid(@NonNull NewCar carData) {
        return isTypeValid(carData) &&
                isInfoValid(carData) &&
                isImageValid(carData) &&
                isLocationValid(carData) &&
                isContactsValid(carData) &&
                isPaymentValid(carData);
    }
}
