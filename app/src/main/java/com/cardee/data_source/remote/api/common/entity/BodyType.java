package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyType {

    @Expose
    @SerializedName("order_num")
    private Integer orderNumber;
    @Expose
    @SerializedName("car_body_type_id")
    private Integer bodyTypeId;
    @Expose
    @SerializedName("car_body_type_name")
    private String bodyTypeName;

    public BodyType() {

    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(Integer bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public String getBodyTypeName() {
        return bodyTypeName;
    }

    public void setBodyTypeName(String bodyTypeName) {
        this.bodyTypeName = bodyTypeName;
    }
}
