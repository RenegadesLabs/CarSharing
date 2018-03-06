package com.cardee.domain.bookings.usecase

import com.cardee.CardeeApp
import com.cardee.data_source.*
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.bookings.entity.AvailabilityState
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import com.cardee.util.DateRepresentationDelegate
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class GetFullBookingAvailability(executor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                                 responseThread: Scheduler = AndroidSchedulers.mainThread(),
                                 private val carsRepository: RenterCarsRepository = RenterCarsRepository.getInstance(),
                                 private val bookingsRepository: BookingRepository = BookingRepository.getInstance())
    : UseCase<AvailabilityState>(executor, responseThread) {

    private val timeFormatter: DateRepresentationDelegate
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    init {
        timeFormatter = DateRepresentationDelegate(CardeeApp.context)
        dateFormatter.timeZone = CardeeApp.getTimeZone()
    }

    override fun buildUseCaseObserver(request: Request): Observable<Response<AvailabilityState>> {
        val (bookingId, hourly) = request as BookingAvailabilityRequest

        return Observable.create<Response<AvailabilityState>> { emitter ->
            bookingsRepository.obtainBookingById(bookingId, object : BookingDataSource.BookingCallback {
                override fun onSuccess(bookingEntity: BookingEntity?) {
                    val carId = bookingEntity?.car?.carId
                    if (carId == null) {
                        emitter.onNext(Response(null, Response.SOCKET_ERROR, "Bad response"))
                        return
                    }
                    val timeBegin = timeFormatter.convertDateToDate(bookingEntity.timeBegin)
                    val timeEnd = timeFormatter.convertDateToDate(bookingEntity.timeEnd)

                    carsRepository.getOfferById(carId, object : RenterCarsDataSource.OfferCallback {
                        override fun onSuccess(response: OfferByIdResponseBody?) {
                            if (response == null) {
                                emitter.onNext(Response(null, Response.SOCKET_ERROR, "Bad response"))
                                return
                            }
                            val availabilityRange = when (hourly) {
                                true -> response.carAvailabilityHourly
                                false -> response.carAvailabilityDaily
                            }?.flatMap { dateString ->
                                try {
                                    val date = dateFormatter.parse(dateString)
                                    listOf(date)
                                } catch (ex: ParseException) {
                                    emptyList<Date>()
                                }
                            }?.toTypedArray()
                            emitter.onNext(Response(AvailabilityState(
                                    availabilityRange ?: emptyArray(), timeBegin, timeEnd)))
                        }

                        override fun onError(error: Error?) {
                            emitter.onNext(Response(null, Response.SOCKET_ERROR, error?.message))
                        }
                    })
                }

                override fun onError(error: Error?) {
                    emitter.onNext(Response(null, Response.SOCKET_ERROR, error?.message))
                }
            })
        }
    }

    data class BookingAvailabilityRequest(val bookingId: Int, val hourly: Boolean) : Request
}