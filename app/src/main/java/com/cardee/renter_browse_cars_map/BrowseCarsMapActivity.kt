package com.cardee.renter_browse_cars_map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cardee.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_browse_cars_map.*

class BrowseCarsMapActivity(private var delegate: LocationClient = LocationClientImpl()) :
        LocationClient by delegate, AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_cars_map)
        init(this)
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {

    }

    override fun onResume() {
        super.onResume()
        connect()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        map.onDestroy()
    }
}
