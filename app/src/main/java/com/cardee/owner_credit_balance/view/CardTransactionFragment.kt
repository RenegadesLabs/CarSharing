package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cardee.R
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.owner_credit_balance.view.adapter.CardListAdapter
import com.cardee.util.ui.InputInteractionController
import com.cardee.util.ui.input_strategy.StrategyFactory
import kotlinx.android.synthetic.main.fragment_card_transfer.*


class CardTransactionFragment : Fragment(), BalanceTransactions.View<List<CardsResponseBody>> {

    private val presenter = TransactionsPresenter.useInstance()
    private lateinit var cardAdapter: CardListAdapter
    private lateinit var controller: InputInteractionController
    private var toast: Toast? = null
    private var paymentToken: String? = null
    private var amount: Long? = null

    companion object {

        fun newInstance(): Fragment {
            return CardTransactionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cardAdapter = CardListAdapter(inflater = inflater)
        controller = InputInteractionController()
        cardAdapter.addCardListener = {

        }
        cardAdapter.selectListener = { token ->
            paymentToken = token

        }
        return inflater.inflate(R.layout.fragment_card_transfer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.addInput(cardTransferAmount, StrategyFactory.newPriceStrategy(2, { value ->
            amount = if (value.isEmpty()) {
                null
            } else {
                (value.toDouble() * 100).toLong()
            }
        }))

        with(selectCardList) {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        presenter.fetchCards(this)

        btnCardSubmit.setOnClickListener {
            if (amount == null || amount == 0L) {
                onError("Please enter transaction amount")
                return@setOnClickListener
            }
            if (paymentToken == null) {
                onError("Please select card or add new")
                return@setOnClickListener
            }
            val args = Bundle()
            args.putLong(TransactionsPresenter.AMOUNT, amount!!)
            args.putString(TransactionsPresenter.TOKEN, paymentToken)
            presenter.onCardChargeSubmit(this, args)
        }
    }

    override fun onResult(result: List<CardsResponseBody>) {
        cardAdapter.addItems(result)
    }

    override fun showProgress(isShowing: Boolean) {
        loadingIndicator.visibility = if (isShowing) View.VISIBLE else View.GONE
    }

    override fun onError(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onEmpty() {

    }
}