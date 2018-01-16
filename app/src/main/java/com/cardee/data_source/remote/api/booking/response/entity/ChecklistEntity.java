package com.cardee.data_source.remote.api.booking.response.entity;

import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistEntity {

    @Expose
    @SerializedName("remarks")
    private String remarks;
    @Expose
    @SerializedName("booking_id")
    private int bookingId;
    @Expose
    @SerializedName("tank_part")
    private float tank;
    @Expose
    @SerializedName("master_mileage")
    private int mileage;
    @Expose
    @SerializedName("fuel_policy")
    private FuelPolicyEntity fuelPolicy;
    @Expose
    @SerializedName("driver")
    private RenterProfile driver;
    @Expose
    @SerializedName("images")
    private ImageEntity[] images;
    @Expose
    @SerializedName("photos")
    private int[] imageIds;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public float getTank() {
        return tank;
    }

    public void setTank(float tank) {
        this.tank = tank;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public FuelPolicyEntity getFuelPolicy() {
        return fuelPolicy;
    }

    public void setFuelPolicy(FuelPolicyEntity fuelPolicy) {
        this.fuelPolicy = fuelPolicy;
    }

    public RenterProfile getDriver() {
        return driver;
    }

    public void setDriver(RenterProfile driver) {
        this.driver = driver;
    }

    public ImageEntity[] getImages() {
        return images;
    }

    public void setImages(ImageEntity[] images) {
        this.images = images;
    }

    public int[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(Integer[] imageIds) {
        this.imageIds = convertIntegerArrayToInt(imageIds);
    }

    //TEMP ISSUE
    private int[] convertIntegerArrayToInt(Integer[] arr) {
        int[] temp = new int[arr.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Integer.parseInt(arr[i].toString());
        }
        return temp;
    }
}
