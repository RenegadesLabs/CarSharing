package com.cardee.data_source.remote.api.booking.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewAsOwner {

    @SerializedName("rating")
    @Expose
    private Integer rating;

    @SerializedName("review_from_owner")
    @Expose
    private String reviewFromOwner;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewFromOwner() {
        return reviewFromOwner;
    }

    public void setReviewFromOwner(String reviewFromOwner) {
        this.reviewFromOwner = reviewFromOwner;
    }
}
