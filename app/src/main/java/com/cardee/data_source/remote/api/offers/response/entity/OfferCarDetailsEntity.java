package com.cardee.data_source.remote.api.offers.response.entity;

import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OfferCarDetailsEntity extends BaseCarEntity {

    @Expose
    @SerializedName("is_favorite")
    private Boolean isFavorite;

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
