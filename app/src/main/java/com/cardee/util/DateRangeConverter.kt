package com.cardee.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateRangeConverter {

    companion object {
        private const val ISO_8601_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        private const val ISO_TIME_PATTERN: String = "HH:mm:ssZZZZZ"
    }

    private val isoDateFormatter = SimpleDateFormat(ISO_8601_DATE_PATTERN, Locale.US)
    private val isoTimeFormatter = SimpleDateFormat(ISO_TIME_PATTERN, Locale.US)

    fun convertToDailyDateList(dateArray: Array<String?>, consumer: Consumer<List<Date>>) {
        val subscription = Single.just(dateArray)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map { array -> array.filter { string -> string != null } }
                .flatMap { dates ->
                    val dateList = dates.map { isoDateFormatter.parse(it) }
                    Single.just(dateList)
                }.subscribe(consumer)
    }

    fun convertToDailyDateList(dateBegin: String,
                               dateEnd: String,
                               consumer: Consumer<MutableList<Date>>) {
        val subscription = Single.just(IsoDateRangeWrapper(dateBegin, dateEnd))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { wrapper ->
                    val begin = isoDateFormatter.parse(wrapper.dateBegin)
                    val end = isoDateFormatter.parse(wrapper.dateEnd)
                    val dateList = mutableListOf<Date>()
                    val baseCalendar = Calendar.getInstance()
                    val limitCalendar = Calendar.getInstance()
                    baseCalendar.time = begin
                    limitCalendar.time = end
                    do {
                        dateList.add(baseCalendar.time)
                        baseCalendar.add(Calendar.DAY_OF_MONTH, 1)
                    } while (baseCalendar.get(Calendar.YEAR) <= limitCalendar.get(Calendar.YEAR) &&
                            baseCalendar.get(Calendar.DAY_OF_YEAR) <= limitCalendar.get(Calendar.DAY_OF_YEAR))
                    Single.just(dateList)
                }.subscribe(consumer)
    }

    fun convertToHourlyDateList(dateArray: Array<String?>,
                                timeBegin: String? = null,
                                timeEnd: String? = null,
                                consumer: Consumer<List<Date>>) {
        val subscription = Single.just(IsoDateWrapper(dateArray, timeBegin, timeEnd))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { wrapper ->
                    val calendar = Calendar.getInstance()
                    val timeBeginDate = isoTimeFormatter.parse(timeBegin ?: "00:00:00+08:00")
                    val timeEndDate = isoTimeFormatter.parse(timeBegin ?: "23:59:59+08:00")
                    calendar.time = timeBeginDate
                    val hourBegin = calendar.get(Calendar.HOUR_OF_DAY)
                    calendar.time = timeEndDate
                    val diff = calculateHoursDifference(timeBeginDate, timeEndDate)
                    val datesList = mutableListOf<Date>()
                    for (dateString in wrapper.dates) {
                        dateString?.let {
                            val date = isoDateFormatter.parse(dateString)
                            calendar.time = date
                            calendar.set(Calendar.HOUR_OF_DAY, hourBegin)
                            for (i in 1..diff) {
                                calendar.add(Calendar.HOUR_OF_DAY, i)
                                datesList.add(calendar.time)
                            }
                        }
                    }
                    Single.just(datesList)
                }.subscribe(consumer)
    }

    private fun calculateDayDifference(begin: Date, end: Date): Int {
        val difMillis = end.time - begin.time
        return TimeUnit.DAYS.convert(difMillis, TimeUnit.MILLISECONDS).toInt()
    }

    private fun calculateHoursDifference(begin: Date, end: Date): Int {
        val difMillis = end.time - begin.time
        return TimeUnit.HOURS.convert(difMillis, TimeUnit.MILLISECONDS).toInt()
    }

    private data class IsoDateRangeWrapper(val dateBegin: String,
                                           val dateEnd: String)

    private data class IsoDateWrapper(val dates: Array<String?>,
                                      val timeBegin: String? = null,
                                      val timeEnd: String? = null)


}
