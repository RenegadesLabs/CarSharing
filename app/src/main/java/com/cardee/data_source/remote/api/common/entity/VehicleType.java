package com.cardee.data_source.remote.api.common.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @Expose
    @SerializedName("type_vehicle_id")
    private Integer vehicleTypeId;
    @Expose
    @SerializedName("type_vehicle_name")
    private String vehicleTypeName;

    public VehicleType() {

    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }
}
