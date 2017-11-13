package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Transmission {

    @Expose
    @SerializedName("order_num")
    private Integer orderNumber;
    @Expose
    @SerializedName("car_transmission_id")
    private Integer transmissionId;
    @Expose
    @SerializedName("car_transmission_name")
    private String transmissionName;

    public Transmission(){

    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public String getTransmissionName() {
        return transmissionName;
    }

    public void setTransmissionName(String transmissionName) {
        this.transmissionName = transmissionName;
    }
}
