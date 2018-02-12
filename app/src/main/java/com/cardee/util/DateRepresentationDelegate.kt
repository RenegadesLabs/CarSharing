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
        private const val WEEK_DAY_MONTH_DAY_PATTERN = "EEE, d\u00a0MMM"
        private const val MONTH_DAY_YEAR_HOUR_MINUTE_PATTERN = "d MMM yyyy, h:mma"
        private const val MONTH_DAY_YEAR_HOUR_PATTERN = "d\u00a0MMM yyyy,\u00a0ha"
        private const val MONTH_DAY_YEAR_PATTERN = "d MMM, yyyy"
        private const val MONTH_DAY_HOUR_PATTERN = "d\u00a0MMM,\u00a0ha"
        private const val MONTH_DAY_PATTERN = "d\u00a0MMM"
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
        val symbols = DateFormatSymbols()
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

    fun onSetPickupReturnTime(view: TextView, isoTimePickup: String, isoTimeReturn: String) {
        val pickupString = convert(isoTimePickup, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val returnString = convert(isoTimeReturn, ISO_8601_TIME_PATTERN, HOUR_PATTERN) ?: return
        val rangeString = "$pickupString\n$returnString"
        view.text = rangeString
    }

    fun onSetMonthDayYear(view: TextView, isoDate: String) {
        val date = convert(isoDate, ISO_8601_DATE_TIME_PATTERN, MONTH_DAY_YEAR_PATTERN) ?: return
        val dateString = "$reviewDatePrefix $date"
        view.text = dateString
    }



    private fun convert(dateString: String, formatFrom: String, formatTo: String): String? {
        try {
            formatter.applyPattern(formatFrom)
            val date = formatter.parse(dateString)
            formatter.applyPattern(formatTo)
            return formatter.format(date)
        } catch (ex: ParseException) {
            Log.e(LOG_TAG, ex.message)
        }
        return null
    }

    private fun reduceStartZero(time: String) = time.replace(startZeroRegex, "")
}
