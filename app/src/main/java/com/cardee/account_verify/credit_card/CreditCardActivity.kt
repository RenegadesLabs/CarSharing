package com.cardee.account_verify.credit_card

import android.app.Activity
import android.content.Intent
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
import com.cardee.account_verify.view.VerifyAccountActivity
import com.cardee.owner_credit_balance.view.DepositActivity
import com.cardee.util.DateRepresentationDelegate
import com.cardee.util.display.ActivityHelper
import kotlinx.android.synthetic.main.activity_credit_card.*
import java.util.*

class CreditCardActivity : AppCompatActivity(), CreditCardView {

    private var presenter: CreditCardPresenter? = null
    private var currentToast: Toast? = null
    private var dateDelegate: DateRepresentationDelegate? = null
    private var action: Action? = null
    private var verify: Boolean = false
    private var forResult = false

    companion object {
        const val RESULT = "started_for_result"
    }

    enum class Action { NEXT, SAVE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)
        intent.extras?.apply {
            forResult = containsKey(RESULT)
        }
        if (forResult) nextActivityButton.visibility = View.GONE
        initToolBar()
        setListeners()
        getIntentData()
        initView()
        presenter = CreditCardPresenter(this)
        dateDelegate = DateRepresentationDelegate(this)
    }

    private fun getIntentData() {
        verify = intent.getBooleanExtra("isVerify", false)
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

        cardCvv.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                cardCvv.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        cardExpiration.addTextChangedListener(object : TextWatcher {
            internal var lengthBefore = 0

            override fun afterTextChanged(s: Editable) {
                if (lengthBefore < s.length) {
                    if (s.length == 2) {
                        s.append(" / ")
                    }
                    if (s.length > 2) {
                        if (Character.isDigit(s[2]))
                            s.insert(2, " / ")
                    }
                    if (s.length > 3) {
                        if (Character.isDigit(s[3]))
                            s.insert(3, "/ ")
                    }
                    if (s.length > 4) {
                        if (Character.isDigit(s[4]))
                            s.insert(4, " ")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                lengthBefore = s.length
            }

            override fun onTextChanged(s: CharSequence, position: Int, before: Int, action: Int) {

            }
        })

        description.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                ActivityHelper.hideSoftKeyboard(this)
            }
        }

        toolbarAction.setOnClickListener {
            action = Action.SAVE
            ActivityHelper.hideSoftKeyboard(this)
            trySaveCard()
        }

        nextActivityButton.setOnClickListener {
            action = Action.NEXT
            trySaveCard()
        }
    }

    private fun trySaveCard() {
        if (isInputValid()) {
            val state = presenter?.getState()
            val dateString = cardExpiration.text.toString().filter { it.isDigit() }
            state?.let {
                state.apply {
                    expMonth = dateString.substring(0, 2).toInt()
                    expYear = dateString.takeLast(2).toInt() + 2000
                    creditCardNum = cardNumber.text.toString().replace("-", "")
                    cvv = cardCvv.text.toString().toInt()
                    primaryCard = setPrimarySwitch.isChecked
                }
                presenter?.saveState(state)
                presenter?.saveCard()
            }
        }
    }

    private fun isInputValid(): Boolean {
        if (!cardNumber.isValid) {
            cardNumber.error = resources.getString(R.string.credit_card_invalid_card)
            return false
        }
        if (cardExpiration.text.isNullOrEmpty() || cardExpiration.text.length < 7) {
            cardExpiration.error = resources.getString(R.string.credit_card_no_expiration)
            return false
        }
        val dateString = cardExpiration.text.toString().filter { it.isDigit() }
        val expDate = dateDelegate?.dateFromMonthYear(dateString)
        val currentDate = Date()
        if (expDate?.before(currentDate) == true) {
            cardExpiration.error = resources.getString(R.string.credit_card_expired)
            return false
        }
        if (cardCvv.text.isNullOrEmpty() || cardCvv.text.length < 3) {
            cardCvv.error = resources.getString(R.string.credit_card_no_cvv)
            return false
        }
        return true
    }

    private fun initView() {
        cardNumber.requestFocus()

        if (verify) {
            nextActivityButton.visibility = View.VISIBLE
        }

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

    override fun onCardSaved() {
        saveState()
        when (action) {
            Action.NEXT -> {
                showMessage("Coming soon")
                val intent = Intent(this, DepositActivity::class.java)
                startActivity(intent)
            }
            Action.SAVE -> {
                if (forResult) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    val intent = Intent(this, VerifyAccountActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }
    }

    private fun saveState() {
        val state = presenter?.getState()
        if (state?.creditAdded?.get() == false) {
            state.creditAdded.set(presenter?.isCreditAdded() ?: false)
        }
        presenter?.saveState(state ?: return)
    }

    override fun showProgress(show: Boolean) {
        creditProgress.visibility = if (show) View.VISIBLE else View.GONE
        cardContainer.visibility = if (show) View.GONE else View.VISIBLE
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
