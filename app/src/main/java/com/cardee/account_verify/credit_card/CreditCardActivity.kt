package com.cardee.account_verify.credit_card

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.cardee.R
import com.cardee.util.display.ActivityHelper
import kotlinx.android.synthetic.main.activity_credit_card.*

class CreditCardActivity : AppCompatActivity(), CreditCardView {

    private var presenter: CreditCardPresenter? = null
    private var currentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)
        initToolBar()
        setListeners()
        initView()
        presenter = CreditCardPresenter(this)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun setListeners() {
        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (cardNumber.text.isNullOrEmpty()) {
                    showClearButton(false)
                } else {
                    showClearButton(true)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        clearButton.setOnClickListener {
            cardNumber.setText("")
        }

        card_cvv.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                card_cvv.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        cardExpiration.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, position: Int, before: Int, action: Int) {
                if (position == 1) {
                    if (!s.toString().endsWith(" ")) {
                        cardExpiration.append(" / ")
                    }
                }
            }
        })

        description.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                ActivityHelper.hideSoftKeyboard(this)
            }
        }
    }

    private fun initView() {
        cardNumber.requestFocus()

        val termsCond = SpannableString(resources.getString(R.string.credit_card_terms_second))
        termsCond.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimaryDark)), 0, termsCond.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        termsCond.setSpan(UnderlineSpan(), 0, termsCond.length, 0)
        termsConditions.append(termsCond)
    }

    private fun showClearButton(show: Boolean) {
        if (show) {
            if (clearButton.visibility == View.GONE) {
                clearButton.visibility = View.VISIBLE
            }
        } else {
            if (clearButton.visibility == View.VISIBLE) {
                clearButton.visibility = View.GONE
            }
        }
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showMessage(message: String?) {
        currentToast?.cancel()
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
