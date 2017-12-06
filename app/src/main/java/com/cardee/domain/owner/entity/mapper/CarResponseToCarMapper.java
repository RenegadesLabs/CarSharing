package com.cardee.domain.owner.entity.mapper;


import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.Image;

public class CarResponseToCarMapper {

    public CarResponseToCarMapper() {

    }

    public Car transform(CarResponseBody carResponse) {
        CarDetailsEntity carDetails = carResponse.getCarDetails();
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
                primaryImageLink, carResponse.getCarAvailabilityHourlyCount(), carResponse.getCarAvailabilityDailyCount(),
                carResponse.getCarAvailabilityTimeBegin(), carResponse.getCarAvailabilityTimeEnd(),
                carResponse.getCarAvailableOrderHours(), carResponse.getCarAvailableOrderDays(),
                carResponse.getCarAvailabilityHourlyDates(), carResponse.getCarAvailabilityDailyDates(),
                carResponse.getCarDetails().getDescription(), images, carDetails.getLongitude(),
                carDetails.getLatitude(), carDetails.getAddress(), carDetails.getTown(), carDetails.getCarRating());
    }
}
