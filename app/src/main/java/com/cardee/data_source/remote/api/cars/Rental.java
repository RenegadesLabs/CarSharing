package com.cardee.data_source.remote.api.cars;


import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.cars.request.DailyRentalDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Rental {

    @PUT("cars/{id}/rental/daily")
    @Headers("Content-Type: application/json")
    Call<NoDataResponse> saveDailyRental(@Path("id") int id, @Body DailyRentalDetails rentalDetails);
}
