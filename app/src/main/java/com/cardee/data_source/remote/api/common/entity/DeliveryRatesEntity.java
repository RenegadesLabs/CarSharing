package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryRatesEntity {
    @Expose
    @SerializedName("base_rate")
    private Float baseRate;
    @Expose
    @SerializedName("distance_rate")
    private Float distanceRate;
    @Expose
    @SerializedName("is_provide_free_delivery")
    private Boolean provideFreeDelivery;
    @Expose
    @SerializedName("rental_duration")
    private Float rentalDuration;

    public DeliveryRatesEntity() {

    }

    public Float getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Float baseRate) {
        this.baseRate = baseRate;
    }

    public Float getDistanceRate() {
        return distanceRate;
    }

    public void setDistanceRate(Float distanceRate) {
        this.distanceRate = distanceRate;
    }

    public Boolean getProvideFreeDelivery() {
        return provideFreeDelivery;
    }

    public void setProvideFreeDelivery(Boolean provideFreeDelivery) {
        this.provideFreeDelivery = provideFreeDelivery;
    }

    public Float getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Float rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
}
