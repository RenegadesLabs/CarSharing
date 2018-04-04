package com.cardee.domain.owner.entity.mapper;

import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.domain.owner.entity.CarData;

public class NewCarDataMapper {

    public NewCarDataMapper() {

    }

    public CarData reverse(NewCarData carData) {
        CarData.Builder builder = new CarData.Builder();
        if (carData != null) {
            builder.setVehicleType(carData.getVehicleType())
                    .setInsuranceUnnamedDriver(carData.getInsuranceUnnamedDriver())
                    .setInsuranceComprehensive(carData.getInsuranceComprehensive())
                    .setInsuranceMinYearsDrivingExperience(carData.getInsuranceMinYearsDrivingExperience())
                    .setInsuranceMinAge(carData.getInsuranceMinAge())
                    .setInsuranceExpiredDate(carData.getInsuranceExpiredDate())
                    .setMake(carData.getMake())
                    .setModel(carData.getModel())
                    .setManufactureYear(carData.getManufactureYear())
                    .setTitle(carData.getTitle())
                    .setLicencePlateNumber(carData.getLicencePlateNumber())
                    .setSeatingCapacity(carData.getSeatingCapacity())
                    .setEngineCapacity(carData.getEngineCapacity())
                    .setTransmissionId(carData.getTransmissionId())
                    .setBodyType(carData.getBodyType())
                    .setImage(carData.getImage())
                    .setLatitude(carData.getLatitude())
                    .setLongitude(carData.getLongitude())
                    .setAddress(carData.getAddress())
                    .setTown(carData.getTown())
                    .setHideExactLocation(carData.getHideExactLocation())
                    .setContactName(carData.getContactName())
                    .setContactPhone(carData.getContactPhone())
                    .setContactEmail(carData.getContactEmail());
        } else {
            return null;
        }
        return builder.build();
    }

    public NewCarData transform(CarData carData) {
        NewCarData newCarData = new NewCarData();
        newCarData.setVehicleType(carData.getVehicleType());
        newCarData.setInsuranceComprehensive(carData.getInsuranceComprehensive());
        newCarData.setInsuranceUnnamedDriver(carData.getInsuranceUnnamedDriver());
        newCarData.setInsuranceMinYearsDrivingExperience(carData.getInsuranceMinYearsDrivingExperience());
        newCarData.setInsuranceMinAge(carData.getInsuranceMinAge());
        newCarData.setInsuranceExpiredDate(carData.getInsuranceExpiredDate());
        newCarData.setMake(carData.getMake());
        newCarData.setModel(carData.getModel());
        newCarData.setManufactureYear(carData.getManufactureYear());
        newCarData.setTitle(carData.getTitle());
        newCarData.setLicencePlateNumber(carData.getLicencePlateNumber());
        newCarData.setSeatingCapacity(carData.getSeatingCapacity());
        newCarData.setEngineCapacity(carData.getEngineCapacity());
        newCarData.setTransmissionId(carData.getTransmissionId());
        newCarData.setBodyType(carData.getBodyType());
        newCarData.setImage(carData.getImage());
        newCarData.setLatitude(carData.getLatitude());
        newCarData.setLongitude(carData.getLongitude());
        newCarData.setAddress(carData.getAddress());
        newCarData.setTown(carData.getTown());
        newCarData.setHideExactLocation(carData.getHideExactLocation());
        newCarData.setContactName(carData.getContactName());
        newCarData.setContactPhone(carData.getContactPhone());
        newCarData.setContactEmail(carData.getContactEmail());
        return newCarData;
    }
}
