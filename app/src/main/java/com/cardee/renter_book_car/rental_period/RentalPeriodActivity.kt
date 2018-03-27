package com.cardee.renter_book_car.rental_period

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.renter_availability_filter.CalendarAdapter
import com.cardee.renter_availability_filter.TimePickerAdapter
import com.cardee.util.AvailabilityFromFilterDelegate
import com.cardee.util.DateRepresentationDelegate
import kotlinx.android.synthetic.main.activity_rental_period.*
import kotlinx.android.synthetic.main.view_daily_no_time.*
import kotlinx.android.synthetic.main.view_daily_no_time.view.*
import kotlinx.android.synthetic.main.view_hourly_rental_period.*
import kotlinx.android.synthetic.main.view_hourly_rental_period.view.*
import java.util.*

class RentalPeriodActivity : AppCompatActivity() {

    var mHourly: Boolean? = null
    var mAvailability: Array<String?>? = null
    var mAvailabilityBegin: String? = null
    var mAvailabilityEnd: String? = null
    var mAvailabilityPickup: String? = null
    var mAvailabilityReturn: String? = null
    var rentalBegin: String? = null
    var rentalEnd: String? = null
    var mDailyAdapter: CalendarAdapter? = null
    var mHourlyAdapter: TimePickerAdapter? = null
    var timeBegin: String? = null
    var timeEnd: String? = null
    val dateDelegate: DateRepresentationDelegate by lazy { DateRepresentationDelegate(this) }
    private val delegate: AvailabilityFromFilterDelegate = AvailabilityFromFilterDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_period)
        getIntentData()
        val layout = if (mHourly == true) {
            R.layout.view_hourly_rental_period
        } else {
            R.layout.view_daily_no_time
        }
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val content = inflater.inflate(layout, backgroundView, false)
        content.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        backgroundView.addView(content)

        if (mHourly == true) {
            initHourlyView(content as ConstraintLayout)
        } else {
            initDailyView(content as ConstraintLayout)
        }

        dim.setOnClickListener { onBackPressed() }
        prepareWindow()
    }

    private fun getIntentData() {
        rentalBegin = intent.getStringExtra("rentalBegin")
        rentalEnd = intent.getStringExtra("rentalEnd")
        mHourly = intent.getBooleanExtra("hourly", true)
        mAvailability = intent.getStringArrayExtra("availability")
        if (mHourly == true) {
            mAvailabilityBegin = intent.getStringExtra("begin")
            mAvailabilityEnd = intent.getStringExtra("end")
        } else {
            mAvailabilityPickup = intent.getStringExtra("pickup")
            mAvailabilityReturn = intent.getStringExtra("return")
        }
    }

    private fun initHourlyView(hourlyView: ConstraintLayout) {
        mHourlyAdapter = TimePickerAdapter()

        hourlyView.timePicker.setOnReadyListener {
            mAvailability?.let {
                mHourlyAdapter?.setAvailabilityRange(it, mAvailabilityBegin, mAvailabilityEnd)
            }
        }

        hourlyView.timePicker.setSelectionAdapter(mHourlyAdapter)

        mHourlyAdapter?.setSelectionListener { it ->
            val end = addOneHour(it.lastOrNull())
            timeBegin = dateDelegate.formatAsIsoDate(it.firstOrNull())
            timeEnd = dateDelegate.formatAsIsoDate(it.lastOrNull())
            if (timeBegin != null) {
                dateHourFrom.text = dateDelegate.formatMonthDayHour(timeBegin)
                dateHourTo.text = dateDelegate.formatMonthDayHour(dateDelegate.formatAsIsoDate(end))
            } else {
                dateHourFrom.text = resources.getString(R.string.not_specified)
                dateHourTo.text = resources.getString(R.string.not_specified)
            }

            delegate.onSetSubmitTitle(
                    btnHourSave,
                    BrowseCarsFilter(rentalPeriodBegin = timeBegin, rentalPeriodEnd = timeEnd),
                    true)
        }

        hourlyView.btnHourReset.setOnClickListener {
            hourlyView.timePicker.reset()
            timeBegin = null
            timeEnd = null
        }

        hourlyView.btnHourSave.setOnClickListener {
            intent = Intent()
            intent.putExtra("begin", timeBegin)
            intent.putExtra("end", timeEnd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        delegate.onInitPickerSelection(
                mHourlyAdapter ?: return,
                rentalBegin ?: return,
                rentalEnd ?: return)
    }

    private fun initDailyView(dailyView: ConstraintLayout) {
        mDailyAdapter = CalendarAdapter()

        dailyView.calendar.setOnReadyListener {
            mAvailability?.let {
                mDailyAdapter?.setAvailabilityRange(it)
            }
        }

        dailyView.calendar.setSelectionAdapter(mDailyAdapter)

        mDailyAdapter?.setSelectionListener {
            val beginDate = setHoursToDate(it.firstOrNull(), mAvailabilityPickup)
            timeBegin = dateDelegate.formatAsIsoDate(beginDate)
            val endDate = setHoursToDate(it.lastOrNull(), mAvailabilityReturn)
            timeEnd = dateDelegate.formatAsIsoDate(endDate)
            if (timeBegin != null) {
                dateFrom.text = dateDelegate.formatMonthDayHour(timeBegin)
                dateTo.text = if (isNextDay(beginDate, endDate) == true) {
                    dateDelegate.formatMonthDayHour(timeEnd, 1)
                } else {
                    dateDelegate.formatMonthDayHour(timeEnd)
                }
            } else {
                dateFrom.text = resources.getString(R.string.not_specified)
                dateTo.text = resources.getString(R.string.not_specified)
            }

            delegate.onSetSubmitTitle(
                    btnSave,
                    BrowseCarsFilter(rentalPeriodBegin = timeBegin,
                            rentalPeriodEnd = if (isNextDay(beginDate, endDate) == true) {
                                addOneDay(timeEnd)
                            } else {
                                timeEnd
                            }),
                    false)
        }

        dailyView.btnReset.setOnClickListener {
            dailyView.calendar.reset()
        }

        dailyView.btnSave.setOnClickListener {
            intent = Intent()
            intent.putExtra("begin", timeBegin)
            intent.putExtra("end", timeEnd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        delegate.onInitCalendarSelection(
                mDailyAdapter ?: return,
                rentalBegin ?: return,
                rentalEnd ?: return)
    }

    private fun isNextDay(begin: Date?, end: Date?): Boolean? {
        val calBegin = Calendar.getInstance(Locale.US)
        calBegin.timeZone = CardeeApp.getTimeZone()
        calBegin.time = begin ?: return null

        val calEnd = Calendar.getInstance(Locale.US)
        calEnd.timeZone = CardeeApp.getTimeZone()
        calEnd.time = end ?: return null

        return calBegin.get(Calendar.AM_PM) == calEnd.get(Calendar.AM_PM)
    }

    private fun setHoursToDate(date: Date?, pickupTime: String?): Date? {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.timeZone = CardeeApp.getTimeZone()
        calendar.time = date ?: return null
        val tempDate: Date = dateDelegate.convertTimeToDate(pickupTime) ?: calendar.time
        val tempCal = GregorianCalendar(Locale.US)
        tempCal.timeZone = CardeeApp.getTimeZone()
        tempCal.time = tempDate
        calendar.set(Calendar.HOUR_OF_DAY, tempCal.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, tempCal.get(Calendar.MINUTE))
        calendar.set(Calendar.SECOND, tempCal.get(Calendar.SECOND))
        return calendar.time
    }

    private fun addOneHour(end: Date?): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = end ?: return null
        calendar.add(Calendar.HOUR_OF_DAY, 1)
        return calendar.time
    }

    private fun addOneDay(date: String?): String? {
        val time = dateDelegate.convertDateToDate(date)

        val calendar = Calendar.getInstance()
        calendar.time = time ?: return null
        calendar.add(Calendar.DATE, 1)

        return dateDelegate.formatAsIsoDate(calendar.time)
    }

    private fun prepareWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        window.attributes.dimAmount = 0.6f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun finish() {
        super.finish()

        overridePendingTransition(0, R.anim.exit_down)
    }
}
