package com.cardee.account_verify.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import com.cardee.account_verify.particulars.ParticularsActivity
import com.cardee.databinding.ActivityVerifyAccountBinding
import com.cardee.domain.account.entity.VerifyAccountState
import kotlinx.android.synthetic.main.activity_verify_account.*

class VerifyAccountActivity : AppCompatActivity(), VerifyAccountView, View.OnClickListener {

    private var mCurrentToast: Toast? = null
    lateinit var binding: ActivityVerifyAccountBinding
    lateinit var state: VerifyAccountState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        initToolBar()
        setListeners()
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_account)
        state = VerifyAccountState()
        binding.state = state
        binding.executePendingBindings()
    }

    private fun setListeners() {
        particularsAdd.setOnClickListener(this)
        identityAdd.setOnClickListener(this)
        licenseAdd.setOnClickListener(this)
        photoAdd.setOnClickListener(this)
        creditAdd.setOnClickListener(this)
        depositAdd.setOnClickListener(this)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onClick(view: View?) {
        when (view) {
            particularsAdd -> {
                val intent = Intent(this, ParticularsActivity::class.java)
                startActivity(intent)
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
