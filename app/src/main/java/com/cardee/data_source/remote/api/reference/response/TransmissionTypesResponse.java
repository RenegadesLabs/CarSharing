package com.cardee.data_source.remote.api.reference.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.common.entity.Transmission;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransmissionTypesResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private Transmission[] types;

    public TransmissionTypesResponse(){

    }

    public Transmission[] getTypes() {
        return types;
    }

    public void setTypes(Transmission[] types) {
        this.types = types;
    }
}
