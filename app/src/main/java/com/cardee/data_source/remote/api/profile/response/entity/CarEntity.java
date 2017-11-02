package com.cardee.data_source.remote.api.profile.response.entity;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarEntity extends ErrorResponseBody {

    @Expose
    @SerializedName("car_availability_hourly_cnt")
    private Integer carAvailabilityHourlyCount;
    @Expose
    @SerializedName("car_availability_daily_cnt")
    private Integer carAvailabilityDailyCount;
    @Expose
    @SerializedName("car_availability_time_begin")
    private String carAvailabilityTimeBegin;
    @Expose
    @SerializedName("car_availability_time_end")
    private String carAvailabilityTimeEnd;
    @Expose
    @SerializedName("is_available_order_hours")
    private Boolean carAvailableOrderHours;
    @Expose
    @SerializedName("is_available_order_days")
    private Boolean carAvailableOrderDays;
    @Expose
    @SerializedName("car_availability_hourly")
    private String[] carAvailabilityHourlyDates;
    @Expose
    @SerializedName("car_availability_daily")
    private String[] carAvailabilityDailyDates;
    @Expose
    @SerializedName("car_details")
    private BaseCarEntity carDetails;

    public CarEntity() {

    }

    public Integer getCarAvailabilityHourlyCount() {
        return carAvailabilityHourlyCount;
    }

    public void setCarAvailabilityHourlyCount(Integer carAvailabilityHourlyCount) {
        this.carAvailabilityHourlyCount = carAvailabilityHourlyCount;
    }

    public Integer getCarAvailabilityDailyCount() {
        return carAvailabilityDailyCount;
    }

    public void setCarAvailabilityDailyCount(Integer carAvailabilityDailyCount) {
        this.carAvailabilityDailyCount = carAvailabilityDailyCount;
    }

    public String getCarAvailabilityTimeBegin() {
        return carAvailabilityTimeBegin;
    }

    public void setCarAvailabilityTimeBegin(String carAvailabilityTimeBegin) {
        this.carAvailabilityTimeBegin = carAvailabilityTimeBegin;
    }

    public String getCarAvailabilityTimeEnd() {
        return carAvailabilityTimeEnd;
    }

    public void setCarAvailabilityTimeEnd(String carAvailabilityTimeEnd) {
        this.carAvailabilityTimeEnd = carAvailabilityTimeEnd;
    }

    public Boolean getCarAvailableOrderHours() {
        return carAvailableOrderHours;
    }

    public void setCarAvailableOrderHours(Boolean carAvailableOrderHours) {
        this.carAvailableOrderHours = carAvailableOrderHours;
    }

    public Boolean getCarAvailableOrderDays() {
        return carAvailableOrderDays;
    }

    public void setCarAvailableOrderDays(Boolean carAvailableOrderDays) {
        this.carAvailableOrderDays = carAvailableOrderDays;
    }

    public String[] getCarAvailabilityHourlyDates() {
        return carAvailabilityHourlyDates;
    }

    public void setCarAvailabilityHourlyDates(String[] carAvailabilityHourlyDates) {
        this.carAvailabilityHourlyDates = carAvailabilityHourlyDates;
    }

    public String[] getCarAvailabilityDailyDates() {
        return carAvailabilityDailyDates;
    }

    public void setCarAvailabilityDailyDates(String[] carAvailabilityDailyDates) {
        this.carAvailabilityDailyDates = carAvailabilityDailyDates;
    }

    public BaseCarEntity getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(BaseCarEntity carDetails) {
        this.carDetails = carDetails;
    }

}
