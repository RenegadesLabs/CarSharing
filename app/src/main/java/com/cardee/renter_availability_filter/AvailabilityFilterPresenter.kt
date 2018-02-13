package com.cardee.renter_availability_filter

import android.content.Context
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.usecase.GetFilter
import com.cardee.domain.renter.usecase.SaveFilter
import com.cardee.util.DateRepresentationDelegate
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import java.util.*

class AvailabilityFilterPresenter(context: Context) {

    private var filter: BrowseCarsFilter? = null
    private val getFilterUseCase = GetFilter()
    private val saveFilterUseCase = SaveFilter()
    private val delegate = DateRepresentationDelegate(context)
    var disposable: Disposable? = Disposables.empty()

    fun getFilter(callback: (BrowseCarsFilter) -> Unit) {
        filter?.let {
            callback.invoke(it)
            return
        }
        val result = getFilterUseCase.getFilter()
        filter = BrowseCarsFilter(result.vehicleTypeId, result.byLocation, result.latitude,
                result.longitude, result.radius, result.address, result.bookingHourly,
                result.rentalPeriodBegin, result.rentalPeriodEnd, result.pickupTime,
                result.returnTime, result.instantBooking, result.curbsideDelivery,
                result.bodyTypeId, result.transmissionAuto, result.transmissionManual,
                result.minYears, result.maxYears, result.minPrice, result.maxPrice, result.favorite)
        filter?.let(callback)
    }

    fun saveFilter(hourly: Boolean?, callback: () -> Unit) {
        filter?.let {
            it.bookingHourly = hourly
            saveFilterUseCase.saveFilter(it)
            callback.invoke()
        }
    }

    fun resetFilter(callback: () -> Unit) {
        filter?.let {
            it.rentalPeriodBegin = null
            it.rentalPeriodEnd = null
            it.pickupTime = null
            it.returnTime = null
        }
        callback.invoke()
    }

    fun setHourlyFilter(start: Date? = null, end: Date? = null) {
        filter?.let {
            it.bookingHourly = true
            it.rentalPeriodBegin = delegate.formatAsIsoDate(start)
            it.rentalPeriodEnd = delegate.formatAsIsoDate(end)
        }
    }

    fun setDailyFilter(start: Date? = null, end: Date? = null) {
        filter?.let {
            it.bookingHourly = false
            it.rentalPeriodBegin = delegate.formatAsIsoDate(start)
            it.rentalPeriodEnd = delegate.formatAsIsoDate(end)
            it.pickupTime = null
            it.returnTime = null
        }
    }

    fun setDailyFilterTime(pickupTime: String? = null, returnTime: String? = null) {
        filter?.let {
            it.bookingHourly = false
            it.pickupTime = delegate.formatAsIsoTime(pickupTime)
            it.returnTime = delegate.formatAsIsoTime(returnTime)
        }
    }

    fun isTimingAllowed(): Boolean {
        filter ?: return false
        filter?.bookingHourly?.let { hourly ->
            if (!hourly && filter?.rentalPeriodBegin != null && filter?.rentalPeriodEnd != null) {
                return true
            }
        }
        return false
    }

    fun doUpdate(function: (BrowseCarsFilter) -> Unit) {
        filter?.let(function)
    }
}
