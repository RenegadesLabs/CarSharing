package com.cardee.util

import android.content.Context
import android.widget.NumberPicker
import android.widget.TextView
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.renter_availability_filter.CalendarAdapter
import com.cardee.renter_availability_filter.TimePickerAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AvailabilityFromFilterDelegate(context: Context) {

    enum class Mode {
        DAILY, HOURLY
    }

    companion object {
        private val ISO_8601_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private val ISO_DATE_PATTERN: String = "HH:mm:ssZZZZZ"
        private val SHORT_DATE_PATTERN: String = "d MMM"
        private val SHORT_TIME_PATTERN: String = "ha"
    }

    private var anytime: String = "Anytime"
    private var daily: String = "Book Daily"
    private var hourly: String = "Book Hourly"
    private var noValue: String = "Not Specified"
    private val timePattern = Regex(", \\d{1,2}[ap]m$")

    private val iso8601OutDateFormatter: SimpleDateFormat
    private val iso8601InDateFormatter: SimpleDateFormat
    private val isoOutDateFormatter: SimpleDateFormat
    private val isoInDateFormatter: SimpleDateFormat
    private val shortDateFormatter: SimpleDateFormat
    private val shortTimeFormatter: SimpleDateFormat

    init {
        val timeZone = TimeZone.getTimeZone("GMT+08:00")
        iso8601OutDateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.getDefault())
        iso8601InDateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.getDefault())
        isoOutDateFormatter = SimpleDateFormat(ISO_DATE_PATTERN, Locale.getDefault())
        isoInDateFormatter = SimpleDateFormat(ISO_DATE_PATTERN, Locale.getDefault())
        shortDateFormatter = SimpleDateFormat(SHORT_DATE_PATTERN, Locale.getDefault())
        shortTimeFormatter = SimpleDateFormat(SHORT_TIME_PATTERN, Locale.getDefault())
        iso8601OutDateFormatter.timeZone = timeZone
        isoInDateFormatter.timeZone = timeZone
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
            val timeString = shortTimeFormatter.format(date)
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

    fun onSetTitlesFromFilter(startView: TextView, endView: TextView, filter: BrowseCarsFilter, mode: Mode) {

    }

    fun onSetTitleFromDate(view: TextView, date: Date?) {

    }

    fun onSetTiming(view: TextView, time: String?) {

    }

    fun onSetDateRangeTitle(view: TextView, filter: BrowseCarsFilter, mode: Mode) {

    }

    fun onSetSubmitTitle(view: TextView, filter: BrowseCarsFilter, mode: Mode) {

    }
}