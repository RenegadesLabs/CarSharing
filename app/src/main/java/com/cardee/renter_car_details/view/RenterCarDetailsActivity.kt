package com.cardee.renter_car_details.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.renter_book_car.view.BookCarActivity
import com.cardee.renter_browse_cars_map.LocationClient
import com.cardee.renter_browse_cars_map.LocationClientImpl
import com.cardee.renter_car_details.RenterCarDetailsContract
import com.cardee.renter_car_details.view.viewholder.RenterCarDetailsViewHolder
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*
import kotlinx.android.synthetic.main.view_renter_car_details_map.*


class RenterCarDetailsActivity(private val delegate: LocationClient = LocationClientImpl()) :
        AppCompatActivity(), View.OnClickListener, OnMapReadyCallback, RenterCarDetailsContract.View, LocationClient by delegate {

    private var mCarId: Int? = null
    private var presenter: RenterCarDetailsContract.Presenter = RenterCarDetailsPresenter()

    private var mViewHolder: RenterCarDetailsViewHolder? = null

    override fun onClick(p0: View?) {
        when (p0) {
            ivRenterCarDetailsToolbarShare -> {
            }
            ivRenterCarDetailsToolbarFavoritesImg -> {
            }
            bBookCar -> {
                val intent = Intent(this, BookCarActivity::class.java)
                intent.apply {
                    putExtra("carId", mCarId)
                    putExtra("isHourly", mViewHolder?.isHourly())
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renter_car_details)
        presenter.attachView(this)
        init(this)
        setSupportActionBar(toolbar)
        mapTouchWrapper.disableOnTouch(lockableScrollView)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = null
        }
        RenterCarDetailsViewHolder(this)
        carLocationMap?.run {
            onCreate(savedInstanceState)
            getMapAsync(this@RenterCarDetailsActivity)
        }
        setListeners()
        getData()
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.apply {
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isIndoorLevelPickerEnabled = false
            uiSettings.isMapToolbarEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        carLocationMap.onStart()
    }

    override fun onResume() {
        super.onResume()
        connect()
        carLocationMap.onResume()
    }

    private fun getData() {
        mCarId = intent.getIntExtra("carId", -1)
    }

    private fun setListeners() {
        ivRenterCarDetailsToolbarShare.setOnClickListener(this)
        ivRenterCarDetailsToolbarFavoritesImg.setOnClickListener(this)
        bBookCar.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setCarLocation(location: String) {

    }

    override fun onPause() {
        super.onPause()
        carLocationMap.onPause()
    }

    override fun onStop() {
        super.onStop()
        carLocationMap.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        carLocationMap.onDestroy()
    }
}