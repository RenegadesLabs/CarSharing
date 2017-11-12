package com.cardee.owner_car_add.validator;

import android.support.annotation.NonNull;

import com.cardee.domain.owner.entity.CarData;

public class NewCarDataValidator {

    public NewCarDataValidator() {

    }

    public boolean isTypeValid(@NonNull CarData carData) {
        return carData.getVehicleType() != null;
    }

    public boolean isInfoValid(@NonNull CarData carData) {
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

    public boolean isImageValid(@NonNull CarData carData) {
        return carData.getImage() != null;
    }

    public boolean isLocationValid(@NonNull CarData carData) {
        return carData.getLatitude() != null &&
                carData.getLongitude() != null &&
                carData.getTown() != null &&
                carData.getAddress() != null &&
                carData.getHideExactLocation() != null;
    }

    public boolean isContactsValid(@NonNull CarData carData) {
        return carData.getContactName() != null &&
                carData.getContactPhone() != null &&
                carData.getContactEmail() != null;
    }

    public boolean isPaymentValid(@NonNull CarData carData) {

        return false;
    }

    public boolean isCarDataValid(@NonNull CarData carData) {
        return isTypeValid(carData) &&
                isInfoValid(carData) &&
                isImageValid(carData) &&
                isLocationValid(carData) &&
                isContactsValid(carData) &&
                isPaymentValid(carData);
    }
}
