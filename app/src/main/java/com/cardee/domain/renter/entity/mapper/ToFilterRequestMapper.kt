package com.cardee.domain.renter.entity.mapper

import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.FilterRequest

class ToFilterRequestMapper {
    fun transform(filter: BrowseCarsFilter): FilterRequest {
        return FilterRequest(rentType = if (filter.bookingHourly) "hourly" else "daily",
                typeVehicleId = filter.vehicleTypeId,
                carBodyType = if (filter.bodyTypeId != 0) filter.bodyTypeId else null,
                carTransmission = if (filter.transmissionAuto && filter.transmissionManual) null else
                    if (filter.transmissionAuto) 1 else 2,
                minYears = filter.minYears,
                maxYears = filter.maxYears,
                isInstantBooking = filter.instantBooking,
                isCurbsideDelivery = filter.curbsideDelivery,
                lowerPriceRange = filter.minPrice,
                upperPriceRange = if ((filter.bookingHourly && filter.maxPrice == 41) ||
                        (!filter.bookingHourly && filter.maxPrice == 201)) null else filter.maxPrice
        )
    }
}
