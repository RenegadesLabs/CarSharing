package com.cardee.domain.owner.entity.mapper;


import android.support.annotation.NonNull;

import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.cars.response.entity.OrderDailyDetailsEntity;
import com.cardee.data_source.remote.api.cars.response.entity.OrderHourlyDetailsEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.domain.owner.entity.RentalDetails;

public class CarResponseToRentalDetailsMapper {

    public CarResponseToRentalDetailsMapper() {

    }

    public RentalDetails transform(@NonNull CarResponseBody response) {
        Integer carId = response.getCarDetails().getCarId();
        RentalDetails rentalDetails = new RentalDetails(carId);
        rentalDetails.setDeliveryRates(response.getCarDetails().getDeliveryRates());
        rentalDetails.setCarDetails(response.getCarDetails());

        rentalDetails.setAvailableDaily(response.getCarAvailableOrderDays());
        rentalDetails.setDailyCount(response.getCarAvailabilityDailyCount() == null
                ? 0 : response.getCarAvailabilityDailyCount());
        rentalDetails.setDailyDates(response.getCarAvailabilityDailyDates());
        OrderDailyDetailsEntity dailyDetails = response.getOrderDailyDetails();
        if (dailyDetails != null) {
            rentalDetails.setDailyTimePickup(dailyDetails.getTimePickup());
            rentalDetails.setDailyTimeReturn(dailyDetails.getTimeReturn());
            rentalDetails.setDailyInstantBooking(dailyDetails.getInstantBooking());
            rentalDetails.setDailyCurbsideDelivery(dailyDetails.getCurbsideDelivery());
            rentalDetails.setDailyAcceptCash(dailyDetails.getAcceptCash());
            rentalDetails.setDailyAmountRateFirst(dailyDetails.getAmntRateFirst());
            rentalDetails.setDailyAmountRateSecond(dailyDetails.getAmntRateSecond());
            rentalDetails.setDailyAmountDiscountFirst(dailyDetails.getAmntDiscountFirst());
            rentalDetails.setDailyAmountDiscountSecond(dailyDetails.getAmntDiscountSecond());
            rentalDetails.setDailyMinRentalDuration(dailyDetails.getMinRentalDuration());
            FuelPolicyEntity dailyFuelPolicy = dailyDetails.getFuelPolicy();
            if (dailyFuelPolicy != null) {
                rentalDetails.setDailyFuelPolicyId(dailyFuelPolicy.getFuelPolicyId());
                rentalDetails.setDailyFuelPolicyName(dailyFuelPolicy.getFuelPolicyName());
            }
        }
        rentalDetails.setAvailableHourly(response.getCarAvailableOrderHours());
        rentalDetails.setHourlyCount(response.getCarAvailabilityHourlyCount() == null
                ? 0 : response.getCarAvailabilityHourlyCount());
        rentalDetails.setHourlyDates(response.getCarAvailabilityHourlyDates());
        rentalDetails.setHourlyBeginTime(response.getCarAvailabilityTimeBegin());
        rentalDetails.setHourlyEndTime(response.getCarAvailabilityTimeEnd());
        OrderHourlyDetailsEntity hourlyDetails = response.getOrderHourlyDetails();
        if (hourlyDetails != null) {
            rentalDetails.setHourlyInstantBooking(hourlyDetails.getInstantBooking());
            rentalDetails.setHourlyCurbsideDelivery(hourlyDetails.getCurbsideDelivery());
            rentalDetails.setHourlyAcceptCash(hourlyDetails.getAcceptCrash());
            rentalDetails.setHourlyAmountRateFirst(hourlyDetails.getAmntRateFirst());
            rentalDetails.setHourlyAmountRateSecond(hourlyDetails.getAmntRateSecond());
            rentalDetails.setHourlyAmountPayMileage(hourlyDetails.getAmntPayMileage());
            rentalDetails.setHourlyAmountDiscountFirst(hourlyDetails.getAmntDiscountFirst());
            rentalDetails.setHourlyAmountDiscountSecond(hourlyDetails.getAmntDiscountSecond());
            rentalDetails.setHourlyMinRentalDuration(hourlyDetails.getMinRentalDuration());
            FuelPolicyEntity hourlyFuelPolicy = hourlyDetails.getFuelPolicy();
            if (hourlyFuelPolicy != null) {
                rentalDetails.setHourlyFuelPolicyId(hourlyFuelPolicy.getFuelPolicyId());
                rentalDetails.setHourlyFuelPolicyName(hourlyFuelPolicy.getFuelPolicyName());
            }
        }
        return rentalDetails;
    }
}
