package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.cardee.R
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.owner_credit_balance.view.adapter.CardListAdapter
import kotlinx.android.synthetic.main.fragment_bank_transfer.*
import kotlinx.android.synthetic.main.fragment_card_transfer.*


class CardTransactionFragment : Fragment(), BalanceTransactions.View<List<CardsResponseBody>> {

    private val presenter = TransactionsPresenter.useInstance()
    lateinit var cardAdapter: CardListAdapter
    private var toast: Toast? = null

    companion object {

        fun newInstance(): Fragment {
            return CardTransactionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cardAdapter = CardListAdapter(inflater = inflater)
        cardAdapter.addCardListener = {} //implement
        cardAdapter.selectListener = {} //implement
        return inflater.inflate(R.layout.fragment_card_transfer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchCards(this)
        with(selectCardList) {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onResult(result: List<CardsResponseBody>) {
        cardAdapter.addItems(result)
    }

    override fun showProgress(isShowing: Boolean) {

    }

    override fun onError(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onEmpty() {

    }
}