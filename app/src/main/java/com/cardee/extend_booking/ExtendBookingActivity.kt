package com.cardee.extend_booking

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.cardee.R
import kotlinx.android.synthetic.main.activity_rental_period.*

class ExtendBookingActivity : AppCompatActivity() {

    companion object {
        const val MODE: String = "calendar_view_mode"
    }

    enum class Mode(val titleId: Int) {
        @StringRes
        DAILY(R.string.extend_daily_booking_title),
        @StringRes
        HOURLY(R.string.extend_hourly_booking_title);
    }

    private var currentMode = Mode.DAILY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_period)

        if (intent.extras?.containsKey(MODE) == true) {
            currentMode = intent.extras[MODE] as Mode
        }
        initState(currentMode)

        dim.setOnClickListener { onBackPressed() }
        prepareWindow()
    }

    private fun initState(mode: Mode) {
        dialogTitle.text = getString(mode.titleId)

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
}
