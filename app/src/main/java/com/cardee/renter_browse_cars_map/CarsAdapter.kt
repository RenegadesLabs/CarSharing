package com.cardee.renter_browse_cars_map

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.cardee.data_source.remote.api.common.entity.ImageEntity
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_car_renter_map.view.*
import java.lang.Exception


class CarsAdapter(context: Context) : RecyclerView.Adapter<CarViewHolder>() {

    private val items: MutableList<OfferItem>
    private val inflater: LayoutInflater
    private val subject: PublishSubject<UIModelEvent>
    private var disposable: Disposable = Disposables.empty()
    private val imageManager: RequestManager
    private val favIcon: Drawable
    private val favIconSelected: Drawable

    init {
        items = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        subject = PublishSubject.create()
        imageManager = Glide.with(context)
        favIcon = VectorDrawableCompat.create(context.resources, R.drawable.ic_favorite, null) as Drawable
        favIconSelected = VectorDrawableCompat.create(context.resources, R.drawable.ic_favorite_selected, null) as Drawable
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
                    val imageManager: RequestManager,
                    val favIcon: Drawable,
                    val favIconSelected: Drawable)
    : RecyclerView.ViewHolder(itemView) {

    companion object {
        val INFO_STRING_DOT = "\u2022"
        val INFO_STRING_STAR = "\u2605"
    }

    fun bind(offerItem: OfferItem) = with(itemView) {
        val car = offerItem.offer.carDetails
        car ?: return@with
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