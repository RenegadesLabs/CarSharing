package com.cardee.owner_credit_balance.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cardee.R
import com.cardee.account_verify.credit_card.CreditCardActivity
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.ChildListener
import com.cardee.owner_credit_balance.State
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.owner_credit_balance.view.adapter.CardListAdapter
import com.cardee.util.ui.InputInteractionController
import com.cardee.util.ui.input_strategy.StrategyFactory
import kotlinx.android.synthetic.main.fragment_card_transfer.*


class CardTransactionFragment : Fragment(), BalanceTransactions.View<List<CardsResponseBody>> {

    private val presenter = TransactionsPresenter.useInstance()
    private lateinit var cardAdapter: CardListAdapter
    private lateinit var controller: InputInteractionController
    private lateinit var mode: BalanceTransactions.Mode
    private lateinit var listener: ChildListener
    private var toast: Toast? = null
    private var paymentToken: String? = null
    private var amount: Long? = null

    companion object {

        const val ADD_CARD_REQUEST = 101

        fun newInstance(mode: BalanceTransactions.Mode = BalanceTransactions.Mode.CREDIT): Fragment {
            val fragment = CardTransactionFragment()
            val args = Bundle()
            args.putSerializable(TransactionsPresenter.MODE, mode)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as ChildListener
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        listener = activity as ChildListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = arguments.getSerializable(TransactionsPresenter.MODE) as BalanceTransactions.Mode
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cardAdapter = CardListAdapter(inflater = inflater)
        controller = InputInteractionController()
        cardAdapter.addCardListener = {
            val addCardIntent = Intent(activity, CreditCardActivity::class.java)
            startActivityForResult(addCardIntent, ADD_CARD_REQUEST)
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
            if (loadingIndicator.visibility == View.VISIBLE) {
                return@setOnClickListener
            }
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
            args.putSerializable(TransactionsPresenter.MODE, mode)
            presenter.onCardChargeSubmit(this, args)
        }
    }

    override fun onStart() {
        super.onStart()
        val state = when (mode) {
            BalanceTransactions.Mode.DEPOSIT_CARD -> State.DEPOSIT
            else -> State.CARD
        }
        listener.onStateChanged(state)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            presenter.fetchCards(this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResult(result: List<CardsResponseBody>) {
        cardAdapter.addItems(result)
    }

    override fun onFinish() {
        activity.onBackPressed()
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