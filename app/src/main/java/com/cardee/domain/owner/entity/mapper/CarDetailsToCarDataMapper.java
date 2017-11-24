package com.cardee.domain.owner.entity.mapper;


import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.domain.owner.entity.CarData;

public class CarDetailsToCarDataMapper {

    public CarDetailsToCarDataMapper() {

    }

    public CarData transform(CarDetailsEntity carDetails) {
        CarData.Builder builder = new CarData.Builder();
        builder.setAddress(carDetails.getAddress());
        builder.setLatitude(carDetails.getLatitude());
        builder.setLongitude(carDetails.getLongitude());
        builder.setTown(carDetails.getTown());
        return builder.build();
    }
}
