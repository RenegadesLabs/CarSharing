package com.cardee.data_source.remote.api.cars.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyAvailability {

    @Expose
    @SerializedName("car_availability_time_begin")
    private String startTime;
    @Expose
    @SerializedName("car_availability_time_end")
    private String endTime;
    @Expose
    @SerializedName("car_availability_dates")
    private String[] dates;

    public HourlyAvailability() {

    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }
}
