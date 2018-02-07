package com.cardee.renter_book_car.payment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.cardee.R
import kotlinx.android.synthetic.main.activity_book_payment.*

class BookPaymentActivity : AppCompatActivity(), BookPaymentView {
    var mAcceptCash: Boolean = false
    val mPresenter = BookPaymentPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_payment)
        getIntentData()
        getMethods()
        initList()
    }

    private fun getIntentData() {
        mAcceptCash = intent.getBooleanExtra("acceptCash", false)
    }

    private fun getMethods() {
        mPresenter.getMethods()
    }

    private fun initList() {
        paymentList.setHasFixedSize(true)
        paymentList.adapter = PaymentAdapter(this, )
        paymentList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        paymentList.addItemDecoration(dividerItemDecoration)
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showMessage(message: String?) {

    }

    override fun showMessage(messageId: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
