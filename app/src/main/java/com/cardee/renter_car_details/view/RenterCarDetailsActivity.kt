package com.cardee.renter_car_details.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.auth.preview.PreviewActivity
import com.cardee.data_source.remote.service.AccountManager
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_book_car.rental_period.RentalPeriodActivity
import com.cardee.renter_book_car.view.BookCarActivity
import com.cardee.renter_browse_cars.RenterEventBus
import com.cardee.renter_browse_cars_map.LocationClient
import com.cardee.renter_browse_cars_map.LocationClientImpl
import com.cardee.renter_car_details.RenterCarDetailsContract
import com.cardee.renter_car_details.presenter.RenterCarDetailsPresenter
import com.cardee.renter_car_details.rental_terms.RenterRentalTermsActivity
import com.cardee.renter_car_details.view.viewholder.RenterCarDetailsViewHolder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*
import kotlinx.android.synthetic.main.view_renter_car_details_map.*


class RenterCarDetailsActivity(private val delegate: LocationClient = LocationClientImpl()) :
        AppCompatActivity(), View.OnClickListener, OnMapReadyCallback, RenterCarDetailsContract.View, LocationClient by delegate {
    private val shareLink = "http://labracode.itg5.com/offers/link"

    private var mCarId: Int? = null
    private var favorite: Boolean? = null
    private var presenter: RenterCarDetailsContract.Presenter = RenterCarDetailsPresenter()
    private var viewHolder: RenterCarDetailsViewHolder? = null
    private var map: GoogleMap? = null
    private var markerIcon: Bitmap? = null
    private var currentLocationIcon: Bitmap? = null

    companion object {
        const val LOCATION_REQUEST_CODE = 101
        const val PERIOD_REQUEST_CODE = 911
    }

    override fun onClick(p0: View?) {
        when (p0) {
            ivRenterCarDetailsToolbarShare -> {
                shareCar()
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
            rentalTermsButton -> {
                val intent = Intent(this, RenterRentalTermsActivity::class.java)
                intent.putExtra("carId", mCarId)
                intent.putExtra("agree", false)
                startActivity(intent)
            }
            btnCheckAvailability -> {
                val intent = Intent(this, RentalPeriodActivity::class.java)
                val hourly = viewHolder?.isHourly() ?: false
                val details = viewHolder?.getDetails() ?: return
                intent.putExtra("hourly", hourly)
                if (hourly) {
                    intent.putExtra("availability", details.carAvailabilityHourly)
                    intent.putExtra("begin", details.carAvailabilityTimeBegin)
                    intent.putExtra("end", details.carAvailabilityTimeEnd)
                } else {
                    val dailyDetails = details.orderDailyDetails ?: return
                    intent.putExtra("availability", details.carAvailabilityDaily)
                    intent.putExtra("pickup", dailyDetails.timePickup)
                    intent.putExtra("return", dailyDetails.timeReturn)
                }
                startActivityForResult(intent, PERIOD_REQUEST_CODE)
                overridePendingTransition(R.anim.enter_up, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERIOD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renter_car_details)
        presenter.attachView(this)
        init(this)
        getData()
        doOnConnect { requestCurrentLocation() }
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
        initMarkerBitmap()
    }

    private fun initMarkerBitmap() {
        val idleBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_car_marker)
        markerIcon = Bitmap.createScaledBitmap(idleBitmap, 128, 128, false)
        currentLocationIcon = Bitmap.createScaledBitmap(idleBitmap, 64, 64, false)
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
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_REQUEST_CODE)
        } else {
            val location = LocationServices.FusedLocationApi.getLastLocation(obtainClient())
            location ?: return
            mCarId?.let { id ->
                renderCurrentLocation(LatLng(location.longitude, location.longitude))
                presenter.fetchDistance(id, location.latitude, location.longitude)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestCurrentLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun setLocationString(locationString: String) {
        addressText.text = locationString
    }

    private fun renderCurrentLocation(location: LatLng) {
        val currentLocationMarker = MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(currentLocationIcon))
        map?.addMarker(currentLocationMarker)
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
        val appLinkAction = intent.action
        val appLinkData = intent.data
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            if (AccountManager.getInstance(this).isLoggedIn) {
                val uri = Uri.parse(appLinkData.toString())
                mCarId = uri.lastPathSegment.toInt()
                return
            }
            showMessage(R.string.auth_error)
            startActivity(Intent(this, PreviewActivity::class.java))
            finish()
        }
    }

    private fun setListeners() {
        ivRenterCarDetailsToolbarShare.setOnClickListener(this)
        ivRenterCarDetailsToolbarFavoritesImg.setOnClickListener(this)
        bBookCar.setOnClickListener(this)
        rentalTermsButton.setOnClickListener(this)
        btnCheckAvailability.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setFavorite(favorite: Boolean) {
        this.favorite = favorite
        RenterEventBus.getInstance().put(RenterEventBus.Event(true))
        ivRenterCarDetailsToolbarFavoritesImg
                .setImageResource(if (favorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)
    }


    override fun setDetailedCar(renterDetailedCar: RenterDetailedCar) {
        viewHolder?.bind(renterDetailedCar)
        this.favorite = renterDetailedCar.favorite
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

    private fun shareCar() {
        val link = shareLink + mCarId
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, link)
        startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.invite_friends_title)))
    }
}