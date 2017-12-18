package com.cardee.data_source.remote.api.common.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FuelPolicyEntity {
    @Expose
    @SerializedName("fuel_policy_id")
    private Integer fuelPolicyId;
    @Expose
    @SerializedName("fuel_policy_name")
    private String fuelPolicyName;

    @Expose
    @SerializedName("amnt_pay_mileage")
    private Float amountPayMileage;

    public FuelPolicyEntity() {

    }

    public Integer getFuelPolicyId() {
        return fuelPolicyId;
    }

    public void setFuelPolicyId(Integer fuelPolicyId) {
        this.fuelPolicyId = fuelPolicyId;
    }

    public String getFuelPolicyName() {
        return fuelPolicyName;
    }

    public void setFuelPolicyName(String fuelPolicyName) {
        this.fuelPolicyName = fuelPolicyName;
    }
    public Float getAmountPayMileage() {
        return amountPayMileage;
    }

    public void setAmountPayMileage(Float amountPayMileage) {
        this.amountPayMileage = amountPayMileage;
    }
}
