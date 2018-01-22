package com.cardee.domain.rx.browse_car

import com.cardee.data_source.OffersRepository
import com.cardee.data_source.remote.api.offers.response.Offer
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class ObtainAllOffers(private val repository: OffersRepository = OffersRepository,
                      executor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                      responseThread: Scheduler = AndroidSchedulers.mainThread()) :
        UseCase<List<Offer>>(executor = executor, responseThread = responseThread) {

    override fun buildUseCaseObserver(request: Request): Observable<Response<List<Offer>>> {
        val (offset, take) = request as ObtainAllRequest
        return repository.obtainAll().flatMap { response ->
            var useCaseResponse: Response<List<Offer>> = Response(response.offers, null, null)
            Observable.just(useCaseResponse)
        }
    }

    data class ObtainAllRequest(val offset: Int?, val take: Int?) : Request
}
