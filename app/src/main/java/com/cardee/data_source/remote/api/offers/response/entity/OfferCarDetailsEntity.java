package com.cardee.data_source.remote.api.offers.response.entity;

import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OfferCarDetailsEntity extends BaseCarEntity {

    @Expose
    @SerializedName("is_favorite")
    private Boolean isFavorite;

    @Expose
    @SerializedName("longitude")
    private Double mLongitude;

    @Expose
    @SerializedName("latitude")
    private Double mLatitude;

    @Expose
    @SerializedName("distance")
    private Integer mDistance;

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Integer getDistance() {
        return mDistance;
    }

    public void setDistance(Integer distance) {
        mDistance = distance;
    }
}
