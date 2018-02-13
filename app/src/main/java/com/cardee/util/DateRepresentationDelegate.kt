package com.cardee.util

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.cardee.CardeeApp
import com.cardee.R
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateRepresentationDelegate(context: Context) {

    companion object {
        private val LOG_TAG = DateRepresentationDelegate::class.java.simpleName
        private const val ISO_8601_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private const val ISO_8601_TIME_PATTERN = "HH:mm:ssZZZZZ"
        private const val MONTH_DAY_YEAR_HOUR_MINUTE_PATTERN = "d MMM yyyy, h:mma"
        private const val MONTH_DAY_YEAR_HOUR_PATTERN = "d\u00a0MMM yyyy,\u00a0ha"
        private const val MONTH_DAY_YEAR_PATTERN = "d MMM, yyyy"
        private const val MONTH_DAY_HOUR_PATTERN = "d\u00a0MMM,\u00a0ha"
        private const val HOUR_PATTERN = "hha"
    }

    private val availabilityPickupPrefix: String
    private val availabilityPickupSuffix: String
    private val availabilityReturnPrefix: String
    private val availabilityReturnSuffix: String
    private val availabilityHourlyPrefix: String
    private val reviewDatePrefix: String

    private val formatter: SimpleDateFormat
    private val calendar: Calendar
    private val startZeroRegex: Regex

    init {
        formatter = SimpleDateFormat(ISO_8601_DATE_TIME_PATTERN, Locale.US)
        formatter.timeZone = CardeeApp.getTimeZone()
        val symbols = DateFormatSymbols(Locale.US)
        symbols.amPmStrings = arrayOf("am", "pm")
        formatter.dateFormatSymbols = symbols
        calendar = Calendar.getInstance(CardeeApp.getTimeZone())
        startZeroRegex = Regex("\\s0")

        availabilityPickupPrefix = context.getString(R.string.availability_pickup_prefix)
        availabilityPickupSuffix = context.getString(R.string.availability_pickup_suffix)
        availabilityReturnPrefix = context.getString(R.string.availability_return_prefix)
        availabilityReturnSuffix = context.getString(R.string.availability_return_suffix)
        availabilityHourlyPrefix = context.getString(R.string.hourly_timing_dialog_title)
        reviewDatePrefix = context.getString(R.string.renter_car_details_review_date_prefix)
    }

    fun onSetTimeRangeString(view: TextView, timeStart: String?, timeEnd: String?) {
        if (timeStart == null || timeEnd == null) {
            view.text = availabilityHourlyPrefix
            return
        }
        val startString = convert(timeStart, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val endString = convert(timeEnd, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val rangeString = "$availabilityHourlyPrefix $startString - $endString"
        view.text = rangeString
    }

    fun onSetPickupReturnTime(view: TextView, isoTimePickup: String, isoTimeReturn: String) {
        val pickupString = convert(isoTimePickup, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val returnString = convert(isoTimeReturn, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val rangeString = "$pickupString\n$returnString"
        view.text = rangeString
    }

    fun onSetPickupTime(view: TextView, isoTime: String?) {
        isoTime ?: return
        val timeString = convert(isoTime, ISO_8601_TIME_PATTERN, HOUR_PATTERN)
        val pickupTimeString = "$availabilityPickupPrefix $timeString $availabilityPickupSuffix"
        view.text = pickupTimeString
    }

    fun onSetReturnTime(view: TextView, isoTime: String?) {
        isoTime ?: return
        val timeString = convert(isoTime, ISO_8601_TIME_PATTERN, HOUR_PATTERN)
        val returnTimeString = "$availabilityReturnPrefix $timeString $availabilityReturnSuffix"
        view.text = returnTimeString
    }

    fun onSetMonthDayYear(view: TextView, isoDate: String) {
        val date = convert(isoDate, ISO_8601_DATE_TIME_PATTERN, MONTH_DAY_YEAR_PATTERN) ?: return
        val dateString = "$reviewDatePrefix $date"
        view.text = dateString
    }

    fun formatMonthDayYearHour(isoDate: String?): String? {
        isoDate ?: return null
        val dateString = convert(isoDate, ISO_8601_DATE_TIME_PATTERN, MONTH_DAY_YEAR_HOUR_PATTERN)
                ?: return null
        return dropStartZero(dateString)
    }

    fun formatMonthDayYearHourMinute(isoDate: String?): String? {
        isoDate ?: return null
        val dateString = convert(isoDate, ISO_8601_DATE_TIME_PATTERN, MONTH_DAY_YEAR_HOUR_MINUTE_PATTERN)
                ?: return null
        return dropStartZero(dateString)
    }

    fun formatAsIsoDate(date: Date?): String? {
        date ?: return null
        formatter.applyPattern(ISO_8601_DATE_TIME_PATTERN)
        return formatter.format(date)
    }

    fun formatAsIsoTime(time: String?): String? {
        time ?: return null
        return convert(time, HOUR_PATTERN, ISO_8601_TIME_PATTERN)
    }

    fun formatAsIsoTime(hour: Int): String? {
        calendar.clear()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        return convertTo(calendar.time, ISO_8601_TIME_PATTERN)
    }

    fun formatHour(isoTime: String?): String? {
        isoTime ?: return null
        return convert(isoTime, ISO_8601_TIME_PATTERN, HOUR_PATTERN)
    }

    fun formatMonthDayHour(isoDate: String?): String? {
        isoDate ?: return null
        val timeString = convert(isoDate, ISO_8601_DATE_TIME_PATTERN, MONTH_DAY_HOUR_PATTERN)
                ?: return null
        return dropStartZero(timeString)
    }

    fun formatMonthDayHour(isoDate: String?, dayOffset: Int): String? {
        isoDate ?: return null
        try {
            val date = parseDate(isoDate, ISO_8601_DATE_TIME_PATTERN)
            calendar.time = date
            calendar.add(Calendar.DATE, dayOffset)
            return convertTo(calendar.time, MONTH_DAY_HOUR_PATTERN)
        } catch (ex: ParseException) {
            Log.e(LOG_TAG, ex.message)
        }
        return null
    }

    fun formatMonthDayHour(date: Date?): String? {
        date ?: return null
        return convertTo(date, MONTH_DAY_HOUR_PATTERN)
    }

    fun convertTimeToDate(time: String?): Date? {
        time ?: return null
        try {
            return parseDate(time, ISO_8601_TIME_PATTERN)
        } catch (ex: ParseException) {
            Log.e(LOG_TAG, ex.message)
        }
        return null
    }

    fun convertDateToDate(date: String?): Date? {
        date ?: return null
        try {
            return parseDate(date, ISO_8601_DATE_TIME_PATTERN)
        } catch (ex: ParseException) {
            Log.e(LOG_TAG, ex.message)
        }
        return null
    }

    private fun convert(dateString: String, formatFrom: String, formatTo: String): String? {
        try {
            val date = parseDate(dateString, formatFrom)
            return convertTo(date, formatTo)
        } catch (ex: ParseException) {
            Log.e(LOG_TAG, ex.message)
        }
        return null
    }

    private fun parseDate(dateString: String, formatFrom: String): Date {
        formatter.applyPattern(formatFrom)
        return formatter.parse(dateString)
    }

    private fun convertTo(date: Date, formatTo: String): String? {
        formatter.applyPattern(formatTo)
        return formatter.format(date)
    }

    private fun dropStartZero(time: String) = time.replace(startZeroRegex, "")
}
