package com.cardee.domain.owner.entity;

public class RentalDetails {

    private final int carId;

    private boolean availableDaily;
    private int dailyCount;
    private String[] dailyDates;
    private String dailyTimePickup;
    private String dailyTimeReturn;
    private boolean dailyInstantBooking;
    private boolean dailyCurbsideDelivery;
    private boolean dailyAcceptCash;
    private Float dailyAmountRateFirst;
    private Float dailyAmountRateSecond;
    private Float dailyAmountDiscountFirst;
    private Float dailyAmountDiscountSecond;
    private Float dailyMinRentalDuration;
    private Integer dailyFuelPolicyId;
    private String dailyFuelPolicyName;

    private boolean availableHourly;
    private int hourlyCount;
    private String[] hourlyDates;
    private String hourlyBeginTime;
    private String hourlyEndTime;
    private boolean hourlyInstantBooking;
    private boolean hourlyCurbsideDelivery;
    private boolean hourlyAcceptCash;
    private Float hourlyAmountRateFirst;
    private Float hourlyAmountRateSecond;
    private Float hourlyMinRentalDuration;
    private String hourlyAmountPayMileage;
    private Integer hourlyFuelPolicyId;
    private String hourlyFuelPolicyName;

    public RentalDetails(int carId) {
        this.carId = carId;
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

    public Float getDailyMinRentalDuration() {
        return dailyMinRentalDuration;
    }

    public void setDailyMinRentalDuration(Float dailyMinRentalDuration) {
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

    public Float getHourlyMinRentalDuration() {
        return hourlyMinRentalDuration;
    }

    public void setHourlyMinRentalDuration(Float hourlyMinRentalDuration) {
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
}
