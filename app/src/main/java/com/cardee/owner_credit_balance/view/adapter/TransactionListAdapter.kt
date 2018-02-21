package com.cardee.owner_credit_balance.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TransactionListAdapter(val items: List<Any> = mutableListOf(),
                             val inflater: LayoutInflater) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionHolder {
        return null!!
    }

    override fun onBindViewHolder(holder: TransactionHolder?, position: Int) {

    }

    override fun getItemCount(): Int = items.size


    class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {

        }
    }
}
