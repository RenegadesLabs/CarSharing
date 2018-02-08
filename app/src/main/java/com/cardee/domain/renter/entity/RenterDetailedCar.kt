package com.cardee.domain.renter.entity

import com.cardee.data_source.remote.api.cars.response.entity.OrderDailyDetailsEntity
import com.cardee.data_source.remote.api.cars.response.entity.OrderHourlyDetailsEntity
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity
import com.cardee.data_source.remote.api.common.entity.OfferCarRule
import com.cardee.data_source.remote.api.offers.response.entity.OfferCarReview
import com.cardee.domain.owner.entity.Image


class RenterDetailedCar(val carId: Int?,
                        val carTitle: String?,
                        val favorite: Boolean?,
                        val images: Array<Image>?,
                        val year: String?,
                        val licensePlateNumber: String?,
                        val deliveryRates: DeliveryRatesEntity?,
                        val vehicleType: String?,
                        val carMake: String?,
                        val carModel: String?,
                        val seatingCapacity: Int?,
                        val carEngineCapacity: String?,
                        val bodyType: String?,
                        val carTransmission: String?,
                        val longitude: Double?,
                        val latitude: Double?,
                        val distance: Int?,
                        val address: String?,
                        val town: String?,
                        val hideExactLocation: Boolean?,
                        val description: String?,
                        val requiredMinAge: String?,
                        val requiredDrivingExp: String?,
                        val carRules: List<OfferCarRule>?,
                        val carOtherRules: String?,
                        val requireSecurityDeposit: Boolean?,
                        val securityDepositDescription: String?,
                        val compensationExcess: String?,
                        val compensationOtherGuidelines: String?,
                        val addOns: String?,
                        val additionalTerms: String?,
                        val reviews: List<OfferCarReview>?,
                        val carConditionCleanliness: Float?,
                        val carComfortPerformance: Float?,
                        val carOwner: Float?,
                        val overallRentalExperience: Float?,
                        val orderDailyDetails: OrderDailyDetailsEntity?,
                        val availableOrderDays: Boolean?,
                        val carAvailabilityDaily: Array<String>?,
                        val carAvailabilityDailyCount: Int?,
                        val orderHourlyDetails: OrderHourlyDetailsEntity?,
                        val availableOrderHours: Boolean?,
                        val carAvailabilityHourly: Array<String>?,
                        val carAvailabilityHourlyCount: Int?,
                        val carAvailabilityTimeBegin: String?,
                        val carAvailabilityTimeEnd: String?,
                        val reviewCount: Int?,
                        val rating: Float?)