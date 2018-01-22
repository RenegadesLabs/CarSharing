package com.cardee.data_source.remote.api.offers.response.entity;

import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferOrderDetails {

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
    private FuelPolicyEntity mFuelPolicy;

    @Expose
    @SerializedName("amnt_rate_first")
    private Float mRateFirst;

    @Expose
    @SerializedName("amnt_rate_second")
    private Float mRateSecond;

    @Expose
    @SerializedName("amnt_discount_first")
    private Float mDiscountFirst;

    @Expose
    @SerializedName("amnt_discount_second")
    private Float mDiscountSecond;

    @Expose
    @SerializedName("min_rental_duration")
    private Integer mMinRentalDuration;

    @Expose
    @SerializedName("time_pickup")
    private String mPickupTime;

    @Expose
    @SerializedName("time_return")
    private String mReturnTime;

    public Boolean isInstantBooking() {
        return isInstantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        isInstantBooking = instantBooking;
    }

    public Boolean isCurbsideDelivery() {
        return isCurbsideDelivery;
    }

    public void setCurbsideDelivery(Boolean curbsideDelivery) {
        isCurbsideDelivery = curbsideDelivery;
    }

    public Boolean isAcceptCash() {
        return isAcceptCash;
    }

    public void setAcceptCash(Boolean acceptCash) {
        isAcceptCash = acceptCash;
    }

    public FuelPolicyEntity getFuelPolicy() {
        return mFuelPolicy;
    }

    public void setFuelPolicy(FuelPolicyEntity fuelPolicy) {
        mFuelPolicy = fuelPolicy;
    }

    public Float getRateFirst() {
        return mRateFirst;
    }

    public void setRateFirst(Float rateFirst) {
        mRateFirst = rateFirst;
    }

    public Float getRateSecond() {
        return mRateSecond;
    }

    public void setRateSecond(Float rateSecond) {
        mRateSecond = rateSecond;
    }

    public Float getDiscountFirst() {
        return mDiscountFirst;
    }

    public void setDiscountFirst(Float discountFirst) {
        mDiscountFirst = discountFirst;
    }

    public Float getDiscountSecond() {
        return mDiscountSecond;
    }

    public void setDiscountSecond(Float discountSecond) {
        mDiscountSecond = discountSecond;
    }

    public Integer getMinRentalDuration() {
        return mMinRentalDuration;
    }

    public void setMinRentalDuration(Integer minRentalDuration) {
        mMinRentalDuration = minRentalDuration;
    }

    public String getPickupTime() {
        return mPickupTime;
    }

    public void setPickupTime(String pickupTime) {
        mPickupTime = pickupTime;
    }

    public String getReturnTime() {
        return mReturnTime;
    }

    public void setReturnTime(String returnTime) {
        mReturnTime = returnTime;
    }
}
