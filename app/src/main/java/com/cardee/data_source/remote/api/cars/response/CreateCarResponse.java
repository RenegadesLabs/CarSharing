package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCarResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private CarResponseBody responseBody;

    public CreateCarResponse() {

    }

    public CarResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(CarResponseBody responseBody) {
        this.responseBody = responseBody;
    }
}
