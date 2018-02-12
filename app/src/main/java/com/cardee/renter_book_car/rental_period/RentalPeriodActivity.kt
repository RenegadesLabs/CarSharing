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
import com.cardee.R
import com.cardee.renter_availability_filter.CalendarAdapter
import com.cardee.renter_availability_filter.TimePickerAdapter
import com.cardee.util.DateRepresentationDelegate
import com.cardee.util.DateStringDelegate
import kotlinx.android.synthetic.main.activity_rental_period.*
import kotlinx.android.synthetic.main.view_daily_no_time.view.*
import kotlinx.android.synthetic.main.view_hourly_rental_period.view.*
import java.util.*

class RentalPeriodActivity : AppCompatActivity() {

    var mHourly: Boolean? = null
    var mAvailability: Array<String?>? = null
    var mAvailabilityBegin: String? = null
    var mAvailabilityEnd: String? = null
    var mDailyAdapter: CalendarAdapter? = null
    var mHourlyAdapter: TimePickerAdapter? = null
    var timeBegin: String? = null
    var timeEnd: String? = null
    val dateDelegate: DateRepresentationDelegate by lazy { DateRepresentationDelegate(this) }

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
        if (mHourly == true) {
            val hourlyView = content as ConstraintLayout
            mHourlyAdapter = TimePickerAdapter()
            mHourlyAdapter?.setSelectionListener { it ->
                val end = addOneHour(it.lastOrNull())
                timeBegin = dateDelegate.formatAsIsoDate(it.firstOrNull())
                timeEnd = dateDelegate.formatAsIsoDate(end)
            }
            mAvailability?.let { mHourlyAdapter?.setAvailabilityRange(it, mAvailabilityBegin, mAvailabilityEnd) }
            hourlyView.timePicker.setSelectionAdapter(mHourlyAdapter)
            hourlyView.btnHourReset.setOnClickListener {
                hourlyView.timePicker.reset()
            }
            hourlyView.btnHourSave.setOnClickListener {
                intent = Intent()
                intent.putExtra("begin", timeBegin)
                intent.putExtra("end", timeEnd)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else {
            val dailyView = content as ConstraintLayout
            mDailyAdapter = CalendarAdapter()
            mDailyAdapter?.setSelectionListener {
                timeBegin = dateDelegate.formatAsIsoDate(it.firstOrNull())
                timeEnd = dateDelegate.formatAsIsoDate(it.lastOrNull())
            }
            mAvailability?.let { mDailyAdapter?.setAvailabilityRange(it) }
            dailyView.calendar.setSelectionAdapter(mDailyAdapter)
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
        }
        backgroundView.addView(content)
        dim.setOnClickListener { _ -> onBackPressed() }
        prepareWindow()
    }

    private fun addOneHour(end: Date?): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = end ?: return null
        calendar.add(Calendar.HOUR_OF_DAY, 1)
        return calendar.time
    }

    private fun getIntentData() {
        mHourly = intent.getBooleanExtra("hourly", true)
        mAvailability = intent.getStringArrayExtra("availability")
        mAvailabilityBegin = intent.getStringExtra("begin")
        mAvailabilityEnd = intent.getStringExtra("end")
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
