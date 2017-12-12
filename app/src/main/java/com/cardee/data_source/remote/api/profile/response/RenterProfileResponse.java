package com.cardee.data_source.remote.api.profile.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenterProfileResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private RenterProfile renterProfile;

    public RenterProfileResponse() {
    }

    public RenterProfile getRenterProfile() {
        return renterProfile;
    }

    public void setRenterProfile(RenterProfile renterProfile) {
        this.renterProfile = renterProfile;
    }
}
