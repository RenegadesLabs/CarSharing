package com.cardee.data_source.remote.api.inbox.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipient {

    @Expose
    @SerializedName("profile_id")
    private Integer mProfileId;
    @Expose
    @SerializedName("profile_type")
    private String mProfileType;
    @Expose
    @SerializedName("photo")
    private String mPhoto;
    @Expose
    @SerializedName("name")
    private String mName;

    public Recipient() {
    }

    public Integer getProfileId() {
        return mProfileId;
    }

    public void setProfileId(Integer profileId) {
        mProfileId = profileId;
    }

    public String getProfileType() {
        return mProfileType;
    }

    public void setProfileType(String profileType) {
        mProfileType = profileType;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
