package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseCarEntity {

    @Expose
    @SerializedName("car_id")
    private Integer carId;
    @Expose
    @SerializedName("rating")
    private Float carRating;
    @Expose
    @SerializedName("car_transmission")
    private String carTransmission;
    @Expose
    @SerializedName("seating_capacity")
    private Integer seatingCapacity;
    @Expose
    @SerializedName("license_plate_number")
    private String licencePlateNumber;
    @Expose
    @SerializedName("year_manufacture")
    private String manufactureYear;
    @Expose
    @SerializedName("car_body_type")
    private String bodyType;
    @Expose
    @SerializedName("car_make")
    private String make;
    @Expose
    @SerializedName("car_title")
    private String title;
    @Expose
    @SerializedName("car_model")
    private String model;
    @Expose
    @SerializedName("vehicle_type")
    private String vehicleType;
    @Expose
    @SerializedName("vehicle_type_id")
    private Integer vehicleTypeId;
    @Expose
    @SerializedName("car_engine_capacity")
    private String engineCapacity;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("images")
    private ImageEntity[] images;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("town")
    private String town;


    public BaseCarEntity() {

    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarTransmission() {
        return carTransmission;
    }

    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageEntity[] getImages() {
        return images;
    }

    public void setImages(ImageEntity[] images) {
        this.images = images;
    }

    public Float getCarRating() {
        return carRating;
    }

    public void setCarRating(Float carRating) {
        this.carRating = carRating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
