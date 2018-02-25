package com.cardee.owner_credit_balance.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.owner_credit_balance.BalanceTransactions
import kotlinx.android.synthetic.main.activity_deposit.*


class DepositActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)
        supportActionBar ?: initToolbar()
        initClickBehaviour()
    }

    private fun initToolbar() {
        setSupportActionBar(depositToolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = null
        }
    }

    private fun initClickBehaviour() {
        btnDepositBankTransfer.setOnClickListener(this)
        btnDepositCardTransaction.setOnClickListener(this)
        depositToolbarAction.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        view ?: return
        when (view) {
            btnDepositBankTransfer -> {
                val bankIntent = Intent(this, BalanceActivity::class.java)
                bankIntent.putExtra(BalanceActivity.MODE, BalanceTransactions.Mode.DEPOSIT_BANK)
                startActivity(bankIntent)
            }
            btnDepositCardTransaction -> {
                val cardIntent = Intent(this, BalanceActivity::class.java)
                cardIntent.putExtra(BalanceActivity.MODE, BalanceTransactions.Mode.DEPOSIT_CARD)
                startActivity(cardIntent)
            }
            depositToolbarAction -> onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId.let {
            when (it) {
                android.R.id.home -> onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}