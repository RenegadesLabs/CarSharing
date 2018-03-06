package com.cardee.extend_booking.view.adapter

import com.cardee.custom.time_picker.model.Hour
import com.cardee.custom.time_picker.view.selection.RangeSelectionAdapter
import java.util.*


class TimePickerExtensionAdapter : RangeSelectionAdapter<Date>() {

    var listener: (List<Date>) -> Unit = {}

    override fun onNext(item: Date?): Date? {
        return item
    }

    override fun onSelectionChanged(dayz: MutableList<Hour>?) {
        dayz ?: return
        listener.invoke(dayz.flatMap { listOf(it.calendarTime) })
    }


}