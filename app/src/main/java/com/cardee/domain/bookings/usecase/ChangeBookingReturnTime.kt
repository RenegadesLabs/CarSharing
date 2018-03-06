package com.cardee.domain.bookings.usecase

import com.cardee.CardeeApp
import com.cardee.data_source.BookingDataSource
import com.cardee.data_source.BookingRepository
import com.cardee.data_source.Error
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import com.cardee.util.DateRepresentationDelegate
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*


class ChangeBookingReturnTime(threadExecutor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                              responseThread: Scheduler = AndroidSchedulers.mainThread(),
                              private val repository: BookingRepository = BookingRepository.getInstance(),
                              private val converter: DateRepresentationDelegate = DateRepresentationDelegate(CardeeApp.context))
    : UseCase<Boolean>(threadExecutor, responseThread) {


    override fun buildUseCaseObserver(request: Request): Observable<Response<Boolean>> {
        val (id, date) = request as ExtensionRequest
        val isoDate = converter.formatAsIsoDate(date)
        return Observable.create { emitter ->
            repository.extendBooking(id, isoDate, object : BookingDataSource.SimpleCallback {
                override fun onSuccess() {
                    emitter.onNext(Response(true))
                }

                override fun onError(error: Error?) {
                    emitter.onNext(Response(null, Response.SOCKET_ERROR, error?.message))
                }
            })
        }
    }

    data class ExtensionRequest(val bookingId: Int, val extensionDate: Date) : Request
}