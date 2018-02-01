package com.cardee.domain.renter.entity

import com.cardee.CardeeApp
import com.cardee.R


class FilterStringHolder(val vehicleTypeDesc: Array<String> = arrayOf(CardeeApp.context.resources.getString(R.string.vehicle_type_personal_cars_desc),
        CardeeApp.context.resources.getString(R.string.vehicle_type_private_desc),
        CardeeApp.context.resources.getString(R.string.vehicle_type_commercial_desc)),
                         val priceRangeTitles: Array<String> = arrayOf(CardeeApp.context.resources.getString(R.string.price_range_per_hour),
                                 CardeeApp.context.resources.getString(R.string.price_range_per_day)))