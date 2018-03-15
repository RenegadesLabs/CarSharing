package com.cardee.renter_book_car.view

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import com.cardee.account_verify.view.VerifyAccountActivity
import com.cardee.databinding.ActivityBookCarBinding
import com.cardee.domain.bookings.entity.BookCarState
import com.cardee.renter_book_car.BookCarContract
import com.cardee.renter_book_car.collection.CollectionAreaActivity
import com.cardee.renter_book_car.message.view.BookMessageActivity
import com.cardee.renter_book_car.payment.BookPaymentActivity
import com.cardee.renter_book_car.presenter.BookCarPresenter
import com.cardee.renter_book_car.rental_period.RentalPeriodActivity
import com.cardee.renter_car_details.rental_terms.RenterRentalTermsActivity
import com.cardee.renter_home.view.RenterHomeActivity
import com.cardee.util.DateRepresentationDelegate
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_book_car.*
import java.util.*


class BookCarActivity : AppCompatActivity(), BookCarContract.BookCarView, View.OnClickListener {

    private val PERIOD_REQUEST_CODE = 911
    private val LOCATION_REQUEST_CODE = 912
    private val PAYMENT_REQUEST_CODE = 913
    private val RENTAL_TERMS_REQUEST_CODE = 914
    private val VERIFY_ACC_REQUEST_CODE = 915

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityBookCarBinding
    lateinit var mState: BookCarState
    private var mPresenter = BookCarPresenter()
    private var mCarId: Int? = null
    private val delegate: DateRepresentationDelegate by lazy { DateRepresentationDelegate(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.init(this)
        bindView()
        initToolBar()
        setListeners()
        getIntentData()
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
        bookHourly.setOnClickListener(this)
        bookDaily.setOnClickListener(this)
        bookingPeriodContainer.setOnClickListener(this)
        collectionAddress.setOnClickListener(this)
        costBreakdown.setOnClickListener(this)
        promoCodeText.setOnClickListener(this)
        submitCode.setOnClickListener(this)
        verifyAccContainer.setOnClickListener(this)
        paymentContainer.setOnClickListener(this)
        rentalTermsContainer.setOnClickListener(this)
        noteContainer.setOnClickListener(this)
        bookButton.setOnClickListener(this)
    }

    private fun getIntentData() {
        mCarId = intent.getIntExtra("carId", -1)
        mState.bookingHourly = intent.getBooleanExtra("isHourly", true)
    }

    override fun onClick(view: View?) {
        when (view) {
            bookHourly -> {
                if (mState.bookingHourly == false) {
                    mState.bookingHourly = true
                    resetCost()
                    resetDatesAndDelivery()
                    mPresenter.getCost(mCarId ?: return, mState, null)
                }
            }

            bookDaily -> {
                if (mState.bookingHourly == true) {
                    mState.bookingHourly = false
                    resetCost()
                    resetDatesAndDelivery()
                    mPresenter.getCost(mCarId ?: return, mState, null)
                }
            }

            bookingPeriodContainer -> {
                showRentalPeriodActivity()
            }

            collectionAddress -> {
                if ((mState.bookingHourly == true && mState.hourlyCurbsideDelivery.get()) ||
                        (mState.bookingHourly == false && mState.dailyCurbsideDelivery.get())) {
                    val intent = Intent(this, CollectionAreaActivity::class.java)
                    startActivityForResult(intent, LOCATION_REQUEST_CODE)
                }
            }

            costBreakdown -> {
                mPresenter.showCostBreakdown(this, mState)
            }

            promoCodeText -> {
                showMessage("Coming Soon")
//            mState.promocodeClicked.set(true)
            }

            submitCode -> {
                mState.promocodeClicked.set(false)
            }

            verifyAccContainer -> {
                val intent = Intent(this, VerifyAccountActivity::class.java)
                startActivityForResult(intent, VERIFY_ACC_REQUEST_CODE)
            }

            paymentContainer -> {
                showPaymentActivity()
            }

            rentalTermsContainer -> {
                val intent = Intent(this, RenterRentalTermsActivity::class.java)
                intent.putExtra("carId", mCarId)
                intent.putExtra("agree", true)
                startActivityForResult(intent, RENTAL_TERMS_REQUEST_CODE)
            }

            noteContainer -> {
                val intent = Intent(this, BookMessageActivity::class.java)
                startActivity(intent)
            }

            bookButton -> {
                if ((mState.accVerified.get() || mState.accVerifAllFieldsFilled.get()) &&
                        mState.paymentSelected.get() &&
                        mState.rentalTermsAgreed.get()) {
                    mPresenter.requestBooking(mState)
                }
            }
        }
    }

    private fun showRentalPeriodActivity() {
        val intent = Intent(this, RentalPeriodActivity::class.java)
        intent.putExtra("rentalBegin", mPresenter.getFilter().rentalPeriodBegin)
        intent.putExtra("rentalEnd", mPresenter.getFilter().rentalPeriodEnd)
        intent.putExtra("hourly", mState.bookingHourly)
        if (mState.bookingHourly == true) {
            intent.putExtra("availability", mState.availabilityHourly)
            intent.putExtra("begin", mState.availabilityHourlyBegin)
            intent.putExtra("end", mState.availabilityHourlyEnd)
        } else {
            intent.putExtra("availability", mState.availabilityDaily)
            intent.putExtra("pickup", mState.availabilityDailyPickup)
            intent.putExtra("return", mState.availabilityDailyReturn)
        }
        startActivityForResult(intent, PERIOD_REQUEST_CODE)
        overridePendingTransition(R.anim.enter_up, 0)
    }

    private fun showPaymentActivity() {
        val paymentIntent = Intent(this, BookPaymentActivity::class.java)
        if (mState.bookingHourly == true) {
            paymentIntent.putExtra("acceptCash", mState.acceptCashHourly.get())
        } else {
            paymentIntent.putExtra("acceptCash", mState.acceptCashDaily.get())
        }
        paymentIntent.putExtra("cardToken", mState.paymentToken)
        startActivityForResult(paymentIntent, PAYMENT_REQUEST_CODE)
    }

    private fun resetDatesAndDelivery() {
        displayRentalPeriod()
        mState.collectionPicked.set(false)
    }

    override fun resetCost() {
        val cost = if (mState.bookingHourly == true && mState.paymentByMileage.get()) {
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

    override fun displayRentalPeriod() {
        val beginString: String?
        val endString: String?
        if (mState.bookingHourly == true) {
            if (mState.timeBeginHourly == null) {
                beginString = resources.getString(R.string.rental_period_from)
                endString = resources.getString(R.string.rental_period_to)
            } else {
                beginString = delegate.formatMonthDayHour(mState.timeBeginHourly)
                endString = delegate.formatMonthDayHour(mState.timeEndHourly)
            }

        } else {
            if (mState.timeBeginDaily == null) {
                beginString = resources.getString(R.string.rental_period_from)
                endString = resources.getString(R.string.rental_period_to)
            } else {
                beginString = delegate.formatMonthDayHour(mState.timeBeginDaily ?: return)
                val beginDate = delegate.convertDateToDate(mState.timeBeginDaily)
                val endDate = delegate.convertDateToDate(mState.timeEndDaily)

                endString = if (isNextDay(beginDate, endDate) == true) {
                    delegate.formatMonthDayHour(mState.timeEndDaily, 1)
                } else {
                    delegate.formatMonthDayHour(mState.timeEndDaily)
                }
            }
        }
        bookingStart?.text = beginString
        bookingEnd?.text = endString
        mPresenter.saveSate(mState)
    }


    override fun onRequestSuccess() {
        val homeIntent = Intent(this, RenterHomeActivity::class.java)
        homeIntent.putExtra(RenterHomeActivity.TAB_TO_SELECT, 1)
        startActivity(homeIntent)
    }

    private fun isNextDay(begin: Date?, end: Date?): Boolean? {
        val calBegin = Calendar.getInstance(Locale.US)
        calBegin.timeZone = TimeZone.getTimeZone("GMT+8")
        calBegin.time = begin ?: return null

        val calEnd = Calendar.getInstance(Locale.US)
        calEnd.timeZone = TimeZone.getTimeZone("GMT+8")
        calEnd.time = end ?: return null

        return calBegin.get(Calendar.AM_PM) == calEnd.get(Calendar.AM_PM)
    }

    override fun getDateDelegate(): DateRepresentationDelegate {
        return delegate
    }

    private fun setRentalPeriod(timeBegin: String, timeEnd: String) {
        val filter = mPresenter.getFilter()
        filter.rentalPeriodBegin = timeBegin
        filter.rentalPeriodEnd = timeEnd
        mPresenter.saveFilter(filter)

        if (mState.bookingHourly == true) {
            mState.timeBeginHourly = timeBegin

            var endDate = delegate.convertDateToDate(timeEnd)
            endDate = mPresenter.addOneHour(endDate)

            mState.timeEndHourly = delegate.formatAsIsoDate(endDate)
        } else {
            mState.timeBeginDaily = timeBegin
            mState.timeEndDaily = timeEnd
        }
        displayRentalPeriod()
        mPresenter.getCost(mCarId ?: return, mState, null)
    }

    private fun resetRentalPeriod() {
        val filter = mPresenter.getFilter()
        filter.rentalPeriodBegin = null
        filter.rentalPeriodEnd = null
        mPresenter.saveFilter(filter)

        when (mState.bookingHourly) {
            true -> {
                mState.timeBeginHourly = null
                mState.timeEndHourly = null
            }
            false -> {
                mState.timeBeginDaily = null
                mState.timeEndDaily = null
            }
        }
        displayRentalPeriod()
        resetCost()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.saveSate(mState)
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
                    mState.deliveryAddress = address
                    mState.collectionPicked.set(true)
                    mPresenter.saveSate(mState)
                    mPresenter.getCost(mCarId ?: return, mState, null)
                }
            }
            PAYMENT_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val method = data?.getStringExtra("method")
                    val token = data?.getStringExtra("token")
                    paymentChoose.text = method ?: resources.getString(R.string.choose)
                    mState.paymentSelected.set(true)
                    mState.paymentSource = when (method) {
                        "Cash" -> "cash"
                        else -> "card"
                    }
                    mState.paymentToken = token ?: ""
                    mPresenter.saveSate(mState)
                }
            }
            PERIOD_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val timeBegin = data?.getStringExtra("begin")
                    val timeEnd = data?.getStringExtra("end")
                    if (timeBegin != null && timeEnd != null) {
                        setRentalPeriod(timeBegin, timeEnd)
                    } else {
                        resetRentalPeriod()
                    }
                }
            }
            RENTAL_TERMS_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mState.rentalTermsAgreed.set(true)
                    mPresenter.saveSate(mState)
                }
            }
            VERIFY_ACC_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mState.accVerified.set(true)
                    mPresenter.saveSate(mState)
                }
            }
        }
    }
}