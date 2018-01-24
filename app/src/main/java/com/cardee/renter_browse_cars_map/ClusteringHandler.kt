package com.cardee.renter_browse_cars_map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager


class ClusteringHandler(val context: Context, val map: GoogleMap) {

    val manager: ClusterManager<ClusterMarker>

    init {
        manager = ClusterManager(context, map)
        map.setOnCameraIdleListener(manager)
        map.setOnMarkerClickListener(manager)
    }

    fun addMarker(marker: Marker) {
        manager.addItem(ClusterMarker(marker))
    }

    class ClusterMarker(val marker: Marker) : ClusterItem {

        override fun getSnippet(): String = marker.snippet

        override fun getTitle(): String = marker.title

        override fun getPosition(): LatLng = marker.position

    }
}