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
        Image primary = null;
        for (Image image : images) {
            if (image.isPrimary()) {
                primary = new Image(image.getImageId(),
                        image.getLink(), image.getThumbnail(),
                        image.isPrimary());
                break;
            }
        }
        String licenseNumber = carDetails.getLicencePlateNumber();
        String year = carDetails.getManufactureYear();
        String bodyType = carDetails.getBodyType();
        String vehicleType = carDetails.getVehicleType();
        String title = carDetails.getTitle();
        double longitude = carDetails.getLongitude() == null ? 0.0 : carDetails.getLongitude();
        double latitude = carDetails.getLatitude() == null ? 0.0 : carDetails.getLatitude();
        int distance = carDetails.getDistance() == null ? 0 : carDetails.getDistance();
        String address = carDetails.getAddress();
        String town = carDetails.getTown();
        OfferOwnerEntity owner = response.getOwnerEntity();
        int ownerId = owner.getId();
        String ownerPicture = owner.getPhotoUrl();
        String ownerName = owner.getName();
        int ratingCount = response.getRatingCount() == null ? 0 : response.getRatingCount();
        float rating = response.getRating() == null ? .0f : response.getRating();
        OfferOrderDetails order = response.getOrderDetails();

        boolean instant = false;
        boolean curbside = false;
        boolean cash = false;
        String fuelPolicy = null;
        float rateFirst = 0;
        float rateSecond = 0;
        float discountFirst = 0;
        float discountSecond = 0;
        int minRental = 0;
        String pickupTime = null;
        String returnTime = null;
        if (order != null) {

            instant = order.isInstantBooking() == null ? false : order.isInstantBooking();
            curbside = order.isCurbsideDelivery() == null ? false : order.isCurbsideDelivery();
            cash = order.isAcceptCash() == null ? false : order.isAcceptCash();
            fuelPolicy = order.getFuelPolicy().getFuelPolicyName();
            rateFirst = order.getRateFirst() == null ? .0f : order.getRateFirst();
            rateSecond = order.getRateSecond() == null ? .0f : order.getRateSecond();
            discountFirst = order.getDiscountFirst() == null ? .0f : order.getDiscountFirst();
            discountSecond = order.getDiscountSecond() == null ? .0f : order.getDiscountSecond();
            minRental = order.getMinRentalDuration() == null ? 0 : order.getMinRentalDuration();
            pickupTime = order.getPickupTime();
            returnTime = order.getReturnTime();
        }

        return new OfferCar.Builder()
                .setCarId(carId)
                .setFavorite(favorite)
                .setSeatCapacity(seatCapacity)
                .setImages(images)
                .setPrimaryCarImage(primary)
                .setLicensePlateNumber(licenseNumber)
                .setYearOfManufacture(year)
                .setBodyType(bodyType)
                .setVehicleType(vehicleType)
                .setTitle(title)
                .setLongitude(longitude)
                .setLatitude(latitude)
                .setDistance(distance)
                .setAddress(address)
                .setTown(town)
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
