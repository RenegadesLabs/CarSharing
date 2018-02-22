package com.cardee.renter_book_car.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import kotlinx.android.synthetic.main.activity_book_payment.*

class BookPaymentActivity : AppCompatActivity(), BookPaymentView, PaymentAdapter.ItemClickListener {
    var mAcceptCash: Boolean = false
    lateinit var mSelectedCard: String
    val mPresenter = BookPaymentPresenter(this)
    private var mCurrentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_payment)
        initToolBar()
        getIntentData()
        initList()
        getMethods()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun getIntentData() {
        mAcceptCash = intent.getBooleanExtra("acceptCash", false)
        mSelectedCard = intent.getStringExtra("cardToken")
    }

    private fun initList() {
        paymentList.setHasFixedSize(true)
        val adapter = PaymentAdapter(this, mAcceptCash, mSelectedCard)
        adapter.setClickListener(this)
        paymentList.adapter = adapter
        mPresenter.setAdapter(adapter)
        paymentList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        paymentList.addItemDecoration(dividerItemDecoration)
    }

    private fun getMethods() {
        mPresenter.getMethods()
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent()
        intent.putExtra("method", mPresenter.getAdapter()?.getItem(position)?.first)
        intent.putExtra("token", mPresenter.getAdapter()?.getItem(position)?.second)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showMessage(message: String?) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
