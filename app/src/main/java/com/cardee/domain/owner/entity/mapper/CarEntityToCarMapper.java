package com.cardee.domain.owner.entity.mapper;


import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class CarEntityToCarMapper {

    public CarEntityToCarMapper() {

    }

    public Car transform(CarEntity carEntity) {
        BaseCarEntity carDetails = carEntity.getCarDetails();
        ImageEntity[] imageEntities = carDetails.getImages();
        Image[] images = new Image[imageEntities.length];
        String primaryImageLink = null;
        for (int i = 0; i < images.length; i++) {
            ImageEntity imageEntity = imageEntities[i];
            images[i] = new Image(
                    imageEntity.getImageId(),
                    imageEntity.getLink(),
                    imageEntity.getThumbnail(),
                    imageEntity.isPrimary());
            if (imageEntity.isPrimary()) {
                primaryImageLink = imageEntity.getLink();
            }
        }
        return new Car(carDetails.getCarId(), carDetails.getMake(), carDetails.getTitle(),
                carDetails.getModel(), carDetails.getVehicleTypeId(), carDetails.getVehicleType(),
                carDetails.getManufactureYear(), carDetails.getLicencePlateNumber(), carDetails.getBodyType(),
                carDetails.getEngineCapacity(), carDetails.getCarTransmission(), carDetails.getSeatingCapacity(),
                primaryImageLink, carEntity.getCarAvailabilityHourlyCount(), carEntity.getCarAvailabilityDailyCount(),
                carEntity.getCarAvailabilityTimeBegin(), carEntity.getCarAvailabilityTimeEnd(),
                carEntity.getCarAvailableOrderHours(), carEntity.getCarAvailableOrderDays(),
                carEntity.getCarAvailabilityHourlyDates(), carEntity.getCarAvailabilityDailyDates(),
                carDetails.getDescription(), images, null, null, null, null);
    }

    public List<Car> transform(CarEntity[] carEntities) {
        if (carEntities != null) {
            List<Car> cars = new ArrayList<>(carEntities.length);
            for (CarEntity carEntity : carEntities) {
                cars.add(transform(carEntity));
            }
            return cars;
        }
        return null;
    }
}
