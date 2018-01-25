package com.cardee.data_source.remote.api.offers;

import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.offers.request.GetFavorites;
import com.cardee.data_source.remote.api.offers.request.SearchOffers;
import com.cardee.data_source.remote.api.offers.response.OffersResponse;
import com.cardee.domain.renter.entity.FilterRequest;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Offers {

    @GET("offers/")
    Observable<OffersResponse> browseCars();

    @PUT("offers/{id}/favorites")
    Single<NoDataResponse> addCarToFavorites(@Path("id") int id);

    @POST("offers/")
    Call<OffersResponse> getFavorites(@Body GetFavorites requestBody);

    @POST("offers/")
    Call<OffersResponse> searchOffers(@Body SearchOffers requestBody);

    @POST("offers/")
    Maybe<OffersResponse> obtainCarsByFilter(@Body FilterRequest request);
}
