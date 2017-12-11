package com.cardee.data_source.remote.api.cars.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailabilityValues {

    @Expose
    @SerializedName("is_available_order_days")
    private Boolean availableDaily;
    @Expose
    @SerializedName("is_available_order_hours")
    private Boolean availableHourly;

    public AvailabilityValues(){

    }

    public Boolean getAvailableDaily() {
        return availableDaily;
    }

    public void setAvailableDaily(Boolean availableDaily) {
        this.availableDaily = availableDaily;
    }

    public Boolean getAvailableHourly() {
        return availableHourly;
    }

    public void setAvailableHourly(Boolean availableHourly) {
        this.availableHourly = availableHourly;
    }
}
