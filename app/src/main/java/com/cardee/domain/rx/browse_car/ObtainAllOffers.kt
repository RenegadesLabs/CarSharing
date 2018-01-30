package com.cardee.domain.rx.browse_car

import com.cardee.data_source.OffersRepository
import com.cardee.domain.renter.entity.OfferCar
import com.cardee.domain.renter.entity.mapper.OfferResponseBodyToOfferMapper
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
        UseCase<List<OfferCar>>(executor = executor, responseThread = responseThread) {

    override fun buildUseCaseObserver(request: Request): Observable<Response<List<OfferCar>>> {
        val (offset, take) = request as ObtainAllRequest
        return repository.obtainAll().flatMap { response ->
            val offerList = response.offersResponseBody.flatMap { offerEntity ->
                listOf(OfferResponseBodyToOfferMapper.transform(offerEntity))
            }
            var useCaseResponse: Response<List<OfferCar>> = Response(offerList, null, null)
            Observable.just(useCaseResponse)
        }
    }

    data class ObtainAllRequest(val offset: Int?, val take: Int?) : Request
}
