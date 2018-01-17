package com.cardee.renter_browse_cars_map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.cardee.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_browse_cars_map.*

class BrowseCarsMapActivity(private var delegate: LocationClient = LocationClientImpl()) :
        LocationClient by delegate, AppCompatActivity(), OnMapReadyCallback {

    lateinit var adapter: CarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_cars_map)
        init(this)
        supportActionBar ?: setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.title = null
        }
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        adapter = CarsAdapter(this)
        carsList.adapter = adapter
        carsList.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false)
        carsList.itemAnimator = DefaultItemAnimator()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        var list: List<Any> = listOf(Any(), Any(), Any(), Any(), Any(), Any(), Any())
        adapter.setItems(list)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
