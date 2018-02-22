package com.cardee.data_source.remote.api.reference.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.common.entity.VehicleType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleTypesResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private VehicleType[] types;

    public VehicleTypesResponse(){

    }

    public VehicleType[] getTypes() {
        return types;
    }

    public void setTypes(VehicleType[] types) {
        this.types = types;
    }
}
