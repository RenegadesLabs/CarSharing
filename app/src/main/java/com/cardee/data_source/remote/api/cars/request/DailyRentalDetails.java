package com.cardee.data_source.remote.api.cars.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyRentalDetails {

    @Expose
    @SerializedName("is_instant_booking")
    private Boolean instantBooking;
    @Expose
    @SerializedName("is_curbside_delivery")
    private Boolean curbsideDelivery;
    @Expose
    @SerializedName("is_accept_cash")
    private Boolean acceptCash;
    @Expose
    @SerializedName("fuel_policy_id")
    private Integer fuelPolicyId;
    @Expose
    @SerializedName("rate_first")
    private Float rentalRateFirst;
    @Expose
    @SerializedName("rate_second")
    private Float rentalRateSecond;
    @Expose
    @SerializedName("amnt_discount_first")
    private Float amountDiscountFirst;
    @Expose
    @SerializedName("amnt_discount_second")
    private Float amountDiscountSecond;
    @Expose
    @SerializedName("min_rental_duration")
    private Integer minRentalDuration;
    @Expose
    @SerializedName("time_pickup")
    private String pickupTime;
    @Expose
    @SerializedName("time_return")
    private String returnTime;
    @Expose
    @SerializedName("amnt_pay_mileage")
    private Float amountPayMileage;

    public DailyRentalDetails(){

    }

    public Boolean getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instantBooking = instantBooking;
    }

    public Boolean getCurbsideDelivery() {
        return curbsideDelivery;
    }

    public void setCurbsideDelivery(Boolean curbsideDelivery) {
        this.curbsideDelivery = curbsideDelivery;
    }

    public Boolean getAcceptCash() {
        return acceptCash;
    }

    public void setAcceptCash(Boolean acceptCash) {
        this.acceptCash = acceptCash;
    }

    public Integer getFuelPolicyId() {
        return fuelPolicyId;
    }

    public void setFuelPolicyId(Integer fuelPolicyId) {
        this.fuelPolicyId = fuelPolicyId;
    }

    public Integer getMinRentalDuration() {
        return minRentalDuration;
    }

    public void setMinRentalDuration(Integer minRentalDuration) {
        this.minRentalDuration = minRentalDuration;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Float getAmountPayMileage() {
        return amountPayMileage;
    }

    public void setAmountPayMileage(Float amountPayMileage) {
        this.amountPayMileage = amountPayMileage;
    }

    public Float getRentalRateFirst() {
        return rentalRateFirst;
    }

    public void setRentalRateFirst(Float rentalRateFirst) {
        this.rentalRateFirst = rentalRateFirst;
    }

    public Float getRentalRateSecond() {
        return rentalRateSecond;
    }

    public void setRentalRateSecond(Float rentalRateSecond) {
        this.rentalRateSecond = rentalRateSecond;
    }

    public Float getAmountDiscountFirst() {
        return amountDiscountFirst;
    }

    public void setAmountDiscountFirst(Float amountDiscountFirst) {
        this.amountDiscountFirst = amountDiscountFirst;
    }

    public Float getAmountDiscountSecond() {
        return amountDiscountSecond;
    }

    public void setAmountDiscountSecond(Float amountDiscountSecond) {
        this.amountDiscountSecond = amountDiscountSecond;
    }
}
