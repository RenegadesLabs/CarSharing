package com.cardee.owner_credit_balance.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R
import com.cardee.owner_credit_balance.ChildListener
import com.cardee.owner_credit_balance.State
import kotlinx.android.synthetic.main.fragment_balance_home.*


class CreditBalanceHomeFragment : Fragment(), View.OnClickListener {

    companion object {

        fun newInstance(): Fragment {
            return CreditBalanceHomeFragment()
        }
    }

    private var parent: ChildListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = context as ChildListener
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        parent = context as ChildListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_balance_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parent?.onStateChanged(State.HOME)
        btnBankTransfer.setOnClickListener(this)
        btnCardTransaction.setOnClickListener(this)
        btnTransactionHistory.setOnClickListener(this)
        initInfo()
    }

    private fun initInfo() {

    }

    override fun onClick(view: View?) {
        when (view) {
            btnBankTransfer -> parent?.onChangeState(State.BANK)
            btnCardTransaction -> parent?.onChangeState(State.CARD)
            btnTransactionHistory -> parent?.onChangeState(State.HISTORY)
        }
    }
}