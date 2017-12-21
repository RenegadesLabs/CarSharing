package com.cardee.data_source.remote.api.booking.response;


import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingCar {

    @Expose
    @SerializedName("car_id")
    private Integer carId;
    @Expose
    @SerializedName("year_manufacture")
    private String manufactureYear;
    @Expose
    @SerializedName("license_plate_number")
    private String plateNumber;
    @Expose
    @SerializedName("car_title")
    private String carTitle;
    @Expose
    @SerializedName("images")
    private ImageEntity[] images;

    public BookingCar() {

    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public ImageEntity[] getImages() {
        return images;
    }

    public void setImages(ImageEntity[] images) {
        this.images = images;
    }
}
