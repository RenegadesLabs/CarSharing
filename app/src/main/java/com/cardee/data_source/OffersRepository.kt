package com.cardee.data_source

import com.cardee.CardeeApp
import com.cardee.data_source.remote.api.offers.Offers
import com.cardee.data_source.remote.api.offers.response.Offer
import com.cardee.data_source.remote.api.offers.response.OffersResponse
import io.reactivex.Observable

object OffersRepository : OffersDataSource {

    private val api: Offers
    private val cache: MutableList<Offer>

    init {
        api = CardeeApp.retrofit.create(Offers::class.java)
        cache = ArrayList()
    }

    override fun obtainAll(): Observable<OffersResponse> =
            api.obtainAll().toObservable()

    override fun obtainById(id: Int): Observable<Offer> {
        return Observable.just(null)
    }

    override fun toggleFavorite(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
