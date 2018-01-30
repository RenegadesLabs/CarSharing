package com.cardee.data_source.remote.api.offers.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterParams(@Expose @SerializedName("rent_type") val rentType: RentType? = null,
                        @Expose @SerializedName("rental_period_begin") val rentalPeriodBegin: String? = null,
                        @Expose @SerializedName("rental_period_end") val rentalPeriodEnd: String? = null,
                        @Expose @SerializedName("time_pickup") val timePickup: String? = null,
                        @Expose @SerializedName("time_return") val timeReturn: String? = null,
                        @Expose @SerializedName("type_vehicle_id") val vehicleTypeId: Int? = null,
                        @Expose @SerializedName("car_body_type") val bodyTypeId: Int? = null,
                        @Expose @SerializedName("car_transmission") val transmissionTypeId: Int? = null,
                        @Expose @SerializedName("min_years") val minYear: Int? = null,
                        @Expose @SerializedName("max_years") val maxYear: Int? = null,
                        @Expose @SerializedName("is_favorite") val favorite: Boolean? = null,
                        @Expose @SerializedName("by_location") val byLocation: Boolean? = null,
                        @Expose @SerializedName("latitude") val latitude: Double? = null,
                        @Expose @SerializedName("longitude") val longitude: Double? = null,
                        @Expose @SerializedName("radius") val radius: Int? = null,
                        @Expose @SerializedName("is_instant_booking") val isInstantBooking: Boolean? = null,
                        @Expose @SerializedName("is_curbside_delivery") val isCurbsideDelivery: Boolean? = null,
                        @Expose @SerializedName("lower_price_range") val lowerPrice: Int? = null,
                        @Expose @SerializedName("upper_price_range") val upperPrice: Int? = null,
                        @Expose @SerializedName("order_by") val orderBy: OrderType? = null,
                        @Expose @SerializedName("search_criteria") val searchCriteria: String? = null)

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
