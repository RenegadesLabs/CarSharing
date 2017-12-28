package com.cardee.data_source.remote.api.booking.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RenterRateEntity {

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("rental_experience_name")
    @Expose
    private String rentalExperienceName;
    @SerializedName("rental_experience_id")
    @Expose
    private Integer rentalExperienceId;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRentalExperienceName() {
        return rentalExperienceName;
    }

    public void setRentalExperienceName(String rentalExperienceName) {
        this.rentalExperienceName = rentalExperienceName;
    }

    public Integer getRentalExperienceId() {
        return rentalExperienceId;
    }

    public void setRentalExperienceId(Integer rentalExperienceId) {
        this.rentalExperienceId = rentalExperienceId;
    }
}
