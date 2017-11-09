package com.cardee.data_source.remote.api.cars.response;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCarResponseBody extends ErrorResponseBody {

    @Expose
    @SerializedName("car_id")
    private Integer carId;

    public CreateCarResponseBody() {

    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
