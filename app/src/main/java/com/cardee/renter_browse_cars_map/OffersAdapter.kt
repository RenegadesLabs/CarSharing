package com.cardee.renter_browse_cars_map

import android.content.Context
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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_car_renter_map.view.*
import java.lang.Exception


class OffersAdapter(context: Context) : RecyclerView.Adapter<CarViewHolder>() {

    private val items: MutableList<OfferItem>
    private val inflater: LayoutInflater
    private val subject: PublishSubject<UIModelEvent>
    private var disposable: Disposable = Disposables.empty()
    private val imageManager: RequestManager
    private var selectedPosition: Int = 0
    private val favIcon: Drawable
    private val favIconSelected: Drawable
    private var recyclerView: RecyclerView? = null
    private var scrollHandler: RecyclerAutoscrollHandler? = null
    private var manager: MapManager<OfferItem>? = null

    init {
        items = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        subject = PublishSubject.create()
        imageManager = Glide.with(context)
        val res = context.resources
        favIcon = VectorDrawableCompat.create(res, R.drawable.ic_favorite, null) as Drawable
        favIconSelected = VectorDrawableCompat.create(res, R.drawable.ic_favorite_filled, null) as Drawable
    }

    fun initScrollingBehaviour(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        scrollHandler = RecyclerAutoscrollHandler(recyclerView)
        scrollHandler?.subscribe({ position: Int ->
            onSelect(position)
            val id = items[position].id ?: -1
            manager?.mapFocus(id)
            manager?.select(id)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder {
        val itemView = inflater.inflate(R.layout.item_car_renter_map, parent, false)
        return CarViewHolder(itemView, imageManager, favIcon, favIconSelected)
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder?.apply {
            val model = items[position]
            bind(model)
            itemView.setOnClickListener { view ->
                subject.onNext(UIModelEvent(UIModelEvent.EVENT_OFFER_LIST_CLICK, view, model))
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(offerItems: List<OfferItem>) {
        items.clear()
        items.addAll(offerItems)
        manager?.clear()
        notifyDataSetChanged()
        onSelect(selectedPosition)
        moveToSelection()
        if (manager != null) {
            initMapContent(items)
        }
    }

    private fun onSelect(position: Int) {
        items[selectedPosition].selected = false
        items[position].selected = true
        notifyItemChanged(selectedPosition)
        notifyItemChanged(position)
        selectedPosition = position
    }

    private fun onSelected(item: OfferItem) {
        selectedPosition = items.indexOf(item)
        moveToSelection()
    }

    private fun moveToSelection() {
        recyclerView?.scrollToPosition(selectedPosition)
    }

    fun initMapContent(context: Context, map: GoogleMap) {
        manager = MapManager(context, map)
        manager?.subscribe { item: MapManager.MarkerItem<OfferItem> ->
            items[selectedPosition].selected = false
            item.base.selected = true
            onSelected(item.base)
        }
        if (items.isNotEmpty()) {
            initMapContent(items)
        }
    }

    private fun initMapContent(items: List<OfferItem>) {
        manager?.clear()
        items.filter { item -> item.id != null }
                .filter { item ->
                    val offer = item.offer
                    offer.latitude != null && offer.longitude != null
                }.let { list ->
            manager?.populate(list, { item ->
                object : MapManager.MarkerItem<OfferItem>(item) {

                    override fun getId(): Int = base.id!!

                    override fun isSelected(): Boolean = base.selected

                    override fun getSnippet(): String = ""

                    override fun getTitle(): String = base.offer.title ?: ""

                    override fun getPosition(): LatLng {
                        val offer = base.offer
                        return LatLng(offer.latitude, offer.longitude)
                    }
                }
            })
        }
    }

    fun subscribe(consumer: Consumer<UIModelEvent>) {
        disposable = subject.subscribe(consumer)
    }

    fun unsubscribe() {
        scrollHandler?.unsubscribe()
        manager?.unsubscribe()
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
        val car = offerItem.offer
        selectedMarker.visibility = if (offerItem.selected) View.VISIBLE else View.GONE
        imgProgress.visibility = View.VISIBLE
        val link = car.images?.firstOrNull { image -> image.isPrimary }?.thumbnail
        imageManager
                .load(link ?: "")
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

        title.text = car.title
        info.text = " ${INFO_STRING_DOT} ${car?.yearOfManufacture} ${INFO_STRING_STAR}"
        car.isFavorite?.let { favorite ->
            imgFavorite.setImageDrawable(if (favorite) favIconSelected else favIcon)
        }
    }
}