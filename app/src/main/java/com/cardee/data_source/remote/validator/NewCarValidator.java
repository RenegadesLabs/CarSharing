package com.cardee.data_source.remote.validator;


import com.cardee.data_source.remote.api.cars.request.NewCarData;

public class NewCarValidator {

    private static final String TAG = NewCarValidator.class.getSimpleName();

    public NewCarValidator() {

    }

    public boolean isValid(NewCarData carData) {
        return carData != null &&
                carData.getVehicleType() != null &&
                carData.getMake() != null &&
                carData.getModel() != null &&
                carData.getManufactureYear() != null &&
                carData.getTitle() != null &&
                carData.getLicencePlateNumber() != null &&
                carData.getSeatingCapacity() != null &&
                carData.getEngineCapacity() != null &&
                carData.getTransmissionId() != null &&
                carData.getBodyType() != null &&
                carData.getLatitude() != null &&
                carData.getLongitude() != null &&
                carData.getTown() != null &&
                carData.getAddress() != null &&
                carData.getHideExactLocation() != null &&
                carData.getContactName() != null &&
                carData.getContactPhone() != null &&
                carData.getContactEmail() != null;
    }
}
