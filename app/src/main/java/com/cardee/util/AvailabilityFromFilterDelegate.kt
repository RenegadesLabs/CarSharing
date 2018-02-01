package com.cardee.util

import android.content.Context
import android.widget.NumberPicker
import android.widget.TextView
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.renter_availability_filter.CalendarAdapter
import com.cardee.renter_availability_filter.TimePickerAdapter
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AvailabilityFromFilterDelegate(context: Context) {

    companion object {
        private val ISO_8601_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private val ISO_DATE_PATTERN: String = "HH:mm:ssZZZZZ"
        private val SHORT_DATE_PATTERN: String = "d MMM"
        private val SHORT_TIME_PATTERN: String = "ha"
        private val TITLE_PATTERN: String = "d MMM, ha"
    }

    private var anytime: String = "Anytime"
    private var daily: String = "Book Daily"
    private var hourly: String = "Book Hourly"
    private var noValue: String = "Not Specified"
    private val timePattern = Regex(", \\d{1,2}[ap]m$")
    private val datePattern = Regex("^\\d{1,2}\\s+\\w+$")
    private val datePrefixPattern = Regex("^\\d{1,2}\\s+\\w+")
    private val titlePattern = Regex("^(\\d{1,2}\\s+\\w+),\\s(\\d{1,2}[ap]m)$")

    private val iso8601OutDateFormatter: SimpleDateFormat
    private val iso8601InDateFormatter: SimpleDateFormat
    private val isoOutDateFormatter: SimpleDateFormat
    private val isoInDateFormatter: SimpleDateFormat
    private val shortDateFormatter: SimpleDateFormat
    private val shortTimeFormatter: SimpleDateFormat
    private val titleFormatter: SimpleDateFormat

    init {
        val timeZone = TimeZone.getTimeZone("GMT+08:00")
        val symbols = DateFormatSymbols(Locale.US)
        symbols.amPmStrings = arrayOf("am", "pm")
        iso8601OutDateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.US)
        iso8601InDateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.US)
        isoOutDateFormatter = SimpleDateFormat(ISO_DATE_PATTERN, Locale.US)
        isoInDateFormatter = SimpleDateFormat(ISO_DATE_PATTERN, Locale.US)
        shortDateFormatter = SimpleDateFormat(SHORT_DATE_PATTERN, Locale.US)
        shortTimeFormatter = SimpleDateFormat(SHORT_TIME_PATTERN, Locale.US)
        titleFormatter = SimpleDateFormat(TITLE_PATTERN, Locale.US)
        iso8601OutDateFormatter.timeZone = timeZone
        isoInDateFormatter.timeZone = timeZone
        shortTimeFormatter.dateFormatSymbols = symbols
        titleFormatter.dateFormatSymbols = symbols
    }

    fun onInitCalendarSelection(calendarAdapter: CalendarAdapter, isoDateStart: String, isoDateEnd: String) {
        try {
            val dateStart = iso8601InDateFormatter.parse(isoDateStart)
            val dateEnd = iso8601InDateFormatter.parse(isoDateEnd)
            calendarAdapter.setRange(dateStart, dateEnd)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
    }

    fun onInitPickerSelection(pickerAdapter: TimePickerAdapter, isoDateStart: String, isoDateEnd: String) {
        try {
            val dateStart = iso8601InDateFormatter.parse(isoDateStart)
            val dateEnd = iso8601InDateFormatter.parse(isoDateEnd)
            pickerAdapter.setRange(dateStart, dateEnd)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
    }

    fun onInitTimingSelection(picker: NumberPicker, isoTime: String, values: Array<String?>) {
        if (picker.maxValue != values.size - 1) {
            throw IllegalArgumentException("NumberPicker and Array size mismatch")
        }
        try {
            val date = isoInDateFormatter.parse(isoTime)
            val timeString = shortTimeFormatter.format(date).toLowerCase()
            for (i in 0 until values.size) {
                if (timeString == values[i]) {
                    picker.value = i
                    break
                }
            }
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
    }

    fun onSetTitlesFromFilter(startView: TextView, endView: TextView, filter: BrowseCarsFilter) {
        if (filter.rentalPeriodBegin == null || filter.rentalPeriodEnd == null) {
            startView.text = noValue
            endView.text = noValue
            return
        }
        when (filter.bookingHourly) {
            false -> setDailyTitlesFromFilter(startView, endView, filter)
            true -> {
                setHourlyTitle(startView, filter.rentalPeriodBegin!!)
                setHourlyTitle(endView, filter.rentalPeriodEnd!!)
            }
        }
    }

    private fun setHourlyTitle(view: TextView, time: String) {
        val date = iso8601InDateFormatter.parse(time)

        onSetTitleFromTime(view, date)
    }

    private fun setDailyTitlesFromFilter(startView: TextView, endView: TextView, filter: BrowseCarsFilter) {
        try {
            val startDate = iso8601InDateFormatter.parse(filter.rentalPeriodBegin)
            val endDate = iso8601InDateFormatter.parse(filter.rentalPeriodEnd)
            onSetTitleFromDate(startView, startDate)
            onSetTitleFromDate(endView, endDate)
        } catch (ex: ParseException) {
            startView.text = noValue
            endView.text = noValue
        }
    }

    fun onSetTitleFromDate(view: TextView, date: Date?) {
        if (date == null) {
            view.text = noValue
            return
        }
        val previous = view.text.toString()
        if (titlePattern.matches(previous)) {
            view.text = replaceDate(previous, date)
        } else {
            view.text = shortDateFormatter.format(date)
        }
    }

    fun onSetTitleFromTime(view: TextView, date: Date?) {
        val dateString = titleFormatter.format(date)
        view.text = dateString
    }

    fun onSetTiming(view: TextView, time: String?) {
        val timing = time ?: ""
        val previous = view.text.toString()
        if (titlePattern.matches(previous)) {
            view.text = replaceTiming(previous, timing)
        } else if (datePattern.matches(previous)) {
            view.text = concatTiming(previous, timing)
        } else throw IllegalStateException("Illegal date string value")
    }

    private fun replaceDate(dateString: String, date: Date): String {
        val datePrefix = shortDateFormatter.format(date)
        return dateString.replace(datePrefixPattern, datePrefix)
    }

    private fun concatTiming(dateString: String, timeString: String): String {
        if (!timeString.isBlank()) {
            return "${dateString}, ${timeString}"
        }
        return dateString
    }

    private fun replaceTiming(dateString: String, timeString: String): String {
        val timeSuffix = if (timeString.isEmpty()) "" else ", ${timeString}"
        return dateString.replace(timePattern, timeSuffix)
    }

    fun onSetDateRangeTitle(view: TextView, filter: BrowseCarsFilter) {

    }

    fun onSetSubmitTitle(view: TextView, filter: BrowseCarsFilter) {

    }
}