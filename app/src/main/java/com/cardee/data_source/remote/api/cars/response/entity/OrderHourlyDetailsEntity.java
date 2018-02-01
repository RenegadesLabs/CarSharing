package com.cardee.data_source.remote.api.cars.response.entity;


import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHourlyDetailsEntity {

    @Expose
    @SerializedName("is_instant_booking")
    private Boolean instantBooking;
    @Expose
    @SerializedName("cnt_hours4instant_booking")
    private Integer instantBookingCount;
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
    private Integer minRentalDuration;
    @Expose
    @SerializedName("fuel_policy")
    private FuelPolicyEntity fuelPolicy;
    @Expose
    @SerializedName("amnt_pay_mileage")
    private String amntPayMileage;

    public OrderHourlyDetailsEntity() {

    }

    public Boolean getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instantBooking = instantBooking;
    }

    public Integer getInstantBookingCount() {
        return instantBookingCount == null ? 0 : instantBookingCount;
    }

    public void setInstantBookingCount(Integer instantBookingCount) {
        this.instantBookingCount = instantBookingCount;
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

    public void setAmntRateSecond(Float amntRateSecond) {
        this.amntRateSecond = amntRateSecond;
    }

    public Integer getMinRentalDuration() {
        return minRentalDuration;
    }

    public void setMinRentalDuration(Integer minRentalDuration) {
        this.minRentalDuration = minRentalDuration;
    }

    public FuelPolicyEntity getFuelPolicy() {
        return fuelPolicy;
    }

    public void setFuelPolicy(FuelPolicyEntity fuelPolicy) {
        this.fuelPolicy = fuelPolicy;
    }

    public String getAmntPayMileage() {
        return amntPayMileage;
    }

    public void setAmntPayMileage(String amntPayMileage) {
        this.amntPayMileage = amntPayMileage;
    }
}
