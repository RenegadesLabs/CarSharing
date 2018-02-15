package com.cardee.account_verify.particulars

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.custom.modal.DatePickerMenuFragment
import com.cardee.custom.modal.DatePickerMenuFragment.Companion.DATETYPE
import com.cardee.util.DateRepresentationDelegate
import kotlinx.android.synthetic.main.activity_particulars.*
import java.util.*

class ParticularsActivity : AppCompatActivity(), ParticularsView, DatePickerMenuFragment.DialogOnClickListener {

    private var currentToast: Toast? = null
    private var presenter: ParticularsPresenter? = null
    private var dateDelegate: DateRepresentationDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particulars)
        initToolBar()
        presenter = ParticularsPresenter(this)
        dateDelegate = DateRepresentationDelegate(this)
        birthDateInput.setOnClickListener {
            val date = dateDelegate?.convertDayMonthYearToDate(birthDateInput.text.toString())
            val cal = GregorianCalendar()
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
