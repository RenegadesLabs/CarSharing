package com.cardee.renter_browse_cars_map

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

interface LocationClient : GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    fun init(context: Context)

    fun doOnConnect(action: () -> Unit)

    fun connect()

    fun disconnect()

    fun obtainClient(): GoogleApiClient
}

class LocationClientImpl : LocationClient {

    lateinit var apiClient: GoogleApiClient
    var doOnConnect: () -> Unit = {}

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

    override fun doOnConnect(action: () -> Unit) {
        if (apiClient.isConnected) {
            action.invoke()
        } else doOnConnect = action
    }

    override fun onConnected(p0: Bundle?) {
        doOnConnect.invoke()
    }

    override fun onConnectionSuspended(p0: Int) {
        apiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun obtainClient(): GoogleApiClient {
        return apiClient
    }
}
