package com.cardee.owner_credit_balance.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cardee.R
import com.cardee.domain.rx.balance.Transaction
import com.cardee.util.SimpleIso8601DateFormatter
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class TransactionListAdapter(val items: MutableList<Transaction> = mutableListOf(),
                             val inflater: LayoutInflater) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionHolder {
        val rootView = inflater.inflate(R.layout.item_transaction, parent, false)
        return TransactionHolder(rootView)
    }

    override fun onBindViewHolder(holder: TransactionHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addItems(newItems: List<Transaction>) {
        val start = items.count()
        items.addAll(newItems)
        notifyItemRangeInserted(start, items.size - 1)
    }

    class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {

            private val formatter = DecimalFormat("######.00")

            init {
                val symbols = DecimalFormatSymbols()
                symbols.decimalSeparator = '.'
                formatter.decimalFormatSymbols = symbols
            }

            fun onFormatAmount(view: TextView, amount: Double) {
                val sign = if (amount < 0) {
                    view.context?.let { context ->
                        view.setTextColor(ContextCompat.getColor(context, R.color.negative_value_red))
                    }
                    "-"
                } else {
                    view.context?.let { context ->
                        view.setTextColor(ContextCompat.getColor(context, R.color.positive_value_green))
                    }
                    "+"
                }
                val amountString = "${sign}SGD ${formatter.format(amount)}"
                view.text = amountString
            }
        }

        fun bind(transaction: Transaction) = with(itemView) {
            transaction.date?.let { date ->
                val formattedDate = SimpleIso8601DateFormatter
                        .useInstance().format(date, "d MMM | h:mma")
                transactionDate.text = formattedDate?.replace("|\\s0", "| ")
            }
            transactionType.text = transaction.type?.typeName
            transactionId.text = transaction.id.toString()
            transactionNumber.text = transaction.number
            transaction.amount?.let { amount ->
                var totalAmount = amount
                if (transaction.fee != null) {
                    totalAmount -= transaction.fee
                }
                onFormatAmount(transactionAmount, totalAmount.toDouble() / 100)
            }
        }
    }
}
