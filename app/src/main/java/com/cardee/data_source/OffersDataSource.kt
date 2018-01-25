package com.cardee.data_source

import com.cardee.data_source.remote.api.offers.response.Offer
import com.cardee.data_source.remote.api.offers.response.OffersResponse
import io.reactivex.Observable

interface OffersDataSource {

    fun obtainAll(): Observable<OffersResponse>

    fun obtainById(id: Int): Observable<Offer>

    fun toggleFavorite(id: Int)

}
