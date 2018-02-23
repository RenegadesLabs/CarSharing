package com.cardee.data_source.remote.api.profile.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerProfileResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private OwnerProfile ownerProfile;

    public OwnerProfileResponse() {
    }

    public OwnerProfile getOwnerProfile() {
        return ownerProfile;
    }

    public void setOwnerProfile(OwnerProfile ownerProfile) {
        this.ownerProfile = ownerProfile;
    }
}