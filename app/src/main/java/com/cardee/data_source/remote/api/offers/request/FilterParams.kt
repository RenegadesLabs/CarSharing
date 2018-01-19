package com.cardee.data_source.remote.api.offers.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterParams(@Expose @SerializedName("rent_type") val rentType: RentType?,
                        @Expose @SerializedName("rental_period_begin") val rentalPeriodBegin: String?,
                        @Expose @SerializedName("rental_period_end") val rentalPeriodEnd: String?,
                        @Expose @SerializedName("time_pickup") val timePickup: String?,
                        @Expose @SerializedName("time_return") val timeReturn: String?,
                        @Expose @SerializedName("type_vehicle_id") val vehicleTypeId: Int?,
                        @Expose @SerializedName("car_body_type") val bodyTypeId: Int?,
                        @Expose @SerializedName("car_transmission") val transmissionTypeId: Int?,
                        @Expose @SerializedName("min_years") val minYear: Int?,
                        @Expose @SerializedName("max_years") val maxYear: Int?,
                        @Expose @SerializedName("is_favorite") val favorite: Boolean?,
                        @Expose @SerializedName("by_location") val byLocation: Boolean?,
                        @Expose @SerializedName("latitude") val latitude: Double?,
                        @Expose @SerializedName("longitude") val longitude: Double?,
                        @Expose @SerializedName("radius") val radius: Int?,
                        @Expose @SerializedName("is_instant_booking") val isInstantBooking: Boolean?,
                        @Expose @SerializedName("is_curbside_delivery") val isCurbsideDelivery: Boolean?,
                        @Expose @SerializedName("lower_price_range") val lowerPrice: Int?,
                        @Expose @SerializedName("upper_price_range") val upperPrice: Int?,
                        @Expose @SerializedName("order_by") val orderBy: OrderType?,
                        @Expose @SerializedName("search_criteria") val searchCriteria: String?)

enum class RentType {
    DAILY, HOURLY;

    override fun toString(): String {
        return name.toLowerCase()
    }
}

enum class OrderType {
    DISTANCE, PRICE, RATING, POPULARITY;

    override fun toString(): String {
        return name.toLowerCase()
    }
}
