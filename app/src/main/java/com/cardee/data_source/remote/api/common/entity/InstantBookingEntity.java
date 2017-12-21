package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstantBookingEntity {

    @Expose
    @SerializedName("is_instant_booking")
    private Boolean isInstantBooking;

    public InstantBookingEntity(Boolean isInstantBooking) {
        this.isInstantBooking = isInstantBooking;
    }

    public Boolean getInstantBooking() {
        return isInstantBooking;
    }
}
