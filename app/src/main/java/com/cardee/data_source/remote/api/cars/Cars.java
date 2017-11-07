package com.cardee.data_source.remote.api.cars;

import com.cardee.data_source.remote.api.cars.response.CarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Cars {

    @GET("cars/{id}")
    Call<CarResponse> getCar(@Path("id") int id);
}
