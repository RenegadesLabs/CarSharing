package com.cardee.renter_car_details.rental_terms

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import kotlinx.android.synthetic.main.activity_renter_rental_terms.*

class RenterRentalTermsActivity : AppCompatActivity(), RenterRentalTermsView {

    private var mCurrentToast: Toast? = null
    private var mPresenter = RenterRentalTermsPresenter()
    private var mCarId: Int? = null
    private var mAgree: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renter_rental_terms)
        initToolBar()
        getIntentData()
        initView()

        mPresenter.init(this)
        mPresenter.getRentalTerms(mCarId)
    }

    private fun getIntentData() {
        mCarId = intent.getIntExtra("carId", -1)
        mAgree = intent.getBooleanExtra("agree", false)
    }

    private fun initView() {
        if (!mAgree) {
            agreeButton.visibility = View.GONE
        }
        val more = SpannableString(resources.getString(R.string.security_deposit_hint_second))
        more.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimaryDark)), 0, more.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        depositHint.append(more)

        val moreIns = SpannableString(resources.getString(R.string.insurance_excess_hint_second))
        moreIns.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimaryDark)), 0, moreIns.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        insuranceHint.append(moreIns)

        agreeButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun setInsurance(insurance: String?) {
        insuranceText.text = insurance
    }

    override fun setDeposit(format: String) {
        depositText.text = format
    }

    override fun setRules(rules: String?) {
        carRulesText.text = rules
    }

    override fun setRequirements(format: String) {
        requirementsText.text = format
    }

    override fun hideDeposit() {
        depositText.visibility = View.GONE
    }

    override fun hideRules() {
        carRulesText.visibility = View.GONE
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
}
