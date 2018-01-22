package com.cardee.data_source.remote.api.offers.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferOwnerEntity {

    @Expose
    @SerializedName("profile_id")
    private Integer mId;

    @Expose
    @SerializedName("profile_photo")
    private String mPhotoUrl;

    @Expose
    @SerializedName("name")
    private String mName;

    public Integer getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
