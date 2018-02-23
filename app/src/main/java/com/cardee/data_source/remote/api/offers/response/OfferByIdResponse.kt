package com.cardee.data_source.remote.api.offers.response

import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.cars.response.entity.OrderDailyDetailsEntity
import com.cardee.data_source.remote.api.cars.response.entity.OrderHourlyDetailsEntity
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity
import com.cardee.data_source.remote.api.common.entity.ImageEntity
import com.cardee.data_source.remote.api.common.entity.OfferCarOwner
import com.cardee.data_source.remote.api.common.entity.OfferCarRule
import com.cardee.data_source.remote.api.offers.response.entity.OfferCarReview
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferByIdResponse(@Expose @SerializedName("data") val offerResponse: OfferByIdResponseBody?) : BaseResponse()

data class OfferByIdResponseBody(@Expose @SerializedName("car_details") val carDetails: OfferByIdCarDetails? = null,
                                 @Expose @SerializedName("order_daily_details") val orderDailyDetails: OrderDailyDetailsEntity? = null,
                                 @Expose @SerializedName("is_available_order_days") val availableOrderDays: Boolean? = null,
                                 @Expose @SerializedName("car_availability_daily") val carAvailabilityDaily: Array<String>? = null,
                                 @Expose @SerializedName("car_availability_daily_cnt") val carAvailabilityDailyCount: Int? = null,
                                 @Expose @SerializedName("owner") val owner: OfferCarOwner? = null,
                                 @Expose @SerializedName("order_hourly_details") val orderHourlyDetails: OrderHourlyDetailsEntity? = null,
                                 @Expose @SerializedName("is_available_order_hours") val availableOrderHours: Boolean? = null,
                                 @Expose @SerializedName("car_availability_hourly") val carAvailabilityHourly: Array<String>? = null,
                                 @Expose @SerializedName("car_availability_hourly_cnt") val carAvailabilityHourlyCount: Int? = null,
                                 @Expose @SerializedName("car_availability_time_begin") val carAvailabilityTimeBegin: String? = null,
                                 @Expose @SerializedName("car_availability_time_end") val carAvailabilityTimeEnd: String? = null,
                                 @Expose @SerializedName("review_cnt") val reviewCount: Int? = null)

data class OfferByIdCarDetails(@Expose @SerializedName("car_id") val carId: Int?,
                               @Expose @SerializedName("car_title") val carTitle: String?,
                               @Expose @SerializedName("is_favorite") val isFavorite: Boolean?,
                               @Expose @SerializedName("images") val images: List<ImageEntity>?,
                               @Expose @SerializedName("year_manufacture") val carYear: String?,
                               @Expose @SerializedName("license_plate_number") val carPlateNumber: String?,
                               @Expose @SerializedName("trips_cnt") val tripsCount: Int?,
                               @Expose @SerializedName("delivery_rates") val deliveryRates: DeliveryRatesEntity?,
                               @Expose @SerializedName("vehicle_type") val vehicleType: String?,
                               @Expose @SerializedName("car_make") val carMake: String?,
                               @Expose @SerializedName("car_model") val carModel: String?,
                               @Expose @SerializedName("seating_capacity") val seatingCapacity: Int?,
                               @Expose @SerializedName("car_engine_capacity") val carEngineCapacity: String?,
                               @Expose @SerializedName("car_body_type") val bodyType: String?,
                               @Expose @SerializedName("car_transmission") val carTransmission: String?,
                               @Expose @SerializedName("longitude") val longitude: Double?,
                               @Expose @SerializedName("latitude") val latitude: Double?,
                               @Expose @SerializedName("distance") val distance: Int?,
                               @Expose @SerializedName("address") val address: String?,
                               @Expose @SerializedName("town") val town: String?,
                               @Expose @SerializedName("is_hide_exact_location") val hideExactLocation: Boolean?,
                               @Expose @SerializedName("description") val description: String?,
                               @Expose @SerializedName("req_min_age") val requiredMinAge: Int?,
                               @Expose @SerializedName("req_max_age") val requiredMaxAge: Int?,
                               @Expose @SerializedName("req_dr_exp") val requiredDrivingExp: Int?,
                               @Expose @SerializedName("car_rules") val carRules: List<OfferCarRule>?,
                               @Expose @SerializedName("car_other_rules") val carOtherRules: String?,
                               @Expose @SerializedName("is_req_security_deposit") val requireSecurityDeposit: Boolean?,
                               @Expose @SerializedName("security_deposit_description") val securityDepositDescription: String?,
                               @Expose @SerializedName("compensation_excess") val compensationExcess: String?,
                               @Expose @SerializedName("compensation_other_guidelines") val compensationOtherGuidelines: String?,
                               @Expose @SerializedName("add_ons") val addOns: String?,
                               @Expose @SerializedName("additional_terms") val additionalTerms: String?,
                               @Expose @SerializedName("reviews") val reviews: List<OfferCarReview>?,
                               @Expose @SerializedName("car_condition_cleanliness") val carConditionCleanliness: Float?,
                               @Expose @SerializedName("car_comfort_performance") val carComfortPerformance: Float?,
                               @Expose @SerializedName("car_owner") val carOwner: Float?,
                               @Expose @SerializedName("overall_rental_experience") val overallRentalExperience: Float?,
                               @Expose @SerializedName("rating") val rating: Float?)