package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentalTermsSecurityDepositEntity {

    @Expose
    @SerializedName("is_req_security_deposit")
    private Boolean isRequired;
    @Expose
    @SerializedName("security_deposit_description")
    private String mDescription;

    public Boolean isRequired() {
        return isRequired;
    }

    public void setRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
