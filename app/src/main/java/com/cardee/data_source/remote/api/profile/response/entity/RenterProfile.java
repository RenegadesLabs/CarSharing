package com.cardee.data_source.remote.api.profile.response.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RenterProfile {

    @SerializedName("profile_id")
    @Expose
    private Integer profileId;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("date_birth")
    @Expose
    private String dateBirth;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("license_effective_date")
    @Expose
    private String licenseEffectiveDate;
    @SerializedName("state")
    @Expose
    private String state;
    @Expose
    @SerializedName("deposit")
    private DepositState deposit;
    @SerializedName("social_net")
    @Expose
    private String socialNet;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("years_driving_exp")
    @Expose
    private Integer yearsDrivingExp;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("cnt_bookings")
    @Expose
    private Integer cntBookings;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("review_cnt")
    @Expose
    private Integer reviewCnt;
    @SerializedName("reviews")
    @Expose
    private List<RenterReview> reviews = null;
    @SerializedName("settings")
    @Expose
    private List<NotificationSettings> settings = null;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getLicenseEffectiveDate() {
        return licenseEffectiveDate;
    }

    public void setLicenseEffectiveDate(String licenseEffectiveDate) {
        this.licenseEffectiveDate = licenseEffectiveDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSocialNet() {
        return socialNet;
    }

    public void setSocialNet(String socialNet) {
        this.socialNet = socialNet;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getYearsDrivingExp() {
        return yearsDrivingExp;
    }

    public void setYearsDrivingExp(Integer yearsDrivingExp) {
        this.yearsDrivingExp = yearsDrivingExp;
    }

    public DepositState getDeposit() {
        return deposit;
    }

    public void setDeposit(DepositState deposit) {
        this.deposit = deposit;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCntBookings() {
        return cntBookings;
    }

    public void setCntBookings(Integer cntBookings) {
        this.cntBookings = cntBookings;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(Integer reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public List<RenterReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<RenterReview> reviews) {
        this.reviews = reviews;
    }

    public List<NotificationSettings> getSettings() {
        return settings;
    }

    public void setSettings(List<NotificationSettings> settings) {
        this.settings = settings;
    }
}
