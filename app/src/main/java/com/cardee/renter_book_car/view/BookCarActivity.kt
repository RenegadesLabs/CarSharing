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
import kotlinx.android.synthetic.main.activity_book_car.*


class BookCarActivity : AppCompatActivity(), BookCarContract.BookCarView {

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityBookCarBinding
    lateinit var mState: BookCarState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        setListeners()
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_car)
        mState = BookCarState()
        binding.state = mState
        binding.executePendingBindings()
    }

    private fun setListeners() {
        bookHourly.setOnClickListener {
            if (mState.bookingHourly == false) {
                mState.bookingHourly = true
            }
        }
        bookDaily.setOnClickListener {
            if (mState.bookingHourly == true) {
                mState.bookingHourly = false
            }
        }
        promoCodeText.setOnClickListener { mState.promocodeClicked.set(true) }
        submitCode.setOnClickListener { mState.promocodeClicked.set(false) }
        verifyAccButton.setOnClickListener {
            mState.accVerified.set(true)
        }
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