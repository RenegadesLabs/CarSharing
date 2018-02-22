package com.cardee.account_details.adapter

import android.content.Context
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cardee.R
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import kotlinx.android.synthetic.main.item_list_credit_card.view.*


class CardsAdapter(var context: Context?) : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {

    var dataList: List<CardsResponseBody>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_list_credit_card, parent, false)
        return CardsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CardsViewHolder?, position: Int) {
        val item = dataList?.get(position) ?: return
        holder?.cardNumberText?.text = context?.getString(R.string.credit_card_template)?.format(item.cardNumber)
        holder?.primaryMarkText?.visibility = if (item.default) {
            View.VISIBLE
        } else {
            View.GONE
        }
        when (item.brand) {
            "Visa" -> {
                holder?.brand?.setImageDrawable(VectorDrawableCompat.create(context?.resources
                        ?: return, R.drawable.ic_visa, null))
            }
            "MasterCard" -> {
                holder?.brand?.setImageDrawable(VectorDrawableCompat.create(context?.resources
                        ?: return, R.drawable.ic_master_card, null))
            }
            "AmericanExpress" -> {
                holder?.brand?.setImageDrawable(VectorDrawableCompat.create(context?.resources
                        ?: return, R.drawable.ic_amex, null))
            }
            "Discover" -> {
                holder?.brand?.setImageDrawable(VectorDrawableCompat.create(context?.resources
                        ?: return, R.drawable.ic_discover, null))
            }
            "DinersClub" -> {
                holder?.brand?.setImageDrawable(VectorDrawableCompat.create(context?.resources
                        ?: return, R.drawable.ic_dinersclub, null))
            }
        }
    }

    fun setData(list: List<CardsResponseBody>) {
        dataList = list
        notifyDataSetChanged()
    }

    fun onDestroy() {
        context = null
    }

    inner class CardsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val cardNumberText: TextView? = itemView?.cardNumber
        val primaryMarkText: TextView? = itemView?.primaryMark
        val brand: ImageView? = itemView?.brandIcon
    }
}