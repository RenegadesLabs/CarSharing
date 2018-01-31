package com.cardee.util

import android.content.Context


class AvailabilityFromFilterDelegate(context: Context) {

    enum class Time {
        BEGIN, END
    }

    companion object {
        private val iso8601DatePattern: String = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private val isoDatePattern: String = "HH:mm:ssZZZZZ"
        private val shortDatePattern: String = "d MMM"
        private val shortTimePattern: String = "ha"
    }

    private var anytime: String = "Anytime"
    private var daily: String = "Book Daily"
    private var hourly: String = "Book Hourly"
    private var noValue: String = "Not Specified"

}