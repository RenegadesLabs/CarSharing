package com.cardee.extend_booking

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.cardee.R
import com.cardee.extend_booking.presenter.ExtendBookingPresenter
import kotlinx.android.synthetic.main.activity_rental_period.*

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
            ExtendBookingContract.Mode.DAILY -> R.layout.view_daily_no_time
            ExtendBookingContract.Mode.HOURLY -> R.layout.view_hourly_rental_period
        }
    }

    private fun initHourlyView() {

    }

    private fun initDailyView() {

    }

    override fun onFinish() {
        onBackPressed()
    }
}
