package com.cardee.renter_book_car.view

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.databinding.ActivityBookCarBinding
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.renter_book_car.BookCarContract
import com.cardee.renter_book_car.collection.CollectionAreaActivity
import com.cardee.renter_book_car.message.view.BookMessageActivity
import com.cardee.renter_book_car.payment.BookPaymentActivity
import com.cardee.renter_book_car.presenter.BookCarPresenter
import com.cardee.renter_book_car.rental_period.RentalPeriodActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_book_car.*
import java.util.*


class BookCarActivity : AppCompatActivity(), BookCarContract.BookCarView {
    private val PERIOD_REQUEST_CODE = 911
    private val LOCATION_REQUEST_CODE = 912
    private val PAYMENT_REQUEST_CODE = 913

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityBookCarBinding
    lateinit var mState: BookCarState
    private var mPresenter = BookCarPresenter()
    private var mCarId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.init(this)
        bindView()
        initToolBar()
        setListeners()
        getIntentData()
        mCarId?.let { mPresenter.getOffer(it, mState) }
    }

    private fun getIntentData() {
        mCarId = intent.getIntExtra("carId", -1)
        mState.bookingHourly = intent.getBooleanExtra("isHourly", true)
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
        bookingPeriodContainer.setOnClickListener {
            val intent = Intent(this, RentalPeriodActivity::class.java)
            intent.putExtra("hourly", mState.bookingHourly)
            startActivityForResult(intent, PERIOD_REQUEST_CODE)
            overridePendingTransition(R.anim.enter_up, 0)
        }
        collectionAddress.setOnClickListener {
            val intent = Intent(this, CollectionAreaActivity::class.java)
            startActivityForResult(intent, LOCATION_REQUEST_CODE)
        }
        costBreakdown.setOnClickListener { mPresenter.showCostBreakdown(this, mState) }
        promoCodeText.setOnClickListener { mState.promocodeClicked.set(true) }
        submitCode.setOnClickListener { mState.promocodeClicked.set(false) }
        verifyAccButton.setOnClickListener {
            mState.accVerified.set(true)
        }
        paymentChoose.setOnClickListener {
            val paymentIntent = Intent(this, BookPaymentActivity::class.java)
            if (mState.bookingHourly == true) {
                paymentIntent.putExtra("acceptCash", mState.acceptCashHourly.get())
            } else {
                paymentIntent.putExtra("acceptCash", mState.acceptCashDaily.get())
            }
            startActivityForResult(paymentIntent, PAYMENT_REQUEST_CODE)
        }
        addNote.setOnClickListener {
            val intent = Intent(this, BookMessageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun resetCost() {
        val cost = if (mState.bookingHourly == true) {
            "$0++"
        } else {
            "$0"
        }
        setTotalCost(cost)
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

    override fun onStop() {
        super.onStop()
        mPresenter?.saveSate(mState)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val address = data?.getStringExtra("address")
                    val location = data?.getParcelableExtra<LatLng>("location")
                    collectionAddress.text = address
                    mState = mPresenter.getState()
                    mState.latitude = location?.latitude ?: return
                    mState.longitude = location.longitude
                    mState.collectionPicked.set(true)
                    mPresenter.saveSate(mState)
                    mPresenter.getCost(mCarId ?: return, mState)
                }
            }
            PAYMENT_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {

                }
            }
        }
    }
}