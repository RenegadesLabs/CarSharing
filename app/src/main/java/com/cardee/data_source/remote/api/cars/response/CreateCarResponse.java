package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCarResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private CreateCarResponseBody responseBody;

    public CreateCarResponse() {

    }

    public CreateCarResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(CreateCarResponseBody responseBody) {
        this.responseBody = responseBody;
    }
}
