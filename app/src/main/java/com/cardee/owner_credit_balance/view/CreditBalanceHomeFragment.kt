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

    val feePlug = 4 //PLUG
    val minimumDepositPlug = "$20" //PLUG

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
        val placeholder = activity.getString(R.string.credit_balance_placeholder)
        val cardFeeInfo = activity.getString(R.string.transaction_fee_amount)
        val cardFeeCaution = activity.getString(R.string.transaction_fee_caution)
        val minimumBalanceCaution = activity.getString(R.string.minimum_balance_caution)
        cardTransactionSubtitle.text = cardFeeInfo.replace(placeholder, feePlug.toString())
        balanceCaution.text = minimumBalanceCaution.replace(placeholder, minimumDepositPlug)
        balanceCautionAdditional.text = cardFeeCaution
    }

    override fun onClick(view: View?) {
        when (view) {
            btnBankTransfer -> parent?.onChangeState(State.BANK)
            btnCardTransaction -> parent?.onChangeState(State.CARD)
            btnTransactionHistory -> parent?.onChangeState(State.HISTORY)
        }
    }
}