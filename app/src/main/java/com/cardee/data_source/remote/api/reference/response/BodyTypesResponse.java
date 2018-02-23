package com.cardee.data_source.remote.api.reference.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.common.entity.BodyType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyTypesResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private BodyType[] types;

    public BodyTypesResponse(){

    }

    public BodyType[] getTypes() {
        return types;
    }

    public void setTypes(BodyType[] types) {
        this.types = types;
    }
}
