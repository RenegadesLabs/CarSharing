package com.cardee.renter_browse_cars_map

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject


class CarsAdapter(context: Context) : RecyclerView.Adapter<CarViewHolder>() {

    private val items: MutableList<OfferItem>
    private val inflater: LayoutInflater
    private val subject: PublishSubject<UIModelEvent>
    private var disposable: Disposable = Disposables.empty()

    init {
        items = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        subject = PublishSubject.create()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder {
        var itemView = inflater.inflate(R.layout.item_car_renter_map, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder?.bind(items[position], position)
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

class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(offerItem: OfferItem, position: Int) = with(itemView) {
        offerItem.offer.let {

        }
    }
}