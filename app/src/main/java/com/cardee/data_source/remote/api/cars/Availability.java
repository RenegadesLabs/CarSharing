package com.cardee.data_source.remote.api.cars;

import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.cars.request.DailyAvailability;
import com.cardee.data_source.remote.api.cars.request.HourlyAvailability;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Availability {

    @PUT("cars/{id}/availability/daily")
    @Headers("Content-Type: application/json")
    Call<NoDataResponse> saveDailyAvailability(@Path("id") int id, @Body DailyAvailability availability);


    @PUT("cars/{id}/availability/hourly")
    @Headers("Content-Type: application/json")
    Call<NoDataResponse> saveHourlyAvailability(@Path("id") int id, @Body HourlyAvailability availability);

}
