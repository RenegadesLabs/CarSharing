package com.cardee.util

import com.cardee.CardeeApp
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SimpleIso8601DateFormatter private constructor() {

    private val formatter: SimpleDateFormat

    init {
        val timeZone = CardeeApp.getTimeZone()
        val symbols = DateFormatSymbols(Locale.US)
        symbols.amPmStrings = arrayOf("am", "pm")
        formatter = SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.US)
        formatter.timeZone = timeZone
        formatter.dateFormatSymbols = symbols
    }

    companion object {
        private const val ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"

        @Volatile
        private var instance: SimpleIso8601DateFormatter? = null

        fun useInstance(): SimpleIso8601DateFormatter {
            if (instance == null) {
                instance = SimpleIso8601DateFormatter()
            }
            return instance!!
        }
    }

    fun format(isoDate: String, format: String): String? {
        return parseDate(isoDate, ISO8601_DATE_FORMAT)?.let { date ->
            formatter.applyPattern(format)
            return@let formatter.format(date)
        }
    }

    fun formatToIso(dateString: String, format: String): String? {
        return parseDate(dateString, format)?.let { date ->
            formatter.applyPattern(ISO8601_DATE_FORMAT)
            return@let formatter.format(date)
        }
    }

    private fun parseDate(date: String, format: String): Date? {
        try {
            formatter.applyPattern(format)
            return formatter.parse(date)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return null
    }


}
