package com.cardee.renter_browse_cars.search_area.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.*
import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.owner_car_details.service.FetchAddressService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_search_area.*

class SearchAreaActivity : AppCompatActivity(), SearchAreaView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraMoveListener {
    private val PERMISSIONS_REQUEST_ACCESS_LOCATION = 101
    private val ADDRESS_BY_LOCATION_CODE = 201
    private val MY_ADDRESS_BY_LOCATION_CODE = 202
    private val MY_ADDRESS_BY_LOCATION_UPDATE_CODE = 203
    private val KEY_CAMERA_POSITION = "camera_position"
    private val KEY_LOCATION = "location"
    private val SINGAPORE_LAT = 1.352083
    private val SINGAPORE_LNG = 103.81983600000001
    private val DEFAULT_ZOOM = 9f
    private val TAG = this.javaClass.simpleName.toString()

    @DrawableRes
    private val myCurrentLocationIcon = R.drawable.ic_my_location
    @DrawableRes
    private val anyOtherLocationIcon = R.drawable.ic_other_location

    private var mMap: GoogleMap? = null
    private var apiClient: GoogleApiClient? = null
    private var mCurrentToast: Toast? = null
    private var addressReceiver: AddressResultReceiver? = null
    private val handler = Handler(Looper.getMainLooper())
    private var currentAddressString: String? = null
    private var myCurrentAddressString: String? = null
    private var mLocationPermissionGranted: Boolean = false
    private var mLastKnownLocation: Location? = null
    private var mLastSearchLocation: LatLng? = null
    private var mDefaultLocation: LatLng = LatLng(SINGAPORE_LAT, SINGAPORE_LNG)
    private var mCameraPosition: CameraPosition? = null
    private var circle: Circle? = null
    private var currentAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
        setContentView(R.layout.activity_search_area)

        searchAreaMap.onCreate(savedInstanceState)
        initToolBar()
        initViews()
        setListeners()
        addressReceiver = AddressResultReceiver(handler)
    }

    private fun setListeners() {
        seekBar.setOnSeekbarChangeListener({ value, actualValue ->
            circle?.radius = actualValue * 1000
        })
        myLocationButton.setOnClickListener { moveToMyCurrentLocation() }
        searchSaveButton.setOnClickListener {
            if (mMap != null) {
                val intent = Intent()
                intent.putExtra("address", currentAddress)
                intent.putExtra("radius", seekBar.selectedMinValue.toInt())
                intent.putExtra("location", mLastSearchLocation)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        searchAreaMap.onResume()
    }

    private fun initToolBar() {
        setSupportActionBar(searchAreaToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun initViews() {
        seekBar.setValueFormatter("%d km")
        apiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()
        apiClient?.connect()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.setOnCameraMoveListener(this)

        getLocationPermission()
        getDeviceLocation()

        circle = mMap?.addCircle(CircleOptions()
                .center(mMap?.cameraPosition?.target)
                .radius((seekBar.selectedMinValue.toInt() * 1000).toDouble())
                .strokeWidth(0f)
                .fillColor(resources.getColor(R.color.search_area_circle)))

        val myCurrentLocation = LatLng(mLastKnownLocation?.latitude ?: return,
                mLastKnownLocation?.longitude ?: return)
        requestAddressByLocation(myCurrentLocation, MY_ADDRESS_BY_LOCATION_CODE)
        mMap?.setOnCameraIdleListener {
            val latLng = mMap?.cameraPosition?.target
            requestAddressByLocation(latLng, ADDRESS_BY_LOCATION_CODE)
        }
    }

    private fun requestAddressByLocation(location: LatLng?, requestCode: Int) {
        mLastSearchLocation = location
        val requestLocationIntent = Intent(this, FetchAddressService::class.java)
        requestLocationIntent.putExtra(FetchAddressService.LOCATION, location)
        requestLocationIntent.putExtra(FetchAddressService.RECEIVER, addressReceiver)
        requestLocationIntent.putExtra(FetchAddressService.REQUEST_CODE, requestCode)
        this.startService(requestLocationIntent)
    }

    private fun getDeviceLocation() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)) {

            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient)
        }

        when {
            mCameraPosition != null -> mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
            mLastKnownLocation != null -> mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(mLastKnownLocation?.latitude ?: return,
                            mLastKnownLocation?.longitude ?: return), DEFAULT_ZOOM))
            else -> mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM))
        }
    }

    private fun getLocationPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_ACCESS_LOCATION)
        }
    }

    private fun moveToMyCurrentLocation() {
        getLocationPermission()
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (apiClient?.isConnected == true) {
                val location: Location? = LocationServices.FusedLocationApi.getLastLocation(apiClient)
                val latLng = LatLng(location?.latitude ?: return, location.longitude)
                updateCurrent(latLng, MY_ADDRESS_BY_LOCATION_UPDATE_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_LOCATION)
        }
    }

    private fun updateCurrent(location: LatLng, addressRequestCode: Int) {
        moveMapToLocation(location)
        requestAddressByLocation(location, addressRequestCode)
    }

    private fun moveMapToLocation(location: LatLng) {
        if (mMap == null) {
            Log.e(TAG, "GoogleMap is not instantiated")
            return
        }
        val currentZoom = mMap?.cameraPosition?.zoom
        val position = CameraPosition.Builder()
                .target(location)
                .zoom(if (currentZoom ?: return < 9) 9f else currentZoom)
                .build()
        val focus = CameraUpdateFactory.newCameraPosition(position)
        mMap?.animateCamera(focus)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
        searchAreaMap.getMapAsync(this)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onCameraMove() {
        circle?.center = mMap?.cameraPosition?.target
    }


    override fun onStop() {
        super.onStop()
        apiClient?.disconnect()
        searchAreaMap.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchAreaMap.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        if (mMap != null) {
            outState?.putParcelable(KEY_CAMERA_POSITION, mMap?.cameraPosition)
            outState?.putParcelable(KEY_LOCATION, mLastKnownLocation)
            super.onSaveInstanceState(outState)
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
                val addressString = address.thoroughfare ?: address.getAddressLine(0)
                currentAddress = addressString
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
