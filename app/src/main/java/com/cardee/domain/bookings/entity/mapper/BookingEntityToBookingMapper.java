package com.cardee.domain.bookings.entity.mapper;


import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.data_source.remote.api.booking.response.entity.BookingRentalTerms;
import com.cardee.data_source.remote.api.booking.response.entity.OwnerRateEntity;
import com.cardee.data_source.remote.api.booking.response.entity.RenterRateEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.data_source.remote.api.reviews.response.entity.Renter;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.PaymentType;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.Rate;
import com.cardee.domain.owner.entity.Image;
import com.cardee.util.DateStringDelegate;

import java.util.List;

public class BookingEntityToBookingMapper {

    private DateStringDelegate delegate;

    public BookingEntityToBookingMapper() {
        delegate = new DateStringDelegate(CardeeApp.context);
    }

    public Booking transform(BookingEntity entity, BookingRentalTerms rentalTerms) {
        ImageEntity[] imageEntities = entity.getCar().getImages();
        Image[] images = new Image[imageEntities.length];
        Image primary = null;
        for (int i = 0; i < images.length; i++) {
            ImageEntity target = imageEntities[i];
            images[i] = new Image(target.getImageId(), target.getLink(), target.getThumbnail(), target.isPrimary());
            if (primary == null && images[i].isPrimary()) {
                primary = images[i];
            }
        }
        if (primary == null) {
            primary = new Image(0, "", "", true);
        }
        String beginTime = delegate.formatShortBookingDate(entity.getTimeBegin());
        String endTime = delegate.formatShortBookingDate(entity.getTimeEnd());
        String createTime = delegate.formatCreationDate(entity.getDateCreated());

        Integer renterId = null;
        String renterName = null;
        String renterPhoto = null;
        Renter renter = entity.getRenter();
        if (renter != null) {
            renterName = renter.getName();
            renterPhoto = renter.getProfilePhoto();
            renterId = renter.getProfileId();
        }
        Rate[] renterRates = null;
        List<RenterRateEntity> renterRateList = entity.getRenterRate();
        if (renterRateList != null && !renterRateList.isEmpty()) {
            renterRates = new Rate[renterRateList.size()];
            for (int i = 0; i < renterRates.length; i++) {
                RenterRateEntity rateEntity = renterRateList.get(i);
                renterRates[i] = new Rate(rateEntity.getRating(),
                        rateEntity.getRentalExperienceName(), rateEntity.getRentalExperienceId());
            }
        }
        Integer ownerId = null;
        String ownerName = null;
        String ownerPhoto = null;
        OwnerProfile owner = entity.getOwner();
        if (owner != null) {
            ownerId = owner.getProfileId();
            ownerName = owner.getName();
            ownerPhoto = owner.getProfilePhotoLink();
        }
        Rate[] ownerRates = null;
        List<OwnerRateEntity> ownerRateList = entity.getOwnerRate();
        if (ownerRateList != null && !ownerRateList.isEmpty()) {
            ownerRates = new Rate[ownerRateList.size()];
            for (int i = 0; i < ownerRates.length; i++) {
                OwnerRateEntity rateEntity = ownerRateList.get(i);
                ownerRates[i] = new Rate(rateEntity.getRating(),
                        rateEntity.getRentalExperienceName(), rateEntity.getRentalExperienceId());
            }
        }
        Integer fuelPolicyId = null;
        String fuelPolicyName = null;
        FuelPolicyEntity fuelPolicy = entity.getFuelPolicyEntity();
        if (fuelPolicy != null) {
            fuelPolicyId = fuelPolicy.getFuelPolicyId();
            fuelPolicyName = fuelPolicy.getFuelPolicyName();
        }
        PaymentType paymentType = null;
        if (rentalTerms != null) {
            Boolean acceptCash = rentalTerms.getAcceptCash();
            if (acceptCash != null && acceptCash) {
                paymentType = PaymentType.CASH;
            } else {
                paymentType = PaymentType.CARD;
            }
        }
        boolean bookingByDays = entity.getBookingByDay() == null ? false : entity.getBookingByDay();
        return new Booking.Builder()
                .setTotalAmount(entity.getTotalAmount())
                .setTimeBegin(beginTime)
                .setTimeEnd(endTime)
                .setBookingStateType(BookingState.valueOf(entity.getBookingStateType()))
                .setBookingStateName(entity.getBookingStateName())
                .setBookingId(entity.getBookingId())
                .setDateCreated(createTime)
                .setCarId(entity.getCar().getCarId())
                .setCarTitle(entity.getCar().getCarTitle())
                .setManufactureYear(entity.getCar().getManufactureYear())
                .setPlateNumber(entity.getCar().getPlateNumber())
                .setImages(images)
                .setPrimaryImage(primary)
                .setRenterName(renterName)
                .setRenterPhoto(renterPhoto)
                .setNote(entity.getNote())
                .setDeliveryAddress(entity.getAddressDelivery())
                .setReviewFromOwner(entity.getReviewFromOwner())
                .setReviewFromRenter(entity.getReviewFromRenter())
                .setBookingNum(entity.getBookingNum())
                .setRenterId(renterId)
                .setRenterName(renterName)
                .setRenterPhoto(renterPhoto)
                .setRenterRates(renterRates)
                .setOwnerId(ownerId)
                .setOwnerName(ownerName)
                .setOwnerPhoto(ownerPhoto)
                .setOwnerRates(ownerRates)
                .setTankPart(entity.getTankPart())
                .setTankPartRentingOut(entity.getTankPartRentingOut())
                .setFuelPolicyId(fuelPolicyId)
                .setFuelPolicyName(fuelPolicyName)
                .setPaymentType(paymentType)
                .setCost(entity.getCost())
                .setBookingByDays(bookingByDays)
                .build();
    }
}
