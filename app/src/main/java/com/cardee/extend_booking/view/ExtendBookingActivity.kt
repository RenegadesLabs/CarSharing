package com.cardee.extend_booking.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import com.cardee.R
import com.cardee.extend_booking.ExtendBookingContract
import com.cardee.extend_booking.presenter.ExtendBookingPresenter
import com.cardee.extend_booking.view.adapter.CalendarExtensionAdapter
import com.cardee.extend_booking.view.adapter.TimePickerExtensionAdapter
import kotlinx.android.synthetic.main.activity_rental_period.*
import kotlinx.android.synthetic.main.view_daily_extension.*
import kotlinx.android.synthetic.main.view_hourly_extesion.*

class ExtendBookingActivity : AppCompatActivity(), ExtendBookingContract.View {

    private val presenter: ExtendBookingContract.Presenter
    private var toast: Toast? = null

    init {
        presenter = ExtendBookingPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_period)
        presenter.onAttachView(this)
        presenter.init(intent)
        dim.setOnClickListener { onBackPressed() }
        prepareWindow()
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.exit_down)
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showMessage(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun onDataReady() {

    }

    override fun onInitMode(mode: ExtendBookingContract.Mode) {
        dialogTitle.text = getString(mode.titleId)
        val layoutId = when (mode) {
            ExtendBookingContract.Mode.DAILY -> R.layout.view_daily_extension
            ExtendBookingContract.Mode.HOURLY -> R.layout.view_hourly_extesion
        }
        val calendarView = layoutInflater.inflate(layoutId, backgroundView, false)
        val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        calendarView.layoutParams = layoutParams
        backgroundView.addView(calendarView)
        when (mode) {
            ExtendBookingContract.Mode.DAILY -> initDailyView()
            ExtendBookingContract.Mode.HOURLY -> initHourlyView()
        }
    }

    private fun initHourlyView() = with(extensionTimePiker) {
        val adapter = TimePickerExtensionAdapter()
        setSelectionAdapter(adapter)
        adapter.listener = { selection ->
            Log.e("HOURLY_SELECTION", "Count: ${selection.size}")
        }
    }

    private fun initDailyView() = with(extensionDailyCalendar) {
        val adapter = CalendarExtensionAdapter()
        setSelectionAdapter(adapter)
        adapter.listener = { selection ->
            Log.e("DAILY_SELECTION", "Count: ${selection.size}")
        }
    }

    override fun onFinish() {
        onBackPressed()
    }
}
