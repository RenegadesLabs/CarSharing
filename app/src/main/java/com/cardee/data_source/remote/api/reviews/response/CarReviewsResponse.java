package com.cardee.data_source.remote.api.reviews.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarReviewsResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private CarReviews carReviews;

    public CarReviewsResponse() {
    }

    public CarReviews getCarReviews() {
        return carReviews;
    }

    public void setCarReviews(CarReviews carReviews) {
        this.carReviews = carReviews;
    }
}
