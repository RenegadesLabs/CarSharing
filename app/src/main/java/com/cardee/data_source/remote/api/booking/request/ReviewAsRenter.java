package com.cardee.data_source.remote.api.booking.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewAsRenter {

    @SerializedName("car_condition_cleanliness")
    @Expose
    private Integer carConditionCleanliness;

    @SerializedName("car_comfort_performance")
    @Expose
    private Integer carComfortPerformance;

    @SerializedName("car_owner")
    @Expose
    private Integer carOwner;

    @SerializedName("overall_rental_experience")
    @Expose
    private Integer overallRentalExperience;

    @SerializedName("review_from_renter")
    @Expose
    private String reviewFromRenter;

    public Integer getCarConditionCleanliness() {
        return carConditionCleanliness;
    }

    public void setCarConditionCleanliness(Integer carConditionCleanliness) {
        this.carConditionCleanliness = carConditionCleanliness;
    }

    public Integer getCarComfortPerformance() {
        return carComfortPerformance;
    }

    public void setCarComfortPerformance(Integer carComfortPerformance) {
        this.carComfortPerformance = carComfortPerformance;
    }

    public Integer getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(Integer carOwner) {
        this.carOwner = carOwner;
    }

    public Integer getOverallRentalExperience() {
        return overallRentalExperience;
    }

    public void setOverallRentalExperience(Integer overallRentalExperience) {
        this.overallRentalExperience = overallRentalExperience;
    }

    public String getReviewFromRenter() {
        return reviewFromRenter;
    }

    public void setReviewFromRenter(String reviewFromRenter) {
        this.reviewFromRenter = reviewFromRenter;
    }
}
