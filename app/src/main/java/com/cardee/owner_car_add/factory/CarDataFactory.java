package com.cardee.owner_car_add.factory;


import android.location.Address;

import com.cardee.domain.owner.entity.CarData;

public class CarDataFactory {

    public CarDataFactory() {

    }

    public CarData from(CarData carData, Address address) {
        CarData.Builder builder;
        if (carData == null) {
            builder = new CarData.Builder();
        } else {
            builder = carData.newBuilder();
        }
        builder.setHideExactLocation(false);
        builder.setLatitude(address.getLatitude());
        builder.setLongitude(address.getLongitude());
        builder.setTown(address.getLocality());
        StringBuilder addressBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressBuilder.append(address.getAddressLine(i));
            if (i != address.getMaxAddressLineIndex()) {
                addressBuilder.append(" ");
            }
        }
        builder.setAddress(addressBuilder.toString());
        return builder.build();
    }
}
