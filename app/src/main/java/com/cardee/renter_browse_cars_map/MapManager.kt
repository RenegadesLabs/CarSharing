package com.cardee.renter_browse_cars_map

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject


class MapManager<T>(context: Context, private val map: GoogleMap) {

    private val manager: ClusterManager<MarkerItem<T>>
    private val markers: MutableMap<Int, MarkerItem<T>> = mutableMapOf()
    private val renderer: MarkerRenderer<T>
    private val subject: PublishSubject<MarkerItem<T>> = PublishSubject.create()
    private var disposable: Disposable = Disposables.empty()

    init {
        manager = ClusterManager(context, map)
        map.setOnCameraIdleListener(manager)
        map.setOnMarkerClickListener(manager)
        renderer = MarkerRenderer(context, map, manager)
        manager.renderer = renderer
        manager.setOnClusterItemClickListener { item ->
            subject.onNext(item)
            renderer.renderSelection(item)
            false
        }
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
        val item = markers[id]
        if (item != null) {
            renderer.renderSelection(item)
        }
    }

    fun mapFocus(id: Int) {
        val marker = markers[id]
        marker ?: return
        val zoom = map.cameraPosition.zoom
        val cameraUpdate = CameraUpdateFactory
                .newCameraPosition(CameraPosition.builder()
                        .zoom(if (zoom < 15) 15f else zoom)
                        .target(marker.position)
                        .build())
        map.animateCamera(cameraUpdate)
    }

    fun subscribe(consumer: (MarkerItem<T>) -> Unit) {
        disposable = subject.subscribe(consumer)
    }

    fun unsubscribe() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun clear() {
        manager.clearItems()
        markers.clear()
    }

    abstract class MarkerItem<B>(val base: B) : ClusterItem {

        abstract fun getId(): Int

        abstract fun isSelected(): Boolean

    }
}
