package com.cardee.data_source.remote.api.offers

import com.cardee.data_source.remote.api.NoDataResponse
import com.cardee.data_source.remote.api.offers.request.GetFavorites
import com.cardee.data_source.remote.api.offers.request.SearchOffers
import com.cardee.data_source.remote.api.offers.response.OffersResponse
import com.cardee.data_source.remote.api.offers.response.OffersResponseJava
import com.cardee.domain.renter.entity.FilterRequest
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface Offers {

    @GET("offers/")
    fun obtainAll(): Single<OffersResponse>

    @GET("offers/")
    fun browseCars(): Call<OffersResponseJava>

    @POST("offers/")
    fun obtainCarsByFilter(@Body request: FilterRequest): Maybe<OffersResponseJava>

    @PUT("offers/{id}/favorites")
    fun addCarToFavorites(@Path("id") id: Int): Single<NoDataResponse>

    @POST("offers/")
    fun getFavorites(@Body requestBody: GetFavorites): Call<OffersResponseJava>

    @POST("offers/")
    fun searchOffers(@Body requestBody: SearchOffers): Call<OffersResponseJava>
}