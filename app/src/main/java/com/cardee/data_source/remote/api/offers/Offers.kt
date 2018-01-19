package com.cardee.data_source.remote.api.offers

import com.cardee.data_source.remote.api.offers.response.OffersResponse
import io.reactivex.Single
import retrofit2.http.GET


interface Offers {

    @GET("offers/")
    fun obtainAll(): Single<OffersResponse>


}