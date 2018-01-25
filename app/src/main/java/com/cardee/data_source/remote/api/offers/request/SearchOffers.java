package com.cardee.data_source.remote.api.offers.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchOffers {

    @Expose
    @SerializedName("search_criteria")
    private String mSearchCriteria;

    public SearchOffers(String searchCriteria) {
        mSearchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return mSearchCriteria;
    }
}
