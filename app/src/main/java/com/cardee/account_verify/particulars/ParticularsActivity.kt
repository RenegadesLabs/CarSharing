package com.cardee.account_verify.particulars

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.custom.modal.DatePickerMenuFragment
import com.cardee.custom.modal.DatePickerMenuFragment.Companion.DATETYPE
import com.cardee.mvp.BaseView
import kotlinx.android.synthetic.main.activity_particulars.*

class ParticularsActivity : AppCompatActivity(), BaseView, DatePickerMenuFragment.DialogOnClickListener {

    private var mCurrentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particulars)
        initToolBar()
        birthDateInput.setOnClickListener {
            val menu = DatePickerMenuFragment.newInstance(DATETYPE.BIRTHDAY, 1990, 1, 1)
            menu.show(supportFragmentManager, DatePickerMenuFragment::class.java.simpleName)
            menu.setOnSaveClickListener(this)
        }
        licenseDateInput.setOnClickListener {
            val menu = DatePickerMenuFragment.newInstance(DATETYPE.LICENSE, 2010, 1, 1)
            menu.show(supportFragmentManager, DatePickerMenuFragment::class.java.simpleName)
            menu.setOnSaveClickListener(this)
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
                birthDateInput.setText(value)
            }
            DATETYPE.LICENSE -> {
                licenseDateInput.setText(value)
            }
        }
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
