package com.cardee.renter_car_details.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_book_car.view.BookCarActivity
import com.cardee.renter_browse_cars_map.LocationClient
import com.cardee.renter_browse_cars_map.LocationClientImpl
import com.cardee.renter_car_details.RenterCarDetailsContract
import com.cardee.renter_car_details.presenter.RenterCarDetailsPresenter
import com.cardee.renter_car_details.view.viewholder.RenterCarDetailsViewHolder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*
import kotlinx.android.synthetic.main.view_renter_car_details_map.*


class RenterCarDetailsActivity(private val delegate: LocationClient = LocationClientImpl()) :
        AppCompatActivity(), View.OnClickListener, OnMapReadyCallback, RenterCarDetailsContract.View, LocationClient by delegate {
    private var mCarId: Int? = null
    private var favorite: Boolean? = null
    private var presenter: RenterCarDetailsContract.Presenter = RenterCarDetailsPresenter()
    private var viewHolder: RenterCarDetailsViewHolder? = null
    private var map: GoogleMap? = null
    private var markerIcon: Bitmap? = null
    private val permissionRequestCode = 101

    override fun onClick(p0: View?) {
        when (p0) {
            ivRenterCarDetailsToolbarShare -> {
            }
            ivRenterCarDetailsToolbarFavoritesImg -> {
                presenter.addCarToFavorites(mCarId, favorite ?: false)
            }
            bBookCar -> {
                val intent = Intent(this, BookCarActivity::class.java)
                intent.apply {
                    putExtra("carId", mCarId)
                    putExtra("isHourly", viewHolder?.isHourly())
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
        viewHolder = RenterCarDetailsViewHolder(this)
        carLocationMap?.run {
            onCreate(savedInstanceState)
            getMapAsync(this@RenterCarDetailsActivity)
        }
        setListeners()
        getData()
        ivRenterCarDetailsToolbarFavoritesImg
                .setImageResource(if (favorite == true) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)
        initMarkerBitmap()
    }

    private fun initMarkerBitmap() {
        val idleBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_car_marker)
        markerIcon = Bitmap.createScaledBitmap(idleBitmap, 128, 128, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.apply {
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isIndoorLevelPickerEnabled = false
            uiSettings.isMapToolbarEnabled = false
        }
        onSetLocation()
    }

    private fun onSetLocation() {
        presenter.fetchLocation { location ->
            val marker = MarkerOptions().apply {
                icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                position(location)
            }
            map?.addMarker(marker)
            val position = CameraPosition.builder().target(location).zoom(17f).build()
            map?.animateCamera(CameraUpdateFactory.newCameraPosition(position))
        }
    }

    private fun requestCurrentLocation() {
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), permissionRequestCode)
        } else {

        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailedCar(mCarId)
        carLocationMap.onStart()
    }

    override fun onResume() {
        super.onResume()
        connect()
        carLocationMap.onResume()
    }

    private fun getData() {
        mCarId = intent.getIntExtra("carId", -1)
        favorite = intent.getBooleanExtra("isFavorite", false)
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

    override fun setFavorite(favorite: Boolean) {
        ivRenterCarDetailsToolbarFavoritesImg
                .setImageResource(if (favorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)
    }


    override fun setDetailedCar(renterDetailedCar: RenterDetailedCar) {
        viewHolder?.bind(renterDetailedCar)
    }

    override fun setCarLocationString(locationString: String) {
        //TODO implement)
    }

    override fun setDistanceToCar(distance: String) {
        viewHolder?.updateDistance(distance)
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showMessage(message: String?) {
    }

    override fun showMessage(messageId: Int) {
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
        presenter.onDestroy()
        carLocationMap.onDestroy()
    }
}