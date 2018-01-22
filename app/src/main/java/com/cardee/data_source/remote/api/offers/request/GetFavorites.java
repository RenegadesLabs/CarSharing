package com.cardee.data_source.remote.api.offers.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFavorites {

    @Expose
    @SerializedName("is_favorite")
    private Boolean favorite;

    public GetFavorites(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
