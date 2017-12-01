package com.cardee.data_source.remote.api.profile.response.entity;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerProfile extends ErrorResponseBody {
    @Expose
    @SerializedName("profile_id")
    private Integer profileId;
    @Expose
    @SerializedName("profile_photo")
    private String profilePhotoLink;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("acceptance")
    private String acceptance;
    @Expose
    @SerializedName("response_time")
    private String responseTime;
    @Expose
    @SerializedName("cnt_bookings")
    private Integer bookingsCount;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("car_cnt")
    private Integer carCount;
    @Expose
    @SerializedName("review_cnt")
    private Integer reviewCount;
    @SerializedName("reviews")
    @Expose
    private OwnerReview[] reviews;
    @Expose
    @SerializedName("rating")
    private Float rating;
    @Expose
    @SerializedName("social_net")
    private String socialNetwork;
    @Expose
    @SerializedName("cars")
    private CarEntity[] cars;
    @Expose
    @SerializedName("settings")
    private NotificationSettings[] settings;

    public OwnerProfile() {

    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getProfilePhotoLink() {
        return profilePhotoLink;
    }

    public void setProfilePhotoLink(String profilePhotoLink) {
        this.profilePhotoLink = profilePhotoLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getBookingsCount() {
        return bookingsCount;
    }

    public void setBookingsCount(Integer bookingsCount) {
        this.bookingsCount = bookingsCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public OwnerReview[] getReviews() {
        return reviews;
    }

    public void setReviews(OwnerReview[] reviews) {
        this.reviews = reviews;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public CarEntity[] getCars() {
        return cars;
    }

    public void setCars(CarEntity[] cars) {
        this.cars = cars;
    }

    public NotificationSettings[] getSettings() {
        return settings;
    }

    public void setSettings(NotificationSettings[] settings) {
        this.settings = settings;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
