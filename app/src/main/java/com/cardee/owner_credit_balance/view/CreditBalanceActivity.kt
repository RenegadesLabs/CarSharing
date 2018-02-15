package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.owner_credit_balance.CreditBalanceParent
import com.cardee.owner_credit_balance.ChildListener
import com.cardee.owner_credit_balance.State
import com.cardee.owner_credit_balance.presenter.CreditBalancePresenter
import kotlinx.android.synthetic.main.activity_current_balance.*


class CreditBalanceActivity(val presenter: CreditBalanceParent.Presenter = CreditBalancePresenter(),
                            baseActionDelegate: BaseActionsView = BaseActionDelegate()) :
        AppCompatActivity(), CreditBalanceParent.View, ChildListener, BaseActionsView by baseActionDelegate {

    private var hasFragment: Boolean = false
    private val addFragmentAction = { fragment: Fragment, tag: String ->
        supportFragmentManager
                .beginTransaction()
                .add(fragmentContainer.id, fragment, tag)
                .commit()
    }
    private val replaceFragmentAction = { fragment: Fragment, tag: String ->
        supportFragmentManager
                .beginTransaction()
                .replace(fragmentContainer.id, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_balance)
        supportActionBar ?: initToolbar()
        initProgressView(loadingIndicator)
        presenter.attachView(this)
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchCurrentBalance()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        hasFragment = true
    }

    override fun onResult(balance: String) {
        balanceValue.text = balance
        if (balanceValue.visibility != View.VISIBLE) {
            balanceValue.visibility = View.VISIBLE
            balanceTitle.visibility = View.VISIBLE
            currencyValue.visibility = View.VISIBLE
        }
        showProgress(false)
    }

    override fun initState() {
        onChangeState(State.HOME)
    }

    override fun onChangeState(state: State) {
        val fragment = when (state) {
            State.HOME -> CreditBalanceHomeFragment.newInstance()
            State.BANK -> BankTransferFragment.newInstance()
            State.CARD -> CardTransactionFragment.newInstance()
            State.HISTORY -> TransactionHistoryFragment.newInstance()
        }
        if (hasFragment.not()) {
            addFragmentAction.invoke(fragment, state.tag)
        } else {
            replaceFragmentAction.invoke(fragment, state.tag)
        }
    }

    override fun onStateChanged(state: State) {
        toolbarTitle.text = getString(state.titleId)
    }

    override fun onTaskDone() {
        supportFragmentManager.popBackStack()
        presenter.fetchCurrentBalance()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}