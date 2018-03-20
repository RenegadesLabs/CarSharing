package com.cardee.util

import android.content.Context
import android.view.View
import android.widget.TextView
import com.cardee.R

class StringFormatDelegate(context: Context) {

    private val saveSuffixes: Array<String> = context.resources.getStringArray(R.array.btn_save_title_suffixes)
    private val valueSuffixes: Array<String> = context.resources.getStringArray(R.array.days_availability_suffixes)
    private val startZeroRegex: Regex = Regex("\\b0")

    fun onDateCountTitleChange(view: TextView, count: Int) {
        onChangeCountString(view, count, saveSuffixes)
    }

    fun onDateCountValueChange(view: TextView, count: Int) {
        onChangeCountString(view, count, valueSuffixes)
    }

    private fun onChangeCountString(view: TextView, count: Int, values: Array<String>) {
        val index = if (count > 1) 2 else count
        val prefix = if (index == 0) "" else "$count "
        val title = "$prefix + ${values[index]}"
        view.text = title
    }

    fun onSetHourlyRentalRateFirst(view: TextView, amount: Float?) = amount?.let {
        onSetRentalRate(view, it, " per hour (off-peak)")
    }

    fun onSetHourlyRentalRateSecond(view: TextView, amount: Float?) = amount?.let {
        onSetRentalRate(view, it, " per hour (peak)")
    }

    fun onSetDailyRentalRateFirst(view: TextView, amount: Float?) = amount?.let {
        onSetRentalRate(view, it, " per day (weekdays)")
    }

    fun onSetDailyRentalRateSecond(view: TextView, amount: Float?) = amount?.let {
        onSetRentalRate(view, it, " per day (weekends and P.H.)")
    }

    private fun onSetRentalRate(view: TextView, amount: Float, suffix: String) {
        val value = "$$amount $suffix"
        view.visibility = View.VISIBLE
        view.text = value
    }

    fun onSetDailyRentalDiscount(view: TextView, discount: Float?) = discount?.let {
        val discountString = "3 days discount $discount%"
        view.visibility = View.VISIBLE
        view.text = discountString
    }

    fun onSetRentalMinimum(view: TextView, minimum: Int?) = minimum?.let {
        val minimumString = "Minimum " + Integer.toString(it) + if (it > 1) " hours" else " hour"
        view.visibility = View.VISIBLE
        view.text = minimumString
    }

    fun onSetFuelPolicy(view: TextView, policyName: String?, payAmountMileage: String?) {
        policyName?.firstOrNull() ?: return
        val condition = !payAmountMileage.isNullOrEmpty() && policyName != "Return with similar level"
        val fuelPolicyString = "$policyName ${if (condition) " @ $payAmountMileage per km" else ""}"
        view.visibility = View.VISIBLE
        view.text = fuelPolicyString
    }

    fun onSetHourlyTitle(view: TextView, daysCount: Int, beginTime: String?, endTime: String?) {
        val index = if (daysCount > 1) 2 else daysCount
        val prefix = if (index == 0) "" else "$daysCount "
        var title = "$prefix ${valueSuffixes[index]}"
        if (beginTime != null && endTime != null) {
            title = "$title\n${dropStartZero(beginTime)} - ${dropStartZero(endTime)}"
        }
        view.text = title
    }

    private fun dropStartZero(time: String) = time.replace(startZeroRegex, "")
}
