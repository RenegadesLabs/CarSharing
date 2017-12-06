package com.cardee.data_source.remote.api.reviews;

import com.cardee.data_source.remote.api.reviews.response.CarReviewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Reviews {

    @GET("cars/{id}/reviews")
    Call<CarReviewsResponse> getCarReviews(@Path("id") int carId);

}
