package com.cardee.owner_credit_balance.view.adapter

import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.cardee.R
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import kotlinx.android.synthetic.main.item_transaction_card.view.*


class CardListAdapter(private val items: MutableList<CardWrapper> = mutableListOf(),
                      private val inflater: LayoutInflater) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        items.add(CardWrapper(viewType = ADD_CARD))
    }

    companion object {
        private const val CARD = 0
        private const val ADD_CARD = 1
    }

    var selectListener: (String) -> Unit = {}
        set(value) {
            items.find { card -> card.selected }?.card?.
                    paymentToken?.let { token -> value.invoke(token) }
            field = value
        }
    var addCardListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            CARD -> {
                val cardView = inflater.inflate(R.layout.item_transaction_card, parent, false)
                CardViewHolder(cardView)
            }
            ADD_CARD -> {
                val addCardView = inflater.inflate(R.layout.item_add_card_view, parent, false)
                AddCardViewHolder(addCardView)
            }
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        items[position].let { item ->
            when (item.viewType) {
                CARD -> {
                    val cardHolder = holder as CardViewHolder
                    cardHolder.bind(item)
                    cardHolder.itemView.tag = item.card?.paymentToken
                    cardHolder.itemView.setOnClickListener { view ->
                        val token = view.tag
                        selectListener.invoke(token.toString())
                        val newSelected = items.indexOfFirst { item -> item.card!!.paymentToken == token }
                        val oldSelected = items.indexOfFirst { item -> item.selected }
                        items[newSelected] = items[newSelected].copy(selected = true)
                        items[oldSelected] = items[oldSelected].copy(selected = false)
                        notifyItemChanged(oldSelected)
                        notifyItemChanged(newSelected)
                    }
                }
                ADD_CARD -> {
                    holder?.itemView?.setOnClickListener { addCardListener.invoke() }
                }
                else -> return
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    fun addItem(card: CardsResponseBody) {
        items.add(0, CardWrapper(card))
        notifyItemInserted(0)
    }

    fun addItems(newItems: List<CardsResponseBody>) {
        val index = items.indexOfFirst { item -> item.viewType == ADD_CARD }
        items.addAll(index, newItems.flatMap { card ->
            listOf(CardWrapper(card, selected = card.default))
        })
        items.find { card -> card.selected }?.card?.paymentToken?.let(selectListener)
        notifyDataSetChanged()
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(wrapper: CardWrapper) = with(itemView) {
            wrapper.card?.let { card ->
                cardPrimary.visibility = if (card.default) View.VISIBLE else View.GONE
                cardSelected.visibility = if (wrapper.selected) View.VISIBLE else View.GONE
                onSetCardIcon(cardVendorImage, card.brand)
                val cardNumberString = "•••• ${card.cardNumber}"
                cardNumber.text = cardNumberString
            }
        }

        private fun onSetCardIcon(view: ImageView, cardVendor: String) {
            val res = view.context.resources
            when (cardVendor) {
                "Visa" -> {
                    val visaIcon = VectorDrawableCompat.create(res, R.drawable.ic_visa, null)
                    view.setImageDrawable(visaIcon)
                }
                "MasterCard" -> {
                    val masterCardIcon = VectorDrawableCompat.create(res, R.drawable.ic_master_card, null)
                    view.setImageDrawable(masterCardIcon)
                }
            }
        }
    }

    class AddCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    data class CardWrapper(val card: CardsResponseBody? = null,
                           val viewType: Int = CARD,
                           val selected: Boolean = false)
}