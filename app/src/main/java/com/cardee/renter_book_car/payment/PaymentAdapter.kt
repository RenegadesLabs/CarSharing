package com.cardee.renter_book_car.payment

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cardee.CardeeApp
import com.cardee.R
import kotlinx.android.synthetic.main.item_list_payment_method.view.*


class PaymentAdapter(context: Context, acceptCash: Boolean, selected: String) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {
    val mContext = context
    val mAcceptCash = acceptCash
    val mSelected = selected
    var mData: List<String>? = null
    var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_list_payment_method, parent, false)
        return PaymentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return (mData?.size ?: 0) + 1
    }

    fun getItem(id: Int): String {
        if (id == 0) {
            return "Cash"
        }
        return mData?.get(id - 1) ?: ""
    }

    override fun onBindViewHolder(holder: PaymentViewHolder?, position: Int) {
        if (position == 0) {
            holder?.textView?.text = "Cash"
            if (!mAcceptCash) {
                holder?.textView?.setTextColor(CardeeApp.context.resources.getColor(R.color.text_disabled))
            }
        } else {
            val item = mData?.get(position - 1) ?: return
            holder?.textView?.text = item
        }
        if (mSelected == holder?.textView?.text) {
            holder.checkMarck?.visibility = View.VISIBLE
        }
    }

    fun setData(data: List<String>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class PaymentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView? = itemView?.cardText
        val checkMarck: AppCompatImageView? = itemView?.checkMark

        init {
            itemView?.setOnClickListener { view ->
                (if (adapterPosition == 0 && mAcceptCash.not()) {
                    return@setOnClickListener
                } else {
                    mClickListener?.onItemClick(view, adapterPosition)
                })
            }
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}