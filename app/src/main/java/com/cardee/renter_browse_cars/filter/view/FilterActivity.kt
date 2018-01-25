package com.cardee.renter_browse_cars.filter.view

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.cardee.R
import com.cardee.databinding.ActivityFilterBinding
import com.cardee.domain.renter.entity.BrowseCarsFilter
import com.cardee.renter_browse_cars.filter.presenter.CarsFilterPresenter
import com.cardee.renter_browse_cars.search_area.view.SearchAreaActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_filter.*


class FilterActivity : AppCompatActivity(), FilterView {

    private val LOCATION_REQUEST_CODE = 111
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

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filter = BrowseCarsFilter()
        binding.filter = filter
        binding.executePendingBindings()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun initViews() {
        priceRangeSeekBar.setValueFormatter("$%d")
        carAgeSeekBar.setValueFormatter("%d yr")
        submitButtonText.setFactory {
            val t = TextView(this@FilterActivity)
            t.textAlignment = View.TEXT_ALIGNMENT_CENTER
            t.setTextColor(resources.getColor(R.color.colorAccent))
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            t.setTypeface(t.typeface, Typeface.BOLD)
            t
        }
        submitButtonText.setInAnimation(this, R.anim.fade_in)
        submitButtonText.setOutAnimation(this, R.anim.fade_out)
        submitButtonText.setCurrentText(resources.getString(R.string.submit_btn_text))
    }

    private fun setListeners() {
        toolbarAction.setOnClickListener {
            // Reset filter
            vehicleType.getTabAt(0)?.select()
            filter.vehicleTypeId = 1
            filter.byLocation = false
            searchAreaAddress.text = resources.getString(R.string.default_search_area)
            filter.bookingHourly = false
            filter.instantBooking = false
            filter.curbsideDelivery = false
            priceRangeSeekBar.apply()
            carAgeSeekBar.apply()
            filter.bodyTypeId = 0
            filter.transmissionAuto = true
            filter.transmissionManual = true
            transmissionText.text = resources.getString(R.string.any)
            mPresenter?.getFilteredCars(filter)
        }
        vehicleType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        filter.vehicleTypeId = 1
                        mPresenter?.getFilteredCars(filter)
                    }
                    1 -> {
                        filter.vehicleTypeId = 2
                        mPresenter?.getFilteredCars(filter)
                    }
                    2 -> {
                        filter.vehicleTypeId = 3
                        mPresenter?.getFilteredCars(filter)
                    }
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
                mPresenter?.getFilteredCars(filter)
            }
        }
        bookDaily.setOnClickListener {
            if (filter.bookingHourly) {
                filter.bookingHourly = false
                priceRangeSeekBar.apply()
                carAgeSeekBar.apply()
                mPresenter?.getFilteredCars(filter)
            }
        }
        searchAreaButton.setOnClickListener {
            val intent = Intent(this, SearchAreaActivity::class.java)
            startActivityForResult(intent, LOCATION_REQUEST_CODE)
        }
        instantBookingSwitch.setOnCheckedChangeListener { _, _ ->
            filter.instantBooking = instantBookingSwitch.isChecked
            mPresenter?.getFilteredCars(filter)
        }
        curbDelSwitch.setOnCheckedChangeListener { _, _ ->
            filter.curbsideDelivery = curbDelSwitch.isChecked
            mPresenter?.getFilteredCars(filter)
        }
        sedanButton.setOnClickListener {
            filter.bodyTypeId = 1
            mPresenter?.getFilteredCars(filter)
        }
        hatchbackButton.setOnClickListener {
            filter.bodyTypeId = 4
            mPresenter?.getFilteredCars(filter)
        }
        suvButton.setOnClickListener {
            filter.bodyTypeId = 3
            mPresenter?.getFilteredCars(filter)
        }
        sportButton.setOnClickListener {
            filter.bodyTypeId = 6
            mPresenter?.getFilteredCars(filter)
        }
        convertibleButton.setOnClickListener {
            filter.bodyTypeId = 7
            mPresenter?.getFilteredCars(filter)
        }
        anyButton.setOnClickListener {
            filter.bodyTypeId = 0
            mPresenter?.getFilteredCars(filter)
        }
        autoButton.setOnClickListener {
            if (filter.transmissionAuto) {
                if (filter.transmissionManual) {
                    filter.transmissionAuto = false
                    transmissionText.text = resources.getString(R.string.manual)
                    mPresenter?.getFilteredCars(filter)
                }
            } else {
                filter.transmissionAuto = true
                if (filter.transmissionManual) {
                    transmissionText.text = resources.getString(R.string.any)
                } else {
                    transmissionText.text = resources.getString(R.string.auto)
                }
                mPresenter?.getFilteredCars(filter)
            }
        }
        manualButton.setOnClickListener {
            if (filter.transmissionManual) {
                if (filter.transmissionAuto) {
                    filter.transmissionManual = false
                    transmissionText.text = resources.getString(R.string.auto)
                    mPresenter?.getFilteredCars(filter)
                }
            } else {
                filter.transmissionManual = true
                if (filter.transmissionAuto) {
                    transmissionText.text = resources.getString(R.string.any)
                } else {
                    transmissionText.text = resources.getString(R.string.manual)
                }
                mPresenter?.getFilteredCars(filter)
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

            mPresenter?.getFilteredCars(filter)
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

            mPresenter?.getFilteredCars(filter)
        }
    }

    private fun initPresenter() {
        mPresenter = CarsFilterPresenter(this)
    }

    override fun setButtonText(s: String) {
        submitButtonText?.setText(s)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val address = data?.getStringExtra("address")
                val radius = data?.getIntExtra("radius", 0)
                val location = data?.getParcelableExtra<LatLng>("location")

                searchAreaAddress.text = String.format(
                        resources.getString(R.string.filter_search_area_template), address, radius)
                filter.byLocation = true
                filter.latitude = location?.latitude ?: return
                filter.longitude = location.longitude
                if (radius != null) {
                    filter.radius = radius * 1000
                }

                mPresenter?.getFilteredCars(filter)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
