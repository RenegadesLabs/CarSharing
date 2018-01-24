package com.cardee.renter_browse_cars_map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.ViewGroup
import com.cardee.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class MarkerRenderer<T>(context: Context, map: GoogleMap, manager: ClusterManager<MapManager.MarkerItem<T>>) :
        DefaultClusterRenderer<MapManager.MarkerItem<T>>(context, map, manager) {

    private val markerIcon: Bitmap
    private val selectedMarkerIcon: Bitmap
    private val markers: MutableMap<Int, MarkerOptions?> = mutableMapOf()
    private var previous: MarkerOptions? = null
    private val iconGenerator: IconGenerator
    private var clusterIconView: ClusterIconView

    init {
        val idleBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker)
        val selectedBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker_selected)
        markerIcon = Bitmap.createScaledBitmap(idleBitmap, 128, 128, false)
        selectedMarkerIcon = Bitmap.createScaledBitmap(selectedBitmap, 128, 128, false)
        iconGenerator = IconGenerator(context)
        clusterIconView = ClusterIconView(context)
        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(164, 164)
        clusterIconView.layoutParams = params
    }

    override fun onBeforeClusterItemRendered(item: MapManager.MarkerItem<T>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions?.icon(if (item?.isSelected() == true)
            BitmapDescriptorFactory.fromBitmap(selectedMarkerIcon) else
            BitmapDescriptorFactory.fromBitmap(markerIcon))
        markers.put(item?.getId()!!, markerOptions)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MapManager.MarkerItem<T>>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)
        val size = cluster?.size
        clusterIconView.setTitle(size?.toString())
        iconGenerator.setContentView(clusterIconView)
        iconGenerator.setBackground(null)
        val icon = iconGenerator.makeIcon()
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    fun renderSelection(id: Int) {
        val selected = markers[id]
    }
}
