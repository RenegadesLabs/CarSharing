
package com.cardee.data_source.remote.api.reviews.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("rate")
    @Expose
    private Float rate;
    @SerializedName("review_date")
    @Expose
    private String reviewDate;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("renter")
    @Expose
    private Renter renter;

    public Float getRate() {
        return rate == null ? 0 : rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

}
