package com.cardee.data_source.remote.api.booking.response.entity;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BookingRentalTerms extends ErrorResponseBody {

    @Expose
    @SerializedName("is_instant_booking")
    private Boolean isInstantBooking;
    @Expose
    @SerializedName("is_curbside_delivery")
    private Boolean isCurbsideDelivery;
    @Expose
    @SerializedName("is_accept_cash")
    private Boolean isAcceptCash;
    @Expose
    @SerializedName("fuel_policy")
    private FuelPolicyEntity fuelPolicy;
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
    @SerializedName("time_pickup")
    private String timePickup;
    @Expose
    @SerializedName("time_return")
    private String timeReturn;

    public BookingRentalTerms(){

    }

    public Boolean getInstantBooking() {
        return isInstantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        isInstantBooking = instantBooking;
    }

    public Boolean getCurbsideDelivery() {
        return isCurbsideDelivery;
    }

    public void setCurbsideDelivery(Boolean curbsideDelivery) {
        isCurbsideDelivery = curbsideDelivery;
    }

    public Boolean getAcceptCash() {
        return isAcceptCash;
    }

    public void setAcceptCash(Boolean acceptCash) {
        isAcceptCash = acceptCash;
    }

    public FuelPolicyEntity getFuelPolicy() {
        return fuelPolicy;
    }

    public void setFuelPolicy(FuelPolicyEntity fuelPolicy) {
        this.fuelPolicy = fuelPolicy;
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

    public Integer getMinRentalDuration() {
        return minRentalDuration;
    }

    public void setMinRentalDuration(Integer minRentalDuration) {
        this.minRentalDuration = minRentalDuration;
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
}
