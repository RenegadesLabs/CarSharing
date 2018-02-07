package com.cardee.renter_book_car.payment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class PaymentAdapter(context: Context, data: List<String>) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {
    val mContext = context
    var mData = data
    var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return PaymentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun getItem(id: Int): String {
        return mData[id]
    }

    override fun onBindViewHolder(holder: PaymentViewHolder?, position: Int) {
        val item = mData[position]
        holder?.textView?.text = item

    }

    inner class PaymentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView as TextView

        init {
            textView.setOnClickListener { view -> mClickListener?.onItemClick(view, adapterPosition) }
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}