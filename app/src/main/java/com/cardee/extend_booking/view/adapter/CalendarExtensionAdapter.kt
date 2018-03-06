package com.cardee.extend_booking.view.adapter

import com.cardee.custom.calendar.model.Day
import com.cardee.custom.calendar.view.selection.RangeSelectionAdapter
import java.util.*


class CalendarExtensionAdapter : RangeSelectionAdapter<Date>() {

    var listener: (List<Date>) -> Unit = {}

    override fun onNext(item: Date?): Date? {
        return item
    }

    override fun onSelectionChanged(dayz: MutableList<Day>?) {
        dayz ?: return
        listener.invoke(dayz.flatMap { listOf(it.calendarTime) })
    }


}