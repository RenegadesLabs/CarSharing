package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentalTermsRequirementsEntity {

    @Expose
    @SerializedName("req_min_age")
    private Integer mMinAge;
    @Expose
    @SerializedName("req_max_age")
    private Integer mMaxAge;
    @Expose
    @SerializedName("req_dr_exp")
    private Integer mExperience;

    public RentalTermsRequirementsEntity(Integer minAge, Integer maxAge, Integer experience) {
        mMinAge = minAge;
        mMaxAge = maxAge;
        mExperience = experience;
    }

    public Integer getMinAge() {
        return mMinAge;
    }

    public Integer getMaxAge() {
        return mMaxAge;
    }

    public Integer getExperience() {
        return mExperience;
    }
}
