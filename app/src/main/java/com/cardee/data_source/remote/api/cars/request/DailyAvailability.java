package com.cardee.data_source.remote.api.cars.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyAvailability {

    @Expose
    @SerializedName("car_availability_dates")
    private String[] dates;

    public DailyAvailability(){

    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }
}
