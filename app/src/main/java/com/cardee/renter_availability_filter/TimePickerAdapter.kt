package com.cardee.renter_availability_filter

import com.cardee.custom.time_picker.model.Hour
import com.cardee.custom.time_picker.view.selection.RangeSelectionAdapter
import com.cardee.util.DateRangeConverter
import java.util.*


class TimePickerAdapter : RangeSelectionAdapter<Date>() {

    private val converter = DateRangeConverter()
    private var listener: (List<Date>) -> Unit = {}
    private var selection: MutableList<Date> = mutableListOf()

    override fun onSelectionChanged(hourz: MutableList<Hour>?) {
        selection.clear()
        hourz?.forEach { selection.add(it.calendarTime) }
        listener.invoke(selection)
    }

    override fun onNext(item: Date?): Date {
        return item ?: Date()
    }

    fun setSelectionListener(listener: (List<Date>) -> Unit) {
        this.listener = listener
    }

    fun setAvailabilityRange(dates: Array<String?>, timeBegin: String?, timeReturn: String?) {
        converter.convertToHourlyDateList(dates, timeBegin, timeReturn, { result: List<Date> ->
            setAvailableDates(result.toTypedArray())
        })
    }
}