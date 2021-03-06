package com.cardee.data_source.remote.api.offers;

import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.offers.request.GetFavorites;
import com.cardee.data_source.remote.api.offers.request.SearchOffers;
import com.cardee.data_source.remote.api.offers.request.SortOffers;
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponse;
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
import retrofit2.http.Query;

public interface Offers {

    @GET("offers/")
    Observable<OffersResponse> browseCars();

    @POST("offers/")
    Maybe<OffersResponse> obtainCarsByFilter(@Body FilterRequest request);

    @PUT("offers/{id}/favorites")
    Single<NoDataResponse> addCarToFavorites(@Path("id") int id);

    @POST("offers/")
    Call<OffersResponse> getFavorites(@Body GetFavorites requestBody);

    @POST("offers/")
    Call<OffersResponse> getSorted(@Body SortOffers requestBody);

    @POST("offers/")
    Call<OffersResponse> searchOffers(@Body SearchOffers requestBody);

    @GET("offers/{id}")
    Maybe<OfferByIdResponse> getOfferById(@Path("id") int id);

    @GET("offers/{id}")
    Maybe<OfferByIdResponse> getOfferById(@Path("id") int id, @Query("lat") double lat, @Query("lng") double lng);
}
