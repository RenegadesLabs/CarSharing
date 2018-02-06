package com.cardee.renter_book_car.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.databinding.ActivityBookCarBinding
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.renter_book_car.BookCarContract
import com.cardee.renter_book_car.presenter.BookCarPresenter
import kotlinx.android.synthetic.main.activity_book_car.*
import java.util.*


class BookCarActivity : AppCompatActivity(), BookCarContract.BookCarView {
    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityBookCarBinding
    lateinit var mState: BookCarState
    private var mPresenter = BookCarPresenter()
    private var mCarId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        initToolBar()
        setListeners()
        mPresenter.init(this)
        getIntentData()
    }

    private fun getIntentData() {
        mCarId = intent.getIntExtra("carId", -1)
    }

    override fun onStart() {
        super.onStart()
        mCarId?.let { mPresenter.getOffer(it, mState) }
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_car)
        mState = BookCarState()
        binding.state = mState
        binding.executePendingBindings()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun setListeners() {
        bookHourly.setOnClickListener {
            if (mState.bookingHourly == false) {
                mState.bookingHourly = true
                mState.timeEnd = null
                mState.timeBegin = null
                resetCost()
            }
        }
        bookDaily.setOnClickListener {
            if (mState.bookingHourly == true) {
                mState.bookingHourly = false
                mState.timeEnd = null
                mState.timeBegin = null
                resetCost()
            }
        }
        collectionAddress.setOnClickListener {
            // TODO: open map
        }
        costBreakdown.setOnClickListener { mPresenter.showCostBreakdown(this, mState) }
        promoCodeText.setOnClickListener { mState.promocodeClicked.set(true) }
        submitCode.setOnClickListener { mState.promocodeClicked.set(false) }
        verifyAccButton.setOnClickListener {
            mState.accVerified.set(true)
        }
    }

    private fun resetCost() {
        val cost = if (mState.bookingHourly == true) {
            "$0++"
        } else {
            "$0"
        }
        setTotalCost(cost)
    }

    override fun updateState(state: BookCarState) {
        mState = state
        binding.state = mState
    }

    override fun setCarTitle(title: String?) {
        carTitle.text = title
    }

    override fun setCarYear(year: String?) {
        carYear.text = year
    }

    override fun setTotalCost(total: String) {
        costText.text = total

        if ((mState.bookingHourly == true && mState.hourlyInstantBooking.get()) ||
                (mState.bookingHourly == false && mState.dailyInstantBooking.get())) {
            bookButtonText.text = String.format(Locale.getDefault(), resources.getString(R.string.book_bnt_instant_template), total)
        } else {
            bookButtonText.text = String.format(Locale.getDefault(), resources.getString(R.string.book_bnt_template), total)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showMessage(message: String?) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}