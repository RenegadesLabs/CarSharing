package com.cardee.domain.renter.entity.mapper;

import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.data_source.remote.api.offers.response.OfferResponseBody;
import com.cardee.data_source.remote.api.offers.response.entity.OfferCarDetailsEntity;
import com.cardee.data_source.remote.api.offers.response.entity.OfferOrderDetails;
import com.cardee.data_source.remote.api.offers.response.entity.OfferOwnerEntity;
import com.cardee.domain.owner.entity.Image;
import com.cardee.domain.renter.entity.OfferCar;

import java.util.ArrayList;
import java.util.List;

public class OfferResponseBodyToOfferMapper {

    public static OfferCar transform(OfferResponseBody response) {

        OfferCarDetailsEntity carDetails = response.getCarDetailEntity();
        int carId = carDetails.getCarId();
        boolean favorite = carDetails.getFavorite();
        int seatCapacity = carDetails.getSeatingCapacity();
        ImageEntity[] responseImages = carDetails.getImages();
        Image[] images = new Image[responseImages.length];
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(responseImages[i].getImageId(),
                    responseImages[i].getThumbnail(),
                    responseImages[i].getLink(),
                    responseImages[i].isPrimary());
        }
        String licenseNumber = carDetails.getLicencePlateNumber();
        String year = carDetails.getManufactureYear();
        String bodyType = carDetails.getBodyType();
        String vehicleType = carDetails.getVehicleType();
        String title = carDetails.getTitle();
        double longitude = carDetails.getLongitude();
        double latitude = carDetails.getLatitude();
        int distance = carDetails.getDistance();
        OfferOwnerEntity owner = response.getOwnerEntity();
        int ownerId = owner.getId();
        String ownerPicture = owner.getPhotoUrl();
        String ownerName = owner.getName();
        int ratingCount = response.getRatingCount();
        float rating = response.getRating();
        OfferOrderDetails order = response.getOrderDetails();
        boolean instant = order.isInstantBooking();
        boolean curbside = order.isCurbsideDelivery();
        boolean cash = order.isAcceptCash();
        String fuelPolicy = order.getFuelPolicy().getFuelPolicyName();
        float rateFirst = order.getRateFirst();
        float rateSecond = order.getRateSecond();
        float discountFirst = order.getDiscountFirst();
        float discountSecond = order.getDiscountSecond();
        int minRental = order.getMinRentalDuration();
        String pickupTime = order.getPickupTime();
        String returnTime = order.getReturnTime();

        return new OfferCar.Builder()
                .setCarId(carId)
                .setFavorite(favorite)
                .setSeatCapacity(seatCapacity)
                .setImages(images)
                .setLicensePlateNumber(licenseNumber)
                .setYearOfManufacture(year)
                .setBodyType(bodyType)
                .setVehicleType(vehicleType)
                .setTitle(title)
                .setLongitude(longitude)
                .setLatitude(latitude)
                .setDistance(distance)
                .setOwnerId(ownerId)
                .setOwnerPicture(ownerPicture)
                .setOwnerName(ownerName)
                .setRatingCount(ratingCount)
                .setRating(rating)
                .setInstantBooking(instant)
                .setCurbsideDelivery(curbside)
                .setAcceptCash(cash)
                .setFuelPolicyName(fuelPolicy)
                .setRateFirst(rateFirst)
                .setRateSecond(rateSecond)
                .setDiscountFirst(discountFirst)
                .setDiscountSecond(discountSecond)
                .setMinRentalDuration(minRental)
                .setPickupTime(pickupTime)
                .setReturnTime(returnTime)
                .build();
    }

    public static List<OfferCar> transform(OfferResponseBody[] responseBodies) {
        if (responseBodies != null) {
            List<OfferCar> offers = new ArrayList<>();
            for (OfferResponseBody offerResponse : responseBodies) {
                offers.add(transform(offerResponse));
            }
            return offers;
        }
        return null;
    }
}
