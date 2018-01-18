package com.cardee.data_source.remote.api.offers;

import com.cardee.data_source.remote.api.offers.response.OffersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Offers {

    @GET("offers/")
    public Call<OffersResponse> browseCars();
}
