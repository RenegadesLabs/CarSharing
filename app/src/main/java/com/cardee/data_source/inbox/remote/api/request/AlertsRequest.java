package com.cardee.data_source.inbox.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlertsRequest {

    @SerializedName("alerts")
    @Expose
    private List<Integer> alerts = null;

    public List<Integer> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Integer> alerts) {
        this.alerts = alerts;
    }
}
