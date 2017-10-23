package com.cardee.data_source.remote.api.profile.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.response.entity.CarObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarsResponse extends BaseResponse {

    /**
     * Doesn't have ErrorResponseBody implementation because of using an array
     */
    @Expose
    @SerializedName("data")
    private CarObject[] cars;

    public CarsResponse() {

    }

    public CarObject[] getCars() {
        return cars;
    }

    public void setCars(CarObject[] cars) {
        this.cars = cars;
    }
}
