package com.cardee.data_source.remote.api.profile.response.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @Expose
    @SerializedName("image_id")
    private Integer imageId;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("thumbnail")
    private String thumbnail;
    @Expose
    @SerializedName("is_primary")
    private Boolean primary;

    public Image() {

    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean isPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
