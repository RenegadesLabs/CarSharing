package com.cardee.data_source.remote.api.profile.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenterReview {
    @SerializedName("comment")
    @Expose
    private String review;
    @SerializedName("review_date")
    @Expose
    private String reviewDate;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("author")
    @Expose
    private Author author;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
