package com.cardee.data_source.remote.api.booking.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.response.entity.UploadBookingImageResponseBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadBookingImageResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private UploadBookingImageResponseBody body;

    public UploadBookingImageResponse() {

    }

    public UploadBookingImageResponseBody getBody() {
        return body;
    }

    public void setBody(UploadBookingImageResponseBody body) {
        this.body = body;
    }
}
