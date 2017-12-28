
package com.cardee.data_source.remote.api.reviews.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarReviews {

    @SerializedName("car_comfort_performance")
    @Expose
    private Integer carComfortPerformance;
    @SerializedName("car_condition_cleanliness")
    @Expose
    private Integer carConditionCleanliness;
    @SerializedName("overall_rental_experience")
    @Expose
    private Integer overallRentalExperience;
    @SerializedName("car_owner")
    @Expose
    private Integer carOwner;
    @SerializedName("reviews_cnt")
    @Expose
    private Integer reviewsCnt;
    @SerializedName("rating_cnt")
    @Expose
    private Integer ratingCnt;
    @SerializedName("like_cnt")
    @Expose
    private Integer likeCnt;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;

    public Integer getCarComfortPerformance() {

        return carComfortPerformance == null ? 0 : carComfortPerformance;
    }

    public void setCarComfortPerformance(Integer carComfortPerformance) {
        this.carComfortPerformance = carComfortPerformance;
    }

    public Integer getCarConditionCleanliness() {
        return carConditionCleanliness == null ? 0 : carConditionCleanliness;
    }

    public void setCarConditionCleanliness(Integer carConditionCleanliness) {
        this.carConditionCleanliness = carConditionCleanliness;
    }

    public Integer getOverallRentalExperience() {
        return overallRentalExperience == null ? 0 : overallRentalExperience;
    }

    public void setOverallRentalExperience(Integer overallRentalExperience) {
        this.overallRentalExperience = overallRentalExperience;
    }

    public Integer getCarOwner() {
        return carOwner == null ? 0 : carOwner;
    }

    public void setCarOwner(Integer carOwner) {
        this.carOwner = carOwner;
    }

    public Integer getReviewsCnt() {
        return reviewsCnt == null ? 0 : reviewsCnt;
    }

    public void setReviewsCnt(Integer reviewsCnt) {
        this.reviewsCnt = reviewsCnt;
    }

    public Integer getRatingCnt() {
        return ratingCnt == null ? 0 : ratingCnt;
    }

    public void setRatingCnt(Integer ratingCnt) {
        this.ratingCnt = ratingCnt;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
