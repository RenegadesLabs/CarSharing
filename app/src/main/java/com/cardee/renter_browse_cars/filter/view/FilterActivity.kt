package com.cardee.renter_browse_cars.filter.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.cardee.R
import com.cardee.databinding.ActivityFilterBinding
import com.cardee.domain.renter.entity.BrowseCarsFilter
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), FilterView {

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityFilterBinding
    lateinit var filter: BrowseCarsFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        setListeners()

    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filter = BrowseCarsFilter()
        binding.filter = filter
        binding.executePendingBindings()
    }

    private fun setListeners() {
        vehicleType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> filter.vehicleTypeId = 0
                    1 -> filter.vehicleTypeId = 1
                    2 -> filter.vehicleTypeId = 2
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        bookHourly.setOnClickListener {
            if (!filter.bookingHourly) {
                filter.bookingHourly = true
                priceRangeSeekBar.apply()
                carAgeSeekBar.apply()
            }
        }
        bookDaily.setOnClickListener {
            if (filter.bookingHourly) {
                filter.bookingHourly = false
                priceRangeSeekBar.apply()
                carAgeSeekBar.apply()
            }
        }
        instantBookingSwitch.setOnCheckedChangeListener { p0, p1 -> filter.instantBooking = !filter.instantBooking }
        curbDelSwitch.setOnCheckedChangeListener { p0, p1 -> filter.curbsideDelivery = !filter.curbsideDelivery }
    }


    override fun showProgress(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String?) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }
}
