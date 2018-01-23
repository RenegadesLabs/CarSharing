package com.cardee.renter_browse_cars_map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_car_renter_map.view.*
import java.lang.Exception


class CarsAdapter(context: Context) : RecyclerView.Adapter<CarViewHolder>(), GoogleMap.OnMarkerClickListener {

    private val items: MutableList<OfferItem>
    private val inflater: LayoutInflater
    private val subject: PublishSubject<UIModelEvent>
    private var disposable: Disposable = Disposables.empty()
    private val imageManager: RequestManager
    private var selectedPosition: Int = 0
    private var selectedMarker: Marker? = null
    private val favIcon: Drawable
    private val favIconSelected: Drawable
    private val idleMarkerIcon: Bitmap
    private val selectedMarkerIcon: Bitmap
    private var recyclerView: RecyclerView? = null
    private var googleMap: GoogleMap? = null

    init {
        items = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        subject = PublishSubject.create()
        imageManager = Glide.with(context)
        val res = context.resources
        favIcon = VectorDrawableCompat.create(res, R.drawable.ic_favorite, null) as Drawable
        favIconSelected = VectorDrawableCompat.create(res, R.drawable.ic_favorite_selected, null) as Drawable
        var idleBitmap = BitmapFactory.decodeResource(res, R.drawable.ic_car_marker)
        var selectedBitmap = BitmapFactory.decodeResource(res, R.drawable.ic_car_marker_selected)
        idleMarkerIcon = Bitmap.createScaledBitmap(idleBitmap, 128, 128, false)
        selectedMarkerIcon = Bitmap.createScaledBitmap(selectedBitmap, 128, 128, false)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder {
        var itemView = inflater.inflate(R.layout.item_car_renter_map, parent, false)
        return CarViewHolder(itemView, imageManager, favIcon, favIconSelected)
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(offerItems: List<OfferItem>) {
        items.clear()
        items.addAll(offerItems)
        notifyDataSetChanged()
        onSelect(selectedPosition)
        initMarkers()
    }

    fun onSelect(position: Int) {
        val idle = items[selectedPosition]
        val selected = items[position]
        items[selectedPosition] = idle.copy(selected = false)
        items[position] = selected.copy(selected = true)
        notifyItemChanged(selectedPosition)
        notifyItemChanged(position)
        selectedPosition = position
        recyclerView?.smoothScrollToPosition(selectedPosition)
    }

    fun setGoogleMap(map: GoogleMap) {
        googleMap = map
        initMarkers()
    }

    private fun initMarkers() {
        if (items.isEmpty() || googleMap == null) return
        googleMap?.setOnMarkerClickListener(this)
        var index = 0
        items.filter { item ->
            if (item.offer.carDetails == null) {
                false
            } else {
                val carDetails = item.offer.carDetails
                carDetails.latitude != null && carDetails.longitude != null
            }
        }.forEach { item ->
            val carDetails = item.offer.carDetails
            val bitmap = if (item.selected) selectedMarkerIcon else idleMarkerIcon
            val markerOptions = MarkerOptions()
                    .position(LatLng(carDetails!!.latitude!!, carDetails.longitude!!))
                    .title(carDetails.carTitle)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            val marker = googleMap?.addMarker(markerOptions)
            if (selectedPosition == index) {
                selectedMarker = marker
            }
            marker?.tag = item.id
            index++
        }
        mapFocus(googleMap)
    }

    private fun mapFocus(map: GoogleMap?) {
        map?.let {
            val zoom = it.cameraPosition.zoom
            val cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(CameraPosition.builder()
                            .zoom(if (zoom < 15) 15f else zoom)
                            .build())
            map.moveCamera(cameraUpdate)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val id = marker?.tag as Int
        val selected = items.find { id.equals(it.id) }
        val newPosition = items.indexOf(selected)
        if (newPosition > -1) {
            selectedMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(idleMarkerIcon))
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(selectedMarkerIcon))
            selectedMarker = marker
            onSelect(newPosition)
        }
        return false
    }

    fun subscribe(consumer: Consumer<UIModelEvent>) {
        disposable = subject.subscribe(consumer)
    }

    fun unsubscribe() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}

class CarViewHolder(itemView: View,
                    private val imageManager: RequestManager,
                    private val favIcon: Drawable,
                    private val favIconSelected: Drawable) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val INFO_STRING_DOT = "\u2022"
        val INFO_STRING_STAR = "\u2605"
    }

    fun bind(offerItem: OfferItem) = with(itemView) {
        val car = offerItem.offer.carDetails
        car ?: return@with
        selectedMarker.visibility = if (offerItem.selected) View.VISIBLE else View.GONE
        imgProgress.visibility = View.VISIBLE
        var link = car.images?.firstOrNull { image -> image.isPrimary }?.link
        imageManager
                .load(if (link == null) "" else link)
                .listener(object : RequestListener<String?, GlideDrawable> {
                    override fun onException(e: Exception?,
                                             model: String?,
                                             target: Target<GlideDrawable>?,
                                             isFirstResource: Boolean): Boolean {
                        imgProgress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable?,
                                                 model: String?,
                                                 target: Target<GlideDrawable>?,
                                                 isFromMemoryCache: Boolean,
                                                 isFirstResource: Boolean): Boolean {
                        imgProgress.visibility = View.GONE
                        return false
                    }
                })
                .error(R.drawable.img_no_car)
                .into(imgCar)

        title.text = car.carTitle
        info.text = " ${INFO_STRING_DOT} ${car?.carYear} ${INFO_STRING_STAR}"
        car.isFavorite?.let { favorite ->
            imgFavorite.setImageDrawable(if (favorite) favIconSelected else favIcon)
        }
    }
}