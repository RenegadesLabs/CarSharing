package com.cardee.domain.renter.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterRequest(@SerializedName("rent_type")
                         @Expose
                         private var rentType: String? = null,
                         @SerializedName("rental_period_begin")
                         @Expose
                         private var rentalPeriodBegin: String? = null,
                         @SerializedName("rental_period_end")
                         @Expose
                         private var rentalPeriodEnd: String? = null,
                         @SerializedName("time_pickup")
                         @Expose
                         private var timePickup: String? = null,
                         @SerializedName("time_return")
                         @Expose
                         private var timeReturn: String? = null,
                         @SerializedName("type_vehicle_id")
                         @Expose
                         private var typeVehicleId: Int? = null,
                         @SerializedName("car_body_type")
                         @Expose
                         private var carBodyType: Int? = null,
                         @SerializedName("car_transmission")
                         @Expose
                         private var carTransmission: Int? = null,
                         @SerializedName("min_years")
                         @Expose
                         private var minYears: Int? = null,
                         @SerializedName("max_years")
                         @Expose
                         private var maxYears: Int? = null,
                         @SerializedName("is_favorite")
                         @Expose
                         private var isFavorite: Boolean? = null,
                         @SerializedName("by_location")
                         @Expose
                         private var byLocation: Boolean? = null,
                         @SerializedName("latitude")
                         @Expose
                         private var latitude: Double? = null,
                         @SerializedName("longitude")
                         @Expose
                         private var longitude: Double? = null,
                         @SerializedName("radius")
                         @Expose
                         private var radius: Int? = null,
                         @SerializedName("is_instant_booking")
                         @Expose
                         private var isInstantBooking: Boolean? = null,
                         @SerializedName("is_curbside_delivery")
                         @Expose
                         private var isCurbsideDelivery: Boolean? = null,
                         @SerializedName("lower_price_range")
                         @Expose
                         private var lowerPriceRange: Int? = null,
                         @SerializedName("upper_price_range")
                         @Expose
                         private var upperPriceRange: Int? = null,
                         @SerializedName("order_by")
                         @Expose
                         private var orderBy: String? = null,
                         @SerializedName("search_criteria")
                         @Expose
                         private var searchCriteria: String? = null)