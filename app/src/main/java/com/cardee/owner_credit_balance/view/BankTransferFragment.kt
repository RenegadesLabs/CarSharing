package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.util.ui.InputInteractionController
import com.cardee.util.ui.input_strategy.StrategyFactory
import kotlinx.android.synthetic.main.fragment_bank_transfer.*


class BankTransferFragment : Fragment() {

    companion object {

        fun newInstance(): Fragment {
            return BankTransferFragment()
        }
    }

    private lateinit var presenter: TransactionsPresenter
    private lateinit var controller: InputInteractionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TransactionsPresenter()
        controller = InputInteractionController()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_bank_transfer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.addInput(transferAmount, StrategyFactory.newPriceStrategy())
        controller.addInput(transferNumber, StrategyFactory.newNumberStrategy())
    }
}