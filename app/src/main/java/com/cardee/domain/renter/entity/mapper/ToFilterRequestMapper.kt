package com.cardee.domain.renter.entity.mapper

import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.FilterRequest

class ToFilterRequestMapper {
    fun transform(filter: BrowseCarsFilter): FilterRequest {
        return FilterRequest(rentType = if (filter.bookingHourly) "hourly" else "daily",
                typeVehicleId = filter.vehicleTypeId,
                carBodyType = filter.bodyTypeId,
                carTransmission = if (filter.transmissionAuto && filter.transmissionManual) 0 else
                    if (filter.transmissionAuto) 1 else 0,
                minYears = filter.minYears,
                maxYears = filter.maxYears,
                isInstantBooking = filter.instantBooking,
                isCurbsideDelivery = filter.curbsideDelivery,
                lowerPriceRange = filter.minPrice,
                upperPriceRange = filter.maxPrice
        )
    }
}
