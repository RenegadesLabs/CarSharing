package com.cardee.data_source.remote.api.offers;

import com.cardee.data_source.remote.api.offers.response.OffersResponse;
import com.cardee.domain.renter.entity.FilterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Offers {

    @GET("offers/")
    Call<OffersResponse> browseCars();

    @POST("offers/")
    Call<OffersResponse> obtainCarsByFilter(@Body FilterRequest request);
}
