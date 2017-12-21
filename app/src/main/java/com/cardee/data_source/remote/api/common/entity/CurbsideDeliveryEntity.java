package com.cardee.data_source.remote.api.common.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurbsideDeliveryEntity {

    @Expose
    @SerializedName("is_curbside_delivery")
    private Boolean isCurbsideDelivery;

    public CurbsideDeliveryEntity(Boolean isCurbsideDelivery) {
        this.isCurbsideDelivery = isCurbsideDelivery;
    }

    public Boolean getCurbsideDelivery() {
        return isCurbsideDelivery;
    }

}
