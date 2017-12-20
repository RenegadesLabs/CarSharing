package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptCashEntity {

    @Expose
    @SerializedName("is_accept_cash")
    private Boolean isAcceptCash;

    public AcceptCashEntity(Boolean isAcceptCash) {
        this.isAcceptCash = isAcceptCash;
    }

    public Boolean getAcceptCash() {
        return isAcceptCash;
    }
}
