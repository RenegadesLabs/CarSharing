
package com.cardee.data_source.remote.api.reviews.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Renter {

    @SerializedName("profile_id")
    @Expose
    private Integer profileId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
