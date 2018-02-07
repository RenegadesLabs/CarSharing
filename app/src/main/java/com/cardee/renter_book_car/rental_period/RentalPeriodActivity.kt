package com.cardee.renter_book_car.rental_period

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import com.cardee.R
import com.cardee.renter_availability_filter.HourlyAvailabilityView
import kotlinx.android.synthetic.main.activity_dialog_availability.*
import kotlinx.android.synthetic.main.view_hourly_availability.view.*

class RentalPeriodActivity : AppCompatActivity() {

    var mHourly: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_period)
        getIntentData()
        val layout = if (mHourly == true) {
            R.layout.view_hourly_availability
        } else {
            R.layout.view_daily_no_time
        }
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val content = inflater.inflate(layout, backgroundView, false)
        content.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        if (mHourly == true) {
            val hourlyView = content as HourlyAvailabilityView
            hourlyView.btnHourReset.setOnClickListener {
                // TODO: reset hours
            }
            hourlyView.btnHourSave.setOnClickListener {
                // TODO: get start/end dates
                onBackPressed()
            }
        }
        backgroundView.addView(content)
        dim.setOnClickListener { _ -> onBackPressed() }
        prepareWindow()
    }

    private fun getIntentData() {
        mHourly = intent.getBooleanExtra("hourly", true)
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
