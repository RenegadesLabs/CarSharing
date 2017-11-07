package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    public CarResponseBody mCarBody;

    public CarResponse() {

    }

    public CarResponseBody getCarBody() {
        return mCarBody;
    }

    public void setCarBody(CarResponseBody carBody) {
        mCarBody = carBody;
    }
}
