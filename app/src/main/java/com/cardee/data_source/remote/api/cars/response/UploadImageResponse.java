package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.response.entity.UploadImageResponseBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImageResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private UploadImageResponseBody body;

    public UploadImageResponse(){

    }

    public UploadImageResponseBody getBody() {
        return body;
    }

    public void setBody(UploadImageResponseBody body) {
        this.body = body;
    }
}
