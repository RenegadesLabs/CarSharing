package com.cardee.renter_availability_filter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.NumberPicker
import com.cardee.R
import com.cardee.util.TimeFrameDelegate
import kotlinx.android.synthetic.main.view_daily_availability.view.*


class DailyAvailabilityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private val adapter = CalendarAdapter()
    private var finishCallback: (Boolean) -> Unit = {}
    private val toggle: (v: View) -> Unit = { toggleState() }
    private var timeValues: Array<String> = arrayOf()

    override fun onFinishInflate() {
        super.onFinishInflate()
        calendar.setSelectionAdapter(adapter)
        adapter.setSelectionListener { selection ->

        }
        setTime.setOnClickListener(toggle)
        backTitle.setOnClickListener(toggle)
        setTimeIcon.setOnClickListener(toggle)
        backTitleIcon.setOnClickListener(toggle)
        initNumberPickers()
    }

    fun setFinishCallback(callback: (Boolean) -> Unit) {
        finishCallback = callback
    }

    fun toggleState() {
        val flag = calendar.visibility == View.VISIBLE
        calendar.visibility = if (!flag) View.VISIBLE else View.GONE
        setTime.visibility = if (!flag) View.VISIBLE else View.GONE
        backTitle.visibility = if (flag) View.VISIBLE else View.GONE
        setTimeIcon.visibility = if (!flag) View.VISIBLE else View.GONE
        backTitleIcon.visibility = if (flag) View.VISIBLE else View.GONE
        pickupTimeTitle.visibility = if (flag) View.VISIBLE else View.GONE
        returnTimeTitle.visibility = if (flag) View.VISIBLE else View.GONE
        pickupTimePicker.visibility = if (flag) View.VISIBLE else View.GONE
        returnTimePicker.visibility = if (flag) View.VISIBLE else View.GONE
        pickerBackground.visibility = if (flag) View.VISIBLE else View.GONE
        timeSelector.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun initNumberPickers() {
        timeValues = context.resources.getStringArray(R.array.availability_time_titles)
        pickupTimePicker.displayedValues = timeValues
        pickupTimePicker.minValue = 0
        pickupTimePicker.maxValue = timeValues.size - 1
        returnTimePicker.displayedValues = timeValues
        returnTimePicker.minValue = 0
        returnTimePicker.maxValue = timeValues.size - 1
        setDividerColor(pickupTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
        setDividerColor(returnTimePicker, ContextCompat.getColor(context, android.R.color.transparent))
    }

    private fun setDividerColor(picker: NumberPicker, color: Int) {
        val fields = NumberPicker::class.java.declaredFields
        fields.filter { it.name == "mSelectionDivider" }[0].let { field ->
            field.isAccessible = true
            try {
                val drawable = ColorDrawable(color)
                field.set(picker, drawable)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}