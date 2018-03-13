package com.cardee.domain.renter.entity.mapper

import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.FilterRequest

class ToFilterRequestMapper {
    fun transform(filter: BrowseCarsFilter): FilterRequest {
        return FilterRequest(rentType = when (filter.bookingHourly) {
            true -> "hourly"
            false -> "daily"
            else -> null
        },
                rentalPeriodBegin = filter.rentalPeriodBegin,
                rentalPeriodEnd = filter.rentalPeriodEnd,
                timePickup = filter.pickupTime,
                timeReturn = filter.returnTime,
                typeVehicleId = filter.vehicleTypeId,
                byLocation = filter.byLocation,
                latitude = if (filter.byLocation) filter.latitude else null,
                longitude = if (filter.byLocation) filter.longitude else null,
                radius = if (filter.byLocation) filter.radius else null,
                carBodyType = if (filter.bodyTypeId != 0) filter.bodyTypeId else null,
                carTransmission = if (filter.transmissionAuto && filter.transmissionManual) null else
                    if (filter.transmissionAuto) 1 else 2,
                minYears = filter.minYears,
                maxYears = if (filter.maxYears == 0) null else filter.maxYears,
                isInstantBooking = filter.instantBooking,
                isCurbsideDelivery = filter.curbsideDelivery,
                lowerPriceRange = filter.minPrice,
                upperPriceRange = if ((filter.bookingHourly == true && filter.maxPrice == 41) ||
                        (filter.bookingHourly == false && filter.maxPrice == 201) ||
                        filter.maxPrice == 0) null else filter.maxPrice,
                isFavorite = filter.favorite,
                orderBy = filter.orderBy
        )
    }
}
