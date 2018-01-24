package com.cardee.renter_browse_cars_map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cardee.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager


class MapManager<T>(context: Context, private val map: GoogleMap) {

    private val manager: ClusterManager<ClusterItem>
    private val markerIcon: Bitmap
    private val selectedMarkerIcon: Bitmap
    private val counterIcon: Bitmap
    private val markers: MutableMap<Int, MarkerItem<T>> = mutableMapOf()
    private var selectedMarker: MarkerItem<T>? = null

    init {
        manager = ClusterManager(context, map)
        markerIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker)
        selectedMarkerIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker_selected)
        counterIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker)
        map.setOnCameraIdleListener(manager)
        map.setOnMarkerClickListener(manager)
    }

    fun populate(list: List<T>, creator: (T) -> MarkerItem<T>) {
        list.forEach {
            val marker = creator.invoke(it)
            manager.addItem(marker)
            markers.put(marker.getId(), marker)
        }
        manager.cluster()
    }

    fun select(id: Int) {

    }

    private fun mapFocus(map: GoogleMap?, marker: Marker?) {
        map ?: return
        marker?.let {
            val zoom = map.cameraPosition.zoom
            val cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(CameraPosition.builder()
                            .zoom(if (zoom < 15) 15f else zoom)
                            .target(it.position)
                            .build())
            map.animateCamera(cameraUpdate)
        }
    }

    fun clear() {

    }

    abstract class MarkerItem<B>(val base: B) : ClusterItem {

        abstract fun getId(): Int

    }
}
