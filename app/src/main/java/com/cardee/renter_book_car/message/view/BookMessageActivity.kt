package com.cardee.renter_book_car.message.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.renter_book_car.message.presenter.BookMessagePresenter
import kotlinx.android.synthetic.main.activity_book_message.*

class BookMessageActivity : AppCompatActivity(), BookMessageView {
    private var mPresenter: BookMessagePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_message)
        initToolbar()
        setListeners()
        mPresenter = BookMessagePresenter()
        mPresenter?.init(this)
    }

    private fun setListeners() {
        messageInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                messageHint.visibility = View.GONE
            } else {
                messageHint.visibility = View.VISIBLE
            }
        }
        addMessageButton.setOnClickListener {
            if (messageInput.text.isNotEmpty()) {
                mPresenter?.saveMessage(messageInput.text.toString())
            }
            finish()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun setText(noteText: String?) {
        messageInput.setText(noteText)
        messageHint.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showMessage(message: String?) {
    }

    override fun showMessage(messageId: Int) {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}