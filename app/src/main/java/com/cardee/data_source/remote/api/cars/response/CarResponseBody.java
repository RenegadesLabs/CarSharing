package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.cars.response.entity.OrderDailyDetailsEntity;
import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.data_source.remote.api.cars.response.entity.OrderHourlyDetailsEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarResponseBody extends ErrorResponseBody {

    @Expose
    @SerializedName("car_details")
    private CarDetailsEntity carDetails;
    @Expose
    @SerializedName("is_available_order_days")
    private Boolean carAvailableOrderDays;
    @Expose
    @SerializedName("car_availability_daily_cnt")
    private Integer carAvailabilityDailyCount;
    @Expose
    @SerializedName("car_availability_daily")
    private String[] carAvailabilityDailyDates;
    @Expose
    @SerializedName("order_daily_details")
    private OrderDailyDetailsEntity orderDailyDetails;
    @Expose
    @SerializedName("is_available_order_hours")
    private Boolean carAvailableOrderHours;
    @Expose
    @SerializedName("car_availability_hourly_cnt")
    private Integer carAvailabilityHourlyCount;
    @Expose
    @SerializedName("car_availability_time_begin")
    private String carAvailabilityTimeBegin;
    @Expose
    @SerializedName("car_availability_time_end")
    private String carAvailabilityTimeEnd;
    @Expose
    @SerializedName("car_availability_hourly")
    private String[] carAvailabilityHourlyDates;
    @Expose
    @SerializedName("order_hourly_details")
    private OrderHourlyDetailsEntity orderHourlyDetails;

    public CarResponseBody() {

    }

    public CarDetailsEntity getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetailsEntity carDetails) {
        this.carDetails = carDetails;
    }

    public Boolean getCarAvailableOrderDays() {
        return carAvailableOrderDays;
    }

    public void setCarAvailableOrderDays(Boolean carAvailableOrderDays) {
        this.carAvailableOrderDays = carAvailableOrderDays;
    }

    public Integer getCarAvailabilityDailyCount() {
        return carAvailabilityDailyCount;
    }

    public void setCarAvailabilityDailyCount(Integer carAvailabilityDailyCount) {
        this.carAvailabilityDailyCount = carAvailabilityDailyCount;
    }

    public String[] getCarAvailabilityDailyDates() {
        return carAvailabilityDailyDates;
    }

    public void setCarAvailabilityDailyDates(String[] carAvailabilityDailyDates) {
        this.carAvailabilityDailyDates = carAvailabilityDailyDates;
    }

    public OrderDailyDetailsEntity getOrderDailyDetails() {
        return orderDailyDetails;
    }

    public void setOrderDailyDetails(OrderDailyDetailsEntity orderDailyDetails) {
        this.orderDailyDetails = orderDailyDetails;
    }

    public Boolean getCarAvailableOrderHours() {
        return carAvailableOrderHours;
    }

    public void setCarAvailableOrderHours(Boolean carAvailableOrderHours) {
        this.carAvailableOrderHours = carAvailableOrderHours;
    }

    public Integer getCarAvailabilityHourlyCount() {
        return carAvailabilityHourlyCount;
    }

    public void setCarAvailabilityHourlyCount(Integer carAvailabilityHourlyCount) {
        this.carAvailabilityHourlyCount = carAvailabilityHourlyCount;
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

    public String[] getCarAvailabilityHourlyDates() {
        return carAvailabilityHourlyDates;
    }

    public void setCarAvailabilityHourlyDates(String[] carAvailabilityHourlyDates) {
        this.carAvailabilityHourlyDates = carAvailabilityHourlyDates;
    }

    public OrderHourlyDetailsEntity getOrderHourlyDetails() {
        return orderHourlyDetails;
    }

    public void setOrderHourlyDetails(OrderHourlyDetailsEntity orderHourlyDetails) {
        this.orderHourlyDetails = orderHourlyDetails;
    }
}
