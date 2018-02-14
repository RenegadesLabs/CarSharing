package com.cardee.owner_credit_balance.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.owner_credit_balance.CreditBalanceParent
import com.cardee.owner_credit_balance.State
import com.cardee.owner_credit_balance.presenter.CreditBalancePresenter
import kotlinx.android.synthetic.main.activity_current_balance.*


class CreditBalanceActivity(val presenter: CreditBalanceParent.Presenter = CreditBalancePresenter(),
                            baseActionDelegate: BaseActionsView = BaseActionDelegate()) :
        AppCompatActivity(), CreditBalanceParent.View, BaseActionsView by baseActionDelegate {

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

    override fun onResult(balance: String) {
        if (balanceValue.visibility != View.VISIBLE) {
            balanceValue.visibility = View.VISIBLE
            balanceTitle.visibility = View.VISIBLE
            currencyValue.visibility = View.VISIBLE
        }
        showProgress(false)
    }

    override fun initState(state: State) {
        toolbarTitle.text = getString(state.titleId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}