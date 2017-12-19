package com.cardee.data_source.inbox.remote.api.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarVersion {

    @Expose
    @SerializedName("image")
    private String mImageUrl;
    @Expose
    @SerializedName("car_title")
    private String mCarTitle;
    @Expose
    @SerializedName("license_plate_number")
    private String mCarNumber;
    @Expose
    @SerializedName("year_manufacture")
    private String mYear;
    @Expose
    @SerializedName("car_id")
    private Integer mCraId;

    public CarVersion() {
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getCarTitle() {
        return mCarTitle;
    }

    public void setCarTitle(String carTitle) {
        mCarTitle = carTitle;
    }

    public String getCarNumber() {
        return mCarNumber;
    }

    public void setCarNumber(String carNumber) {
        mCarNumber = carNumber;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public Integer getCraId() {
        return mCraId;
    }

    public void setCraId(Integer craId) {
        mCraId = craId;
    }
}
