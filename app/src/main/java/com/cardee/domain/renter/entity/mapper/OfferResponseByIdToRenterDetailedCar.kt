package com.cardee.domain.renter.entity.mapper

import com.cardee.data_source.remote.api.offers.response.OfferByIdCarDetails
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.owner.entity.Image
import com.cardee.domain.renter.entity.RenterDetailedCar

class OfferResponseByIdToRenterDetailedCar {

    fun transform(offerResponse: OfferByIdResponseBody?): RenterDetailedCar {
        val carDetails: OfferByIdCarDetails? = offerResponse?.carDetails
        val size: Int? = carDetails?.images?.size
        val images: Array<Image> = Array(size?: 0, {i ->
            Image(carDetails?.images?.get(i)?.imageId,
                carDetails?.images?.get(i)?.thumbnail,
                carDetails?.images?.get(i)?.link,
                carDetails?.images?.get(i)?.isPrimary) })

        return RenterDetailedCar(carDetails?.carId, carDetails?.carTitle, carDetails?.isFavorite, images, carDetails?.carYear,
                carDetails?.carPlateNumber, carDetails?.tripsCount, carDetails?.deliveryRates, carDetails?.vehicleType, carDetails?.carMake, carDetails?.carModel,
                carDetails?.seatingCapacity, carDetails?.carEngineCapacity, carDetails?.bodyType, carDetails?.carTransmission, carDetails?.longitude,
                carDetails?.latitude, carDetails?.distance, carDetails?.address, carDetails?.town, carDetails?.hideExactLocation, carDetails?.securityDepositDescription,
                carDetails?.requiredMinAge, carDetails?.requiredDrivingExp, carDetails?.carRules, carDetails?.carOtherRules, carDetails?.requireSecurityDeposit,
                carDetails?.securityDepositDescription, carDetails?.compensationExcess, carDetails?.compensationOtherGuidelines, carDetails?.addOns, carDetails?.additionalTerms,
                carDetails?.reviews, carDetails?.carConditionCleanliness, carDetails?.carComfortPerformance, carDetails?.carOwner, carDetails?.overallRentalExperience,
                offerResponse?.orderDailyDetails, offerResponse?.availableOrderDays, offerResponse?.carAvailabilityDaily, offerResponse?.carAvailabilityDailyCount, offerResponse?.orderHourlyDetails,
                offerResponse?.availableOrderHours, offerResponse?.carAvailabilityHourly, offerResponse?.carAvailabilityHourlyCount, offerResponse?.carAvailabilityTimeBegin, offerResponse?.carAvailabilityTimeEnd,
                offerResponse?.reviewCount, offerResponse?.rating, offerResponse?.owner)
    }
}