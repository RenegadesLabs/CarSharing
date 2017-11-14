package com.cardee.data_source.remote.api.cars.response.entity;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UploadImageResponseBody extends ErrorResponseBody {

    @Expose
    @SerializedName("image_id")
    private Integer imageId;

    public UploadImageResponseBody() {

    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
