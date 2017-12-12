package com.cardee.domain.owner.entity.mapper;

import com.cardee.data_source.remote.api.cars.request.DailyRentalDetails;
import com.cardee.domain.owner.entity.RentalDetails;

public class RentalDetailsToRequestMapper {

    public RentalDetailsToRequestMapper() {

    }

    public DailyRentalDetails transform(RentalDetails rentalDetails) {
        DailyRentalDetails request = new DailyRentalDetails();
        request.setInstantBooking(rentalDetails.isDailyInstantBooking());
        request.setCurbsideDelivery(rentalDetails.isDailyCurbsideDelivery());
        request.setAcceptCash(rentalDetails.isDailyAcceptCash());
        request.setFuelPolicyId(rentalDetails.getDailyFuelPolicyId());
        request.setRentalRateFirst(rentalDetails.getDailyAmountRateFirst());
        request.setRentalRateSecond(rentalDetails.getDailyAmountRateSecond());
        request.setAmountDiscountFirst(rentalDetails.getDailyAmountDiscountFirst());
        request.setAmountDiscountSecond(rentalDetails.getDailyAmountDiscountSecond());
        request.setMinRentalDuration(rentalDetails.getDailyMinRentalDuration());
        request.setPickupTime(rentalDetails.getDailyTimePickup());
        request.setReturnTime(rentalDetails.getDailyTimeReturn());
        request.setAmountPayMileage(rentalDetails.getHourlyAmountPayMileage());
        return request;
    }
}
