package com.cardee.util

import android.content.Context
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_availability_filter.CalendarAdapter
import com.cardee.renter_availability_filter.TimePickerAdapter
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AvailabilityFromFilterDelegate {

    companion object {
        private val ISO_8601_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private val ISO_TIME_PATTERN: String = "HH:mm:ssZZZZZ"
        private val SHORT_DATE_PATTERN: String = "d MMM"
        private val SHORT_MONTH_PATTERN: String = "MMM"
        private val SHORT_TIME_PATTERN: String = "hha"
        private val TITLE_PATTERN: String = "d MMM, ha"
    }

    private var anytime: String = "Anytime"
    private var daily: String = "Book Daily"
    private var hourly: String = "Book Hourly"
    private var noValue: String = "Not Specified"
    private val separator: String = "•"
    private var save: String = "Save"
    private var day: String = "day"
    private var days: String = "days"
    private var hour: String = "hour"
    private var hours: String = "hours"
    private val timePattern = Regex(", \\d{1,2}[ap]m$")
    private val datePattern = Regex("^\\d{1,2}\\s+\\w+$")
    private val datePrefixPattern = Regex("^\\d{1,2}\\s+\\w+")
    private val titlePattern = Regex("^(\\d{1,2}\\s+\\w+),\\s(\\d{1,2}[ap]m)$")
    private val startZeroRegex = Regex("\\b0")

    private val iso8601DateFormatter: SimpleDateFormat
    private val shortMonthFormatter: SimpleDateFormat
    private val isoTimeFormatter: SimpleDateFormat
    private val shortDateFormatter: SimpleDateFormat
    private val shortTimeFormatter: SimpleDateFormat
    private val titleFormatter: SimpleDateFormat
    private val calendar: Calendar

    init {
        val symbols = DateFormatSymbols(Locale.US)
        symbols.amPmStrings = arrayOf("am", "pm")
        calendar = Calendar.getInstance()
        val timeZone = CardeeApp.getTimeZone()
        iso8601DateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.US)
        shortMonthFormatter = SimpleDateFormat(SHORT_MONTH_PATTERN, Locale.US)
        shortDateFormatter = SimpleDateFormat(SHORT_DATE_PATTERN, Locale.US)
        shortTimeFormatter = SimpleDateFormat(SHORT_TIME_PATTERN, Locale.US)
        isoTimeFormatter = SimpleDateFormat(ISO_TIME_PATTERN, Locale.US)
        titleFormatter = SimpleDateFormat(TITLE_PATTERN, Locale.US)
        iso8601DateFormatter.timeZone = timeZone
        shortMonthFormatter.timeZone = timeZone
        shortDateFormatter.timeZone = timeZone
        shortTimeFormatter.timeZone = timeZone
        isoTimeFormatter.timeZone = timeZone
        titleFormatter.timeZone = timeZone
        shortTimeFormatter.dateFormatSymbols = symbols
        titleFormatter.dateFormatSymbols = symbols
    }

    fun onInitCalendarSelection(calendarAdapter: CalendarAdapter, isoDateStart: String, isoDateEnd: String) {
        try {
            val dateStart = iso8601DateFormatter.parse(isoDateStart)
            val dateEnd = iso8601DateFormatter.parse(isoDateEnd)
            calendarAdapter.setRange(dateStart, dateEnd)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
    }

    fun onInitPickerSelection(pickerAdapter: TimePickerAdapter, isoDateStart: String, isoDateEnd: String) {
        try {
            val dateStart = iso8601DateFormatter.parse(isoDateStart)
            val dateEnd = iso8601DateFormatter.parse(isoDateEnd)
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
            val date = isoTimeFormatter.parse(isoTime)
            val timeString = shortTimeFormatter.format(date).toLowerCase()
            for (i in 0 until values.size) {
                if (timeString.run { replace(Regex("^0"), "") } == values[i]) {
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
        val date = iso8601DateFormatter.parse(time)
        onSetTitleFromTime(view, date)
    }

    fun setDailyTitlesFromFilter(startView: TextView, endView: TextView, filter: BrowseCarsFilter) {
        try {
            val startDate = iso8601DateFormatter.parse(filter.rentalPeriodBegin)
            val endDate = iso8601DateFormatter.parse(filter.rentalPeriodEnd)
            onSetTitleFromDate(startView, startDate)
            onSetTitleFromDate(endView, endDate)
            filter.pickupTime?.let { onAttachTimingToTitle(startView, it) }
            filter.returnTime?.let { onAttachTimingToTitle(endView, it) }
        } catch (ex: ParseException) {
            startView.text = noValue
            endView.text = noValue
        }
    }

    fun setDailyTitlesFromDates(startView: TextView, endView: TextView, dateBegin: Date?, dateEnd: Date?) {
        if (dateBegin != null && dateEnd != null) {
            onSetTitleFromDate(startView, dateBegin)
            onSetTitleFromDate(endView, dateEnd)
        } else {
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

    fun onSetTitleFromTime(view: TextView, dateString: String?, includingLast: Boolean = true) {
        dateString ?: return
        try {
            val date = iso8601DateFormatter.parse(dateString)
            onSetTitleFromTime(view, date, includingLast)
        } catch (ex: ParseException) {
        }
    }

    fun onSetTitleFromTime(view: TextView, date: Date?, includingLast: Boolean = true) {
        if (date != null) {
            val formatReady: Date
            if (!includingLast) {
                calendar.time = date
                calendar.add(Calendar.HOUR, 1)
                formatReady = calendar.time
            } else {
                formatReady = date
            }
            val dateString = titleFormatter.format(formatReady)
            view.text = dateString
            return
        }
        view.text = noValue
    }

    fun onAttachTimingToTitle(view: TextView, isoTime: String) {
        try {
            val date = isoTimeFormatter.parse(isoTime)
            val timeString = shortTimeFormatter.format(date)
            onSetTiming(view, timeString.run { replace(Regex("^0"), "") })
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
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
            return "$dateString, $timeString"
        }
        return dateString
    }

    private fun replaceTiming(dateString: String, timeString: String): String {
        val timeSuffix = if (timeString.isEmpty()) "" else ", $timeString"
        return dateString.replace(timePattern, timeSuffix)
    }

    fun onSetDateRangeTitle(titleView: TextView, subtitleView: TextView, filter: BrowseCarsFilter) {
        val title: String
        if (filter.bookingHourly == null) {
            setDefaultDailyString(titleView, subtitleView)
            return
        }
        if (filter.bookingHourly == false) {
            if (filter.rentalPeriodBegin == null || filter.rentalPeriodEnd == null) {
                setDefaultDailyString(titleView, subtitleView)
            } else {
                titleView.visibility = View.GONE
                title = buildDailyRangeTitle(filter)
                if ("" == title) {
                    setDefaultDailyString(titleView, subtitleView)
                    return
                }
                subtitleView.text = dropStartZero(title)
            }
            return
        }
        if (filter.bookingHourly == true) {
            if (filter.rentalPeriodBegin == null || filter.rentalPeriodEnd == null) {
                setDefaultHourlyString(titleView, subtitleView)
            } else {
                titleView.visibility = View.GONE
                title = buildHourlyRangeTitle(filter)
                if (title == "") {
                    setDefaultHourlyString(titleView, subtitleView)
                    return
                }
                subtitleView.text = dropStartZero(title)
            }
        }
    }

    private fun setDefaultDailyString(titleView: TextView, subtitleView: TextView) {
        titleView.visibility = View.VISIBLE
        titleView.text = daily
        subtitleView.text = anytime
    }

    private fun setDefaultHourlyString(titleView: TextView, subtitleView: TextView) {
        titleView.visibility = View.VISIBLE
        titleView.text = hourly
        subtitleView.text = anytime
    }

    private fun buildDailyRangeTitle(filter: BrowseCarsFilter): String {
        try {
            val dateBegin = iso8601DateFormatter.parse(filter.rentalPeriodBegin)
            val dateEnd = iso8601DateFormatter.parse(filter.rentalPeriodEnd)
            if (filter.pickupTime == null && filter.returnTime == null) {
                if (isSingleMonth(dateBegin, dateEnd)) {
                    return buildSingleMonthString(dateBegin, dateEnd)
                }
            }
            var pickupTime: Date? = null
            var returnTime: Date? = null
            if (filter.pickupTime != null) {
                pickupTime = isoTimeFormatter.parse(filter.pickupTime)
                returnTime = isoTimeFormatter.parse(filter.returnTime)
            }
            return buildDaysString(dateBegin, dateEnd, pickupTime, returnTime)
        } catch (ex: ParseException) {
        }
        return ""
    }

    private fun buildHourlyRangeTitle(filter: BrowseCarsFilter): String {
        try {
            val dateBegin = iso8601DateFormatter.parse(filter.rentalPeriodBegin)
            val dateEndIncluded = iso8601DateFormatter.parse(filter.rentalPeriodEnd)
            calendar.time = dateEndIncluded
            calendar.add(Calendar.HOUR, 1)
            val dateEnd = calendar.time
            if (isSingleDay(dateBegin, dateEnd)) {
                return buildSingleDayString(dateBegin, dateEnd)
            }
            return buildHoursString(dateBegin, dateEnd)
        } catch (ex: ParseException) {
        }
        return ""
    }

    fun setHourlyAvailabilityRange(context: Context, textView: TextView, renterDetailedCar: RenterDetailedCar?) {
        try {
            val timeBegin = isoTimeFormatter.parse(renterDetailedCar?.carAvailabilityTimeBegin
                    ?: "")
            val timeEnd = isoTimeFormatter.parse(renterDetailedCar?.carAvailabilityTimeEnd ?: "")
            if (isSingleDay(timeBegin, timeEnd)) {
                val timeBeginString = shortTimeFormatter.format(timeBegin)
                val timeEndString = shortTimeFormatter.format(timeEnd)
                val timingText: String = context.getString(R.string.renter_car_details_timing_available_from) +
                        timeBeginString + context.getString(R.string.renter_car_details_timing_available_to) +
                        timeEndString
                textView.text = dropStartZero(timingText)
            }
        } catch (e: ParseException) {
            textView.text = ""
        }
    }

    fun setDailyPickupAndReturnTiming(context: Context, textView: TextView, renterDetailedCar: RenterDetailedCar) {
        try {

        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
    }

    private fun isSingleMonth(dateBegin: Date, dateEnd: Date): Boolean {
        calendar.time = dateBegin
        val monthBegin = calendar.get(Calendar.MONTH)
        calendar.time = dateEnd
        val monthEnd = calendar.get(Calendar.MONTH)
        return monthBegin == monthEnd
    }

    private fun isSingleDay(dateBegin: Date, dateEnd: Date): Boolean {
        calendar.time = dateBegin
        val monthBegin = calendar.get(Calendar.MONTH)
        val dayBegin = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = dateEnd
        val monthEnd = calendar.get(Calendar.MONTH)
        val dayEnd = calendar.get(Calendar.DAY_OF_MONTH)
        return monthBegin == monthEnd && dayBegin == dayEnd
    }

    private fun buildSingleDayString(dateBegin: Date, dateEnd: Date): String {
        val dayOfMonth = shortDateFormatter.format(dateBegin)
        val hourBegin = shortTimeFormatter.format(dateBegin)
                .apply { replace(Regex("^0"), "") }
        val hourEnd = shortTimeFormatter.format(dateEnd)
                .apply { replace(Regex("^0"), "") }
        return "$dayOfMonth\n$hourBegin - $hourEnd"
    }

    private fun buildSingleMonthString(dateBegin: Date, dateEnd: Date): String {
        val month = shortMonthFormatter.format(dateBegin)
        calendar.time = dateBegin
        val dayStart = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = dateEnd
        val dayEnd = calendar.get(Calendar.DAY_OF_MONTH)
        return "$month $dayStart - $dayEnd"
    }

    private fun buildHoursString(dateBegin: Date, dateEnd: Date): String {
        val dateBeginString = titleFormatter.format(dateBegin)
        val dateEndString = titleFormatter.format(dateEnd)
        return "$dateBeginString - \n$dateEndString"
    }

    private fun buildDaysString(dateBegin: Date, dateEnd: Date, pickupTime: Date?, returnTime: Date?): String {
        return "${buildDayString(dateBegin, pickupTime)} - \n${buildDayString(dateEnd, returnTime)}"
    }

    private fun buildDayString(date: Date, time: Date?): String {
        val dateString = shortDateFormatter.format(date)
        if (time == null) {
            return dateString
        }
        val timeString = shortTimeFormatter.format(time)
        return "$dateString, $timeString"
    }

    fun onSetSubmitTitle(view: TextView, filter: BrowseCarsFilter, hourly: Boolean) {
        val defaultTitle = "$anytime $separator $save"
        if (filter.rentalPeriodBegin == null || filter.rentalPeriodEnd == null) {
            view.text = defaultTitle
            return
        }
        try {
            val beginDate = iso8601DateFormatter.parse(filter.rentalPeriodBegin)
            val endDate = iso8601DateFormatter.parse(filter.rentalPeriodEnd)
            when (hourly) {
                true -> {
                    val hourCount = countHours(beginDate, endDate)
                    val hourSuffix = if (hourCount == 1L) hour else hours
                    val hourlyTitle = "$hourCount $hourSuffix $separator $save"
                    view.text = hourlyTitle
                }
                false -> {
                    val dayCount = countDays(beginDate, endDate)
                    val daySuffix = if (dayCount == 1L) day else days
                    val dailyTitle = "$dayCount $daySuffix $separator $save"
                    view.text = dailyTitle
                }
            }
            return
        } catch (ex: ParseException) {
        }
        view.text = defaultTitle
    }

    private fun countDays(begin: Date, end: Date): Long {
        val difMillis = end.time - begin.time
        return TimeUnit.DAYS.convert(difMillis, TimeUnit.MILLISECONDS) + 1
    }

    private fun countHours(begin: Date, end: Date): Long {
        val difMillis = end.time - begin.time
        return TimeUnit.HOURS.convert(difMillis, TimeUnit.MILLISECONDS) + 1
    }

    private fun dropStartZero(time: String) = time.replace(startZeroRegex, "")
}