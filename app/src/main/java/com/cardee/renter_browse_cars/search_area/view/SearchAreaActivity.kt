package com.cardee.renter_browse_cars.search_area.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Address
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.owner_car_details.service.FetchAddressService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_search_area.*

class SearchAreaActivity : AppCompatActivity(), SearchAreaView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 105
    private val ADDRESS_BY_LOCATION_CODE = 201
    private val MY_ADDRESS_BY_LOCATION_CODE = 202
    private val MY_ADDRESS_BY_LOCATION_UPDATE_CODE = 203

    @DrawableRes
    private val myCurrentLocationIcon = R.drawable.ic_my_location
    @DrawableRes
    private val anyOtherLocationIcon = R.drawable.ic_other_location

    private var map: GoogleMap? = null
    private var apiClient: GoogleApiClient? = null
    private var mCurrentToast: Toast? = null
    private var addressReceiver: AddressResultReceiver? = null
    private var currentAddressString: String? = null
    private var myCurrentAddressString: String? = null
    private var center: Point? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_area)

        searchAreaMap.onCreate(savedInstanceState)
        initToolBar()
        initViews()
    }

    private fun initToolBar() {
        setSupportActionBar(searchAreaToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun initViews() {
        searchAreaMap.getMapAsync(this)
        seekBar.setValueFormatter("%d km")
        apiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()
        val top = searchMarker.getTop()
        val bottom = searchMarker.getBottom()
        val left = searchMarker.getLeft()
        val right = searchMarker.getRight()
        center = Point(left + (right - left) / 2, top + (bottom - top) / 2)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        val myCurrentLocation = obtainMyCurrentLocation()
        if (myCurrentLocation == null) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            requestAddressByLocation(myCurrentLocation, MY_ADDRESS_BY_LOCATION_CODE)
        }
        map?.setOnCameraIdleListener {
            val latLng = map?.projection?.fromScreenLocation(center)
            requestAddressByLocation(latLng, ADDRESS_BY_LOCATION_CODE)
        }
    }

    private fun obtainMyCurrentLocation(): LatLng? {
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (apiClient?.isConnected == true) {
                val location = LocationServices.getFusedLocationProviderClient(this).lastLocation.result
                return LatLng(location.latitude, location.longitude)
            }
        }
        return null
    }

    private fun requestAddressByLocation(location: LatLng?, requestCode: Int) {
        val requestLocationIntent = Intent(this, FetchAddressService::class.java)
        requestLocationIntent.putExtra(FetchAddressService.LOCATION, location)
        requestLocationIntent.putExtra(FetchAddressService.RECEIVER, addressReceiver)
        requestLocationIntent.putExtra(FetchAddressService.REQUEST_CODE, requestCode)
        this.startService(requestLocationIntent)
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
        apiClient?.connect()
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

    private fun markAsMyCurrentLocation(myCurrentLocation: Boolean) {
        myLocationButton.setImageResource(if (myCurrentLocation) myCurrentLocationIcon else anyOtherLocationIcon)
    }

    private fun isMyCurrentLocation(): Boolean {
        return currentAddressString != null &&
                myCurrentAddressString != null &&
                currentAddressString.equals(myCurrentAddressString, ignoreCase = true)
    }

    private inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            if (resultCode == FetchAddressService.CODE_SUCCESS) {
                val address = resultData.getParcelable<Address>(FetchAddressService.ADDRESS)
                val requestCode = resultData.getInt(FetchAddressService.REQUEST_CODE)
//                currentAddress = address
                val addressBuilder = StringBuilder()
                for (i in 0..address!!.maxAddressLineIndex) {
                    addressBuilder.append(address.getAddressLine(i))
                    if (i != address.maxAddressLineIndex) {
                        addressBuilder.append(" ")
                    }
                }
                val addressString = addressBuilder.toString()
                when (requestCode) {
                    MY_ADDRESS_BY_LOCATION_UPDATE_CODE -> {
                        myCurrentAddressString = addressString
                        addressText.text = addressString
                        currentAddressString = addressString
                    }
                    ADDRESS_BY_LOCATION_CODE -> {
                        addressText.text = addressString
                        currentAddressString = addressString
                    }
                    MY_ADDRESS_BY_LOCATION_CODE -> {
                        myCurrentAddressString = addressString
                    }
                }
            }
            markAsMyCurrentLocation(isMyCurrentLocation())
        }
    }
}
