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
import com.cardee.domain.rx.balance.Transaction
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.owner_credit_balance.view.adapter.TransactionListAdapter
import kotlinx.android.synthetic.main.fragment_transaction_history.*

class TransactionHistoryFragment : Fragment(), BalanceTransactions.View<List<Transaction>> {

    val presenter = TransactionsPresenter.useInstance()
    lateinit var transactionAdapter: TransactionListAdapter
    private var toast: Toast? = null

    companion object {

        fun newInstance(): Fragment {
            return TransactionHistoryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        transactionAdapter = TransactionListAdapter(inflater = inflater)
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(transactionRecycler) {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        presenter.fetchHistory(this)
    }

    override fun onResult(result: List<Transaction>) {
        transactionAdapter.addItems(result)
    }

    override fun onFinish() {

    }

    override fun showProgress(isShowing: Boolean) {

    }

    override fun onError(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onEmpty() {
        //Implement empty response UI later
    }
}