package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.owner_credit_balance.BalanceParent
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.ChildListener
import com.cardee.owner_credit_balance.State
import com.cardee.owner_credit_balance.presenter.BalancePresenter
import kotlinx.android.synthetic.main.activity_current_balance.*


class BalanceActivity(val presenter: BalanceParent.Presenter = BalancePresenter(),
                      baseActionDelegate: BaseActionsView = BaseActionDelegate()) :
        AppCompatActivity(), BalanceParent.View, ChildListener, BaseActionsView by baseActionDelegate {

    companion object {
        const val MODE = "balance_mode"
    }

    private lateinit var mode: BalanceTransactions.Mode
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
        mode = intent.getSerializableExtra(MODE) as BalanceTransactions.Mode
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
        presenter.fetchCurrentBalance(mode)
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
        when (mode) {
            BalanceTransactions.Mode.CREDIT -> onChangeState(State.HOME)
            BalanceTransactions.Mode.DEPOSIT_CARD -> onChangeState(State.CARD)
            BalanceTransactions.Mode.DEPOSIT_BANK -> onChangeState(State.BANK)
        }
    }

    override fun onChangeState(state: State) {
        val fragment = when (state) {
            State.HOME -> CreditBalanceHomeFragment.newInstance()
            State.BANK -> BankTransferFragment.newInstance(mode)
            State.CARD -> CardTransactionFragment.newInstance(mode)
            State.HISTORY -> TransactionHistoryFragment.newInstance()
            else -> throw IllegalArgumentException("Illegal state $state")
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
        presenter.fetchCurrentBalance(mode)
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