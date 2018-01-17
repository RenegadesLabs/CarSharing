package com.cardee.renter_browse_cars_map

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R


class CarsAdapter(context: Context) : RecyclerView.Adapter<CarViewHolder>() {

    private val items: MutableList<Any>
    private val inflater: LayoutInflater

    init {
        items = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder {
        var itemView = inflater.inflate(R.layout.item_car_renter_map, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(cars: List<Any>) {
        items.clear()
        items.addAll(cars)
        notifyDataSetChanged()
    }
}


class CarViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(car: Any) {

    }
}