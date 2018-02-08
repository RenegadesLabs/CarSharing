package com.cardee.renter_availability_filter

import com.cardee.custom.calendar.model.Day
import com.cardee.custom.calendar.view.selection.RangeSelectionAdapter
import com.cardee.util.DateRangeConverter
import java.util.*

class CalendarAdapter : RangeSelectionAdapter<Date>() {

    private val converter = DateRangeConverter()
    private var listener: (List<Date>) -> Unit = {}
    private var selection: MutableList<Date> = mutableListOf()

    override fun onSelectionChanged(dayz: MutableList<Day>?) {
        selection.clear()
        dayz?.forEach { selection.add(it.calendarTime) }
        listener.invoke(selection)
    }

    override fun onNext(item: Date?): Date {
        return item ?: Date()
    }

    fun setSelectionListener(listener: (List<Date>) -> Unit) {
        this.listener = listener
    }

    fun setAvailabilityRange(dates: Array<String?>) {
        converter.convertToDailyDateList(dates, { result: List<Date> ->
            setAvailableDates(result.toTypedArray())
        })
    }
}