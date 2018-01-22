package com.cardee.renter_browse_cars.filter.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.databinding.ActivityFilterBinding
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.renter_browse_cars.filter.presenter.CarsFilterPresenter
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), FilterView {

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityFilterBinding
    lateinit var filter: BrowseCarsFilter
    private var mPresenter: CarsFilterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        initToolBar()
        initViews()
        setListeners()
        initPresenter()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.getFilteredCars(filter)
    }

    private fun initPresenter() {
        mPresenter = CarsFilterPresenter(this)
    }

    private fun initViews() {
        priceRangeSeekBar.setValueFormatter("$%d")
        carAgeSeekBar.setValueFormatter("%d yr")
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filter = BrowseCarsFilter()
        binding.filter = filter
        binding.executePendingBindings()
    }

    private fun initToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun setListeners() {
        toolbarAction.setOnClickListener {
            // Reset filter
            vehicleType.getTabAt(0)?.select()
            filter.vehicleTypeId = 0
            filter.bookingHourly = false
            filter.instantBooking = false
            filter.curbsideDelivery = false
            priceRangeSeekBar.apply()
            carAgeSeekBar.apply()
            filter.bodyTypeId = 5
            filter.transmissionAuto = true
            filter.transmissionManual = true
            transmissionText.text = resources.getString(R.string.any)
        }
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
        instantBookingSwitch.setOnCheckedChangeListener { p0, p1 -> filter.instantBooking = instantBookingSwitch.isChecked }
        curbDelSwitch.setOnCheckedChangeListener { p0, p1 -> filter.curbsideDelivery = curbDelSwitch.isChecked }
        sedanButton.setOnClickListener { filter.bodyTypeId = 0 }
        hatchbackButton.setOnClickListener { filter.bodyTypeId = 1 }
        suvButton.setOnClickListener { filter.bodyTypeId = 2 }
        sportButton.setOnClickListener { filter.bodyTypeId = 3 }
        convertibleButton.setOnClickListener { filter.bodyTypeId = 4 }
        anyButton.setOnClickListener { filter.bodyTypeId = 5 }
        autoButton.setOnClickListener {
            if (filter.transmissionAuto) {
                if (filter.transmissionManual) {
                    filter.transmissionAuto = false
                    transmissionText.text = resources.getString(R.string.manual)
                }
            } else {
                filter.transmissionAuto = true
                if (filter.transmissionManual) {
                    transmissionText.text = resources.getString(R.string.any)
                } else {
                    transmissionText.text = resources.getString(R.string.auto)
                }
            }
        }
        manualButton.setOnClickListener {
            if (filter.transmissionManual) {
                if (filter.transmissionAuto) {
                    filter.transmissionManual = false
                    transmissionText.text = resources.getString(R.string.auto)
                }
            } else {
                filter.transmissionManual = true
                if (filter.transmissionAuto) {
                    transmissionText.text = resources.getString(R.string.any)
                } else {
                    transmissionText.text = resources.getString(R.string.manual)
                }
            }
        }
        priceRangeSeekBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            filter.minPrice = minValue.toInt()
            filter.maxPrice = maxValue.toInt()

            if (minValue?.toFloat() == priceRangeSeekBar.minValue) {
                if (maxValue?.toFloat() == priceRangeSeekBar.maxValue) {
                    priceRangeText.text = "Any"
                } else {
                    priceRangeText.text = "up to $$maxValue"
                }
            } else {
                if (maxValue?.toFloat() == priceRangeSeekBar.maxValue) {
                    priceRangeText.text = "from $$minValue to Any"
                } else {
                    priceRangeText.text = "from $$minValue to $$maxValue"
                }
            }
        }
        carAgeSeekBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            filter.minYears = minValue.toInt()
            filter.maxYears = maxValue.toInt()

            if (minValue?.toFloat() == carAgeSeekBar.minValue) {
                if (maxValue?.toFloat() == carAgeSeekBar.maxValue) {
                    carAgeText.text = "Any"
                } else {
                    carAgeText.text = "up to $maxValue yr"
                }
            } else {
                if (maxValue?.toFloat() == carAgeSeekBar.maxValue) {
                    carAgeText.text = "from $minValue yr to Any"
                } else {
                    carAgeText.text = "from $minValue yr to $maxValue yr"
                }
            }
        }
    }

    override fun setButtonText(s: String) {
        submitButton.text = s
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
