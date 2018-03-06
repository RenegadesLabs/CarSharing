package com.cardee.domain.bookings.entity

import java.util.*


data class AvailabilityState(val availableDates: Array<Date>, val timeStart: Date?, val timeEnd: Date?)