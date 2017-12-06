package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentalTermsAdditionalEntity {

    @Expose
    @SerializedName("additional_terms")
    private String mAdditionalTerms;

    public RentalTermsAdditionalEntity(String additionalTerms) {
        mAdditionalTerms = additionalTerms;
    }

    public String getAdditionalTerms() {
        return mAdditionalTerms;
    }
}
