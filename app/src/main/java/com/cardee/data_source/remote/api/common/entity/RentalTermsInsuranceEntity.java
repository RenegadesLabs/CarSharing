package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentalTermsInsuranceEntity {
    @Expose
    @SerializedName("compensation_excess")
    private String mInsuranceExcess;

    public RentalTermsInsuranceEntity(String insuranceExcess) {
        mInsuranceExcess = insuranceExcess;
    }

    public String getInsuranceExcess() {
        return mInsuranceExcess;
    }
}
