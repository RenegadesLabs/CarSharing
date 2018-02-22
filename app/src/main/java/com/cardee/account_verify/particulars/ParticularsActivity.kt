package com.cardee.account_verify.particulars

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.account_verify.identity_card.IdentityCardActivity
import com.cardee.custom.modal.DatePickerMenuFragment
import com.cardee.custom.modal.DatePickerMenuFragment.Companion.DATETYPE
import com.cardee.domain.profile.entity.VerifyAccountState
import com.cardee.util.DateRepresentationDelegate
import kotlinx.android.synthetic.main.activity_particulars.*
import java.util.*

class ParticularsActivity : AppCompatActivity(), ParticularsView, DatePickerMenuFragment.DialogOnClickListener {

    private var currentToast: Toast? = null
    private var presenter: ParticularsPresenter? = null
    private var dateDelegate: DateRepresentationDelegate? = null
    private var state: VerifyAccountState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particulars)
        initToolBar()
        presenter = ParticularsPresenter(this)
        dateDelegate = DateRepresentationDelegate(this)
        getState()
        setListeners()
    }

    private fun getState() {
        val state = presenter?.getState()
        nameInput.setText(state?.name)
        identityInput.setText(state?.identityCard)
        addressInput.setText(state?.address)

        if (state?.phone?.length ?: 0 > 14) {
            val phone = state?.phone?.takeLast(12)
            val code = state?.phone?.dropLast(12)
            phoneInput.setText(phone)
            countryInput.setText(if (code == null || code == "") "+65" else code)
        } else {
            countryInput.setText("+65")
        }

        dateDelegate?.setMonthDayYear(birthDateInput, state?.birthDate ?: "")
        dateDelegate?.setMonthDayYear(licenseDateInput, state?.licenseDate ?: "")
    }

    private fun setListeners() {
        birthDateInput.setOnClickListener {
            val displayString = birthDateInput.text.toString()
            val date = dateDelegate?.convertDayMonthYearToDate(displayString)
            val cal = GregorianCalendar()
            cal.timeZone = CardeeApp.getTimeZone()
            if (date != null) {
                cal.time = date
            }

            val year = if (date == null) 1990 else cal.get(Calendar.YEAR)
            val month = if (date == null) 0 else cal.get(Calendar.MONTH)
            val day = if (date == null) 1 else cal.get(Calendar.DATE)
            val menu = DatePickerMenuFragment.newInstance(DATETYPE.BIRTHDAY, year, month, day)
            menu.show(supportFragmentManager, DatePickerMenuFragment::class.java.simpleName)
            menu.setOnSaveClickListener(this)
        }

        licenseDateInput.setOnClickListener {
            val date = dateDelegate?.convertDayMonthYearToDate(licenseDateInput.text.toString())
            val cal = GregorianCalendar()
            cal.timeZone = CardeeApp.getTimeZone()
            if (date != null) {
                cal.time = date
            }

            val year = if (date == null) 2010 else cal.get(Calendar.YEAR)
            val month = if (date == null) 0 else cal.get(Calendar.MONTH)
            val day = if (date == null) 1 else cal.get(Calendar.DATE)
            val menu = DatePickerMenuFragment.newInstance(DATETYPE.LICENSE, year, month, day)
            menu.show(supportFragmentManager, DatePickerMenuFragment::class.java.simpleName)
            menu.setOnSaveClickListener(this)
        }

        presenter?.setCountryTextWatcher(countryInput)
        presenter?.setPhoneTextWatcher(phoneInput)

        nextActivityButton.setOnClickListener {
            state = presenter?.getState()
            createState()
            presenter?.saveState(state ?: return@setOnClickListener)
            val intent = Intent(this, IdentityCardActivity::class.java)
            startActivity(intent)
        }

        toolbarAction.setOnClickListener {
            state = presenter?.getState()
            createState()
            presenter?.saveState(state ?: return@setOnClickListener)
            onBackPressed()
        }
    }

    private fun createState() {
        state?.apply {
            name = nameInput.text.toString()
            identityCard = identityInput.text.toString()
            address = addressInput.text.toString()
            phone = countryInput.text.toString() + phoneInput.text.toString()
            birthDate = dateDelegate?.formatShortDayMonthYear(birthDateInput.text.toString()) ?: ""
            licenseDate = dateDelegate?.formatShortDayMonthYear(licenseDateInput.text.toString()) ?: ""
            if (name != "" && identityCard != "" && address != "" && phone != "" && birthDate != "" && licenseDate != "") {
                particularsAdded.set(true)
            } else {
                if (particularsAdded.get()) {
                    particularsAdded.set(false)
                }
            }
        }
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onSaveClicked(type: DatePickerMenuFragment.Companion.DATETYPE, value: String) {
        when (type) {
            DATETYPE.BIRTHDAY -> {
                dateDelegate?.setMonthDayYear(birthDateInput, value)
            }
            DATETYPE.LICENSE -> {
                dateDelegate?.setMonthDayYear(licenseDateInput, value)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
