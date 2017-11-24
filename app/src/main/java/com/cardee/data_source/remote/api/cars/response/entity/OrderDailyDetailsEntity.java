package com.cardee.data_source.remote.api.cars.response.entity;


import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDailyDetailsEntity {

    @Expose
    @SerializedName("time_pickup")
    private String timePickup;
    @Expose
    @SerializedName("time_return")
    private String timeReturn;
    @Expose
    @SerializedName("is_instant_booking")
    private Boolean instantBooking;
    @Expose
    @SerializedName("is_curbside_delivery")
    private Boolean curbsideDelivery;
    @Expose
    @SerializedName("is_accept_cash")
    private Boolean acceptCrash;
    @Expose
    @SerializedName("amnt_rate_first")
    private Float amntRateFirst;
    @Expose
    @SerializedName("amnt_rate_second")
    private Float amntRateSecond;
    @Expose
    @SerializedName("amnt_discount_first")
    private Float amntDiscountFirst;
    @Expose
    @SerializedName("amnt_discount_second")
    private Float amntDiscountSecond;
    @Expose
    @SerializedName("min_rental_duration")
    private Float minRentalDuration;
    @Expose
    @SerializedName("fuel_policy")
    private FuelPolicyEntity fuelPolicy;

    public OrderDailyDetailsEntity() {

    }

    public String getTimePickup() {
        return timePickup;
    }

    public void setTimePickup(String timePickup) {
        this.timePickup = timePickup;
    }

    public String getTimeReturn() {
        return timeReturn;
    }

    public void setTimeReturn(String timeReturn) {
        this.timeReturn = timeReturn;
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

    public Boolean getAcceptCrash() {
        return acceptCrash;
    }

    public void setAcceptCrash(Boolean acceptCrash) {
        this.acceptCrash = acceptCrash;
    }

    public Float getAmntRateFirst() {
        return amntRateFirst;
    }

    public void setAmntRateFirst(Float amntRateFirst) {
        this.amntRateFirst = amntRateFirst;
    }

    public Float getAmntRateSecond() {
        return amntRateSecond;
    }

    public void setAmntRateSecond(Float amntRateSecond) {
        this.amntRateSecond = amntRateSecond;
    }

    public Float getAmntDiscountFirst() {
        return amntDiscountFirst;
    }

    public void setAmntDiscountFirst(Float amntDiscountFirst) {
        this.amntDiscountFirst = amntDiscountFirst;
    }

    public Float getAmntDiscountSecond() {
        return amntDiscountSecond;
    }

    public void setAmntDiscountSecond(Float amntDiscountSecond) {
        this.amntDiscountSecond = amntDiscountSecond;
    }

    public Float getMinRentalDuration() {
        return minRentalDuration;
    }

    public void setMinRentalDuration(Float minRentalDuration) {
        this.minRentalDuration = minRentalDuration;
    }

    public FuelPolicyEntity getFuelPolicy() {
        return fuelPolicy;
    }

    public void setFuelPolicy(FuelPolicyEntity fuelPolicy) {
        this.fuelPolicy = fuelPolicy;
    }
}
