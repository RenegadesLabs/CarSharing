package com.cardee.data_source.remote.api.offers.response

import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity
import com.cardee.data_source.remote.api.common.entity.ImageEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Offer(@Expose @SerializedName("car_details") val carDetails: OfferCarDetails?,
                 @Expose @SerializedName("owner") val owner: OfferOwner?,
                 @Expose @SerializedName("rating_cnt") val ratingCount: Int?,
                 @Expose @SerializedName("rating") val rating: Int?,
                 @Expose @SerializedName("order_details") val orderDetails: OfferOrderDetails?)


data class OfferCarDetails(@Expose @SerializedName("car_id") val carId: Int?,
                           @Expose @SerializedName("car_title") val carTitle: String?,
                           @Expose @SerializedName("is_favorite") val isFavorite: Boolean?,
                           @Expose @SerializedName("distance") val distance: Int?,
                           @Expose @SerializedName("latitude") val latitude: Double?,
                           @Expose @SerializedName("longitude") val longitude: Double?,
                           @Expose @SerializedName("vehicle_type") val vehicleType: String?,
                           @Expose @SerializedName("car_body_type") val bodyType: String?,
                           @Expose @SerializedName("year_manufacture") val carYear: Int?,
                           @Expose @SerializedName("license_plate_number") val carPlateNumber: String?,
                           @Expose @SerializedName("seating_capacity") val seatingCapacity: Int?,
                           @Expose @SerializedName("images") val images: List<ImageEntity>?)


data class OfferOwner(@Expose @SerializedName("profile_id") val ownerId: Int?,
                      @Expose @SerializedName("profile_photo") val photoUrl: String?,
                      @Expose @SerializedName("name") val name: String?)


data class OfferOrderDetails(@Expose @SerializedName("is_instant_booking") val isInstantBooking: Boolean?,
                             @Expose @SerializedName("is_curbside_delivery") val isCurbsideDelivery: Boolean?,
                             @Expose @SerializedName("is_accept_cash") val isAccept: Boolean?,
                             @Expose @SerializedName("fuel_policy") val fuelPolicy: FuelPolicyEntity?,
                             @Expose @SerializedName("amnt_rate_first") val rateFirst: Int?,
                             @Expose @SerializedName("amnt_rate_second") val rateSecond: Int?,
                             @Expose @SerializedName("amnt_discount_first") val discountFirst: Int?,
                             @Expose @SerializedName("amnt_discount_second") val discountSecond: Int?,
                             @Expose @SerializedName("min_rental_duration") val minRentalDuration: Int?,
                             @Expose @SerializedName("time_pickup") val pickupTime: String?,
                             @Expose @SerializedName("time_return") val returnTime: String?)
