package com.cardee.extend_booking.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.cardee.R
import com.cardee.custom.ChangeStrategy
import com.cardee.domain.bookings.entity.AvailabilityState
import com.cardee.extend_booking.ExtendBookingContract
import com.cardee.extend_booking.presenter.ExtendBookingPresenter
import com.cardee.extend_booking.view.adapter.CalendarExtensionAdapter
import com.cardee.extend_booking.view.adapter.TimePickerExtensionAdapter
import com.cardee.util.AvailabilityFromFilterDelegate
import kotlinx.android.synthetic.main.activity_rental_period.*
import kotlinx.android.synthetic.main.view_daily_extension.*
import kotlinx.android.synthetic.main.view_hourly_extesion.*
import java.util.*

class ExtendBookingActivity : AppCompatActivity(), ExtendBookingContract.View {

    private val presenter: ExtendBookingContract.Presenter
    private var toast: Toast? = null
    private var newEndDate: Date? = null
    private var dailyAdapter: CalendarExtensionAdapter? = null
    private var hourlyAdapter: TimePickerExtensionAdapter? = null
    private lateinit var titleDelegate: AvailabilityFromFilterDelegate

    init {
        presenter = ExtendBookingPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_period)
        titleDelegate = AvailabilityFromFilterDelegate()
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

    override fun bindDaily(data: AvailabilityState) {
        dailyAdapter?.setAvailableDates(data.availableDates)
        if (data.timeStart != null && data.timeEnd != null) {
            extensionDailyCalendar.setChangeStrategy(ChangeStrategy.EXTENSION_ONLY, data.timeEnd)
            dailyAdapter?.setRange(data.timeStart, data.timeEnd)
            bindTitles(dailyDateFrom, dailyDateTo, data.timeStart, data.timeEnd)
        }
    }

    override fun bindHourly(data: AvailabilityState) {
        hourlyAdapter?.setAvailableDates(data.availableDates)
        if (data.timeStart != null && data.timeEnd != null) {
            extensionTimePiker.setChangeStrategy(ChangeStrategy.EXTENSION_ONLY, data.timeEnd)
            hourlyAdapter?.setRange(data.timeStart, data.timeEnd)
            bindTitles(dateHourFrom, dateHourTo, data.timeStart, data.timeEnd)
        }
    }

    private fun bindTitles(startTitle: TextView, endTitle: TextView, dateStart: Date?, dateEnd: Date?) {
        titleDelegate.setDailyTitlesFromDates(startTitle, endTitle, dateStart, dateEnd)
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
        presenter.requestData()
    }

    private fun initHourlyView() = with(extensionTimePiker) {
        hourlyAdapter = TimePickerExtensionAdapter()
        setSelectionAdapter(hourlyAdapter)
        hourlyAdapter?.listener = { selection ->
            newEndDate = selection.last()
            titleDelegate.setDailyTitlesFromDates(dateHourFrom, dateHourTo, selection.first(), selection.last())
        }
        btnHourlyExtensionSave.setOnClickListener {
            newEndDate?.let { date -> presenter.save(date) }
        }
    }

    private fun initDailyView() = with(extensionDailyCalendar) {
        dailyAdapter = CalendarExtensionAdapter()
        setSelectionAdapter(dailyAdapter)
        dailyAdapter?.listener = { selection ->
            newEndDate = selection.last()
            titleDelegate.setDailyTitlesFromDates(dailyDateFrom, dailyDateTo, selection.first(), selection.last())
        }
        btnDailyExtensionSave.setOnClickListener {
            newEndDate?.let { date -> presenter.save(date) }
        }
    }

    override fun onFinish() {
        onBackPressed()
    }
}
