package com.cardee.domain.bookings.entity

import java.util.*


data class AvailabilityState(val availableDates: List<String>, val timeStart: Date?, val timeEnd: Date?)