package com.cardee.domain.owner.entity;

import android.support.annotation.NonNull;

import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;

public class RentalDetails {

    private final int carId;

    private boolean availableDaily;
    private int dailyCount;
    private String[] dailyDates;
    private String dailyTimePickup;
    private String dailyTimeReturn;
    private boolean dailyInstantBooking;
    private Integer dailyInstantBookingCount;
    private boolean dailyCurbsideDelivery;
    private boolean dailyAcceptCash;
    private Float dailyAmountRateFirst;
    private Float dailyAmountRateSecond;
    private Float dailyAmountDiscountFirst;
    private Float dailyAmountDiscountSecond;
    private Integer dailyMinRentalDuration;
    private Integer dailyFuelPolicyId;
    private String dailyFuelPolicyName;

    private boolean availableHourly;
    private int hourlyCount;
    private String[] hourlyDates;
    private String hourlyBeginTime;
    private String hourlyEndTime;
    private boolean hourlyInstantBooking;
    private Integer hourlyInstantBookingCount;
    private boolean hourlyCurbsideDelivery;
    private boolean hourlyAcceptCash;
    private Float hourlyAmountRateFirst;
    private Float hourlyAmountRateSecond;
    private Float hourlyAmountDiscountFirst;
    private Float hourlyAmountDiscountSecond;
    private Integer hourlyMinRentalDuration;
    private String hourlyAmountPayMileage;
    private Integer hourlyFuelPolicyId;
    private String hourlyFuelPolicyName;

    private DeliveryRatesEntity deliveryRates;

    private CarDetailsEntity carDetails;

    public RentalDetails(int carId) {
        this.carId = carId;
    }

    public Integer getDailyInstantBookingCount() {
        return dailyInstantBookingCount == null ? 1 : dailyInstantBookingCount;
    }

    public void setDailyInstantBookingCount(Integer dailyInstantBookingCount) {
        this.dailyInstantBookingCount = dailyInstantBookingCount;
    }

    public Integer getHourlyInstantBookingCount() {
        return hourlyInstantBookingCount == null ? 2 : hourlyInstantBookingCount;
    }

    public void setHourlyInstantBookingCount(Integer hourlyInstantBookingCount) {
        this.hourlyInstantBookingCount = hourlyInstantBookingCount;
    }

    public int getCarId() {
        return carId;
    }

    public boolean isAvailableDaily() {
        return availableDaily;
    }

    public void setAvailableDaily(boolean availableDaily) {
        this.availableDaily = availableDaily;
    }

    public int getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(int dailyCount) {
        this.dailyCount = dailyCount;
    }

    public String[] getDailyDates() {
        return dailyDates;
    }

    public void setDailyDates(String[] dailyDates) {
        this.dailyDates = dailyDates;
    }

    public String getDailyTimePickup() {
        return dailyTimePickup;
    }

    public void setDailyTimePickup(String dailyTimePickup) {
        this.dailyTimePickup = dailyTimePickup;
    }

    public String getDailyTimeReturn() {
        return dailyTimeReturn;
    }

    public void setDailyTimeReturn(String dailyTimeReturn) {
        this.dailyTimeReturn = dailyTimeReturn;
    }

    public boolean isDailyInstantBooking() {
        return dailyInstantBooking;
    }

    public void setDailyInstantBooking(boolean dailyInstantBooking) {
        this.dailyInstantBooking = dailyInstantBooking;
    }

    public boolean isDailyCurbsideDelivery() {
        return dailyCurbsideDelivery;
    }

    public void setDailyCurbsideDelivery(boolean dailyCurbsideDelivery) {
        this.dailyCurbsideDelivery = dailyCurbsideDelivery;
    }

    public boolean isDailyAcceptCash() {
        return dailyAcceptCash;
    }

    public void setDailyAcceptCash(boolean dailyAcceptCash) {
        this.dailyAcceptCash = dailyAcceptCash;
    }

    public Float getDailyAmountRateFirst() {
        return dailyAmountRateFirst;
    }

    public void setDailyAmountRateFirst(Float dailyAmountRateFirst) {
        this.dailyAmountRateFirst = dailyAmountRateFirst;
    }

    public Float getDailyAmountRateSecond() {
        return dailyAmountRateSecond;
    }

    public void setDailyAmountRateSecond(Float dailyAmountRateSecond) {
        this.dailyAmountRateSecond = dailyAmountRateSecond;
    }

    public Float getDailyAmountDiscountFirst() {
        return dailyAmountDiscountFirst;
    }

    public void setDailyAmountDiscountFirst(Float dailyAmountDiscountFirst) {
        this.dailyAmountDiscountFirst = dailyAmountDiscountFirst;
    }

    public Float getDailyAmountDiscountSecond() {
        return dailyAmountDiscountSecond;
    }

    public void setDailyAmountDiscountSecond(Float dailyAmountDiscountSecond) {
        this.dailyAmountDiscountSecond = dailyAmountDiscountSecond;
    }

    public Integer getDailyMinRentalDuration() {
        return dailyMinRentalDuration;
    }

    public void setDailyMinRentalDuration(Integer dailyMinRentalDuration) {
        this.dailyMinRentalDuration = dailyMinRentalDuration;
    }

    public Integer getDailyFuelPolicyId() {
        return dailyFuelPolicyId;
    }

    public void setDailyFuelPolicyId(Integer dailyFuelPolicyId) {
        this.dailyFuelPolicyId = dailyFuelPolicyId;
    }

    public String getDailyFuelPolicyName() {
        return dailyFuelPolicyName;
    }

    public void setDailyFuelPolicyName(String dailyFuelPolicyName) {
        this.dailyFuelPolicyName = dailyFuelPolicyName;
    }

    public boolean isAvailableHourly() {
        return availableHourly;
    }

    public void setAvailableHourly(boolean availableHourly) {
        this.availableHourly = availableHourly;
    }

    public int getHourlyCount() {
        return hourlyCount;
    }

    public void setHourlyCount(int hourlyCount) {
        this.hourlyCount = hourlyCount;
    }

    public String[] getHourlyDates() {
        return hourlyDates;
    }

    public void setHourlyDates(String[] hourlyDates) {
        this.hourlyDates = hourlyDates;
    }

    public String getHourlyBeginTime() {
        return hourlyBeginTime;
    }

    public void setHourlyBeginTime(String hourlyBeginTime) {
        this.hourlyBeginTime = hourlyBeginTime;
    }

    public String getHourlyEndTime() {
        return hourlyEndTime;
    }

    public void setHourlyEndTime(String hourlyEndTime) {
        this.hourlyEndTime = hourlyEndTime;
    }

    public boolean isHourlyInstantBooking() {
        return hourlyInstantBooking;
    }

    public void setHourlyInstantBooking(boolean hourlyInstantBooking) {
        this.hourlyInstantBooking = hourlyInstantBooking;
    }

    public boolean isHourlyCurbsideDelivery() {
        return hourlyCurbsideDelivery;
    }

    public void setHourlyCurbsideDelivery(boolean hourlyCurbsideDelivery) {
        this.hourlyCurbsideDelivery = hourlyCurbsideDelivery;
    }

    public boolean isHourlyAcceptCash() {
        return hourlyAcceptCash;
    }

    public void setHourlyAcceptCash(boolean hourlyAcceptCash) {
        this.hourlyAcceptCash = hourlyAcceptCash;
    }

    public Float getHourlyAmountRateFirst() {
        return hourlyAmountRateFirst;
    }

    public void setHourlyAmountRateFirst(Float hourlyAmountRateFirst) {
        this.hourlyAmountRateFirst = hourlyAmountRateFirst;
    }

    public Float getHourlyAmountRateSecond() {
        return hourlyAmountRateSecond;
    }

    public void setHourlyAmountRateSecond(Float hourlyAmountRateSecond) {
        this.hourlyAmountRateSecond = hourlyAmountRateSecond;
    }

    public Float getHourlyAmountDiscountFirst() {
        return hourlyAmountDiscountFirst;
    }

    public void setHourlyAmountDiscountFirst(Float hourlyAmountDiscountFirst) {
        this.hourlyAmountDiscountFirst = hourlyAmountDiscountFirst;
    }

    public Float getHourlyAmountDiscountSecond() {
        return hourlyAmountDiscountSecond;
    }

    public void setHourlyAmountDiscountSecond(Float hourlyAmountDiscountSecond) {
        this.hourlyAmountDiscountSecond = hourlyAmountDiscountSecond;
    }

    public Integer getHourlyMinRentalDuration() {
        return hourlyMinRentalDuration;
    }

    public void setHourlyMinRentalDuration(Integer hourlyMinRentalDuration) {
        this.hourlyMinRentalDuration = hourlyMinRentalDuration;
    }

    public String getHourlyAmountPayMileage() {
        return hourlyAmountPayMileage;
    }

    public void setHourlyAmountPayMileage(String hourlyAmountPayMileage) {
        this.hourlyAmountPayMileage = hourlyAmountPayMileage;
    }

    public Integer getHourlyFuelPolicyId() {
        return hourlyFuelPolicyId;
    }

    public void setHourlyFuelPolicyId(Integer hourlyFuelPolicyId) {
        this.hourlyFuelPolicyId = hourlyFuelPolicyId;
    }

    public String getHourlyFuelPolicyName() {
        return hourlyFuelPolicyName;
    }

    public void setHourlyFuelPolicyName(String hourlyFuelPolicyName) {
        this.hourlyFuelPolicyName = hourlyFuelPolicyName;
    }

    public DeliveryRatesEntity getDeliveryRates() {
        return deliveryRates;
    }

    public void setDeliveryRates(DeliveryRatesEntity deliveryRates) {
        this.deliveryRates = deliveryRates;
    }

    public CarDetailsEntity getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetailsEntity carDetails) {
        this.carDetails = carDetails;
    }

    public static RentalDetails from(@NonNull RentalDetails prototype) {
        RentalDetails copy = new RentalDetails(prototype.getCarId());
        copy.setDeliveryRates(prototype.getDeliveryRates());
        copy.setCarDetails(prototype.getCarDetails());
        copy.setAvailableHourly(prototype.isAvailableHourly());
        copy.setAvailableDaily(prototype.isAvailableDaily());
        copy.setDailyDates(prototype.getDailyDates());
        copy.setHourlyDates(prototype.getHourlyDates());
        copy.setDailyTimePickup(prototype.getDailyTimePickup());
        copy.setDailyTimeReturn(prototype.getDailyTimeReturn());
        copy.setHourlyBeginTime(prototype.getHourlyBeginTime());
        copy.setHourlyEndTime(prototype.getHourlyEndTime());
        copy.setDailyCount(prototype.getDailyCount());
        copy.setDailyInstantBooking(prototype.isDailyInstantBooking());
        copy.setDailyInstantBookingCount(prototype.getDailyInstantBookingCount());
        copy.setDailyCurbsideDelivery(prototype.isDailyCurbsideDelivery());
        copy.setDailyAcceptCash(prototype.isDailyAcceptCash());
        copy.setDailyFuelPolicyName(prototype.getDailyFuelPolicyName());
        copy.setDailyFuelPolicyId(prototype.getDailyFuelPolicyId());
        copy.setDailyAmountDiscountFirst(prototype.getDailyAmountDiscountFirst());
        copy.setDailyAmountDiscountSecond(prototype.getDailyAmountDiscountSecond());
        copy.setDailyAmountRateFirst(prototype.getDailyAmountRateFirst());
        copy.setDailyAmountRateSecond(prototype.getDailyAmountRateSecond());
        copy.setDailyMinRentalDuration(prototype.getDailyMinRentalDuration());
        copy.setHourlyCount(prototype.getHourlyCount());
        copy.setHourlyInstantBooking(prototype.isHourlyInstantBooking());
        copy.setHourlyInstantBookingCount(prototype.getHourlyInstantBookingCount());
        copy.setHourlyCurbsideDelivery(prototype.isHourlyCurbsideDelivery());
        copy.setHourlyAcceptCash(prototype.isHourlyAcceptCash());
        copy.setHourlyFuelPolicyId(prototype.getHourlyFuelPolicyId());
        copy.setHourlyFuelPolicyName(prototype.getHourlyFuelPolicyName());
        copy.setHourlyAmountRateFirst(prototype.getHourlyAmountRateFirst());
        copy.setHourlyAmountRateSecond(prototype.getHourlyAmountRateSecond());
        copy.setHourlyAmountPayMileage(prototype.getHourlyAmountPayMileage());
        copy.setHourlyMinRentalDuration(prototype.getHourlyMinRentalDuration());
        return copy;
    }
}
