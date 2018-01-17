package com.cardee.renter_browse_cars_map

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

interface LocationClient : GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    fun init(context: Context)

    fun connect()

    fun disconnect()
}

class LocationClientImpl : LocationClient {

    lateinit var apiClient: GoogleApiClient

    override fun init(context: Context) {
        apiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    override fun connect() {
        apiClient.connect()
    }

    override fun disconnect() {
        apiClient.disconnect()
    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {
        apiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
