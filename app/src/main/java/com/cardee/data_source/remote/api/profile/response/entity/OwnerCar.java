package com.cardee.data_source.remote.api.profile.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerCar {

    @SerializedName("car_title")
    @Expose
    private String carTitle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("license_plate_number")
    @Expose
    private String licensePlateNumber;
    @SerializedName("year_manufacture")
    @Expose
    private String yearManufacture;

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(String yearManufacture) {
        this.yearManufacture = yearManufacture;
    }
}
