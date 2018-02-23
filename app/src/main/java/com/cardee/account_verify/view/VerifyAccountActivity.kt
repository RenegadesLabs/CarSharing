package com.cardee.account_verify.view

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import com.cardee.account_verify.credit_card.CreditCardActivity
import com.cardee.account_verify.identity_card.IdentityCardActivity
import com.cardee.account_verify.license.LicenseActivity
import com.cardee.account_verify.particulars.ParticularsActivity
import com.cardee.account_verify.presenter.VerifyAccountPresenter
import com.cardee.account_verify.profilePhoto.ProfilePhotoActivity
import com.cardee.databinding.ActivityVerifyAccountBinding
import com.cardee.domain.profile.entity.VerifyAccountState
import kotlinx.android.synthetic.main.activity_verify_account.*

class VerifyAccountActivity : AppCompatActivity(), VerifyAccountView, View.OnClickListener {

    private var currentToast: Toast? = null
    private var presenter: VerifyAccountPresenter? = null
    lateinit var binding: ActivityVerifyAccountBinding
    lateinit var state: VerifyAccountState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = VerifyAccountPresenter()
        presenter?.init(this)
        bindView()
        initToolBar()
        setListeners()
        presenter?.loadVerifyState()
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_account)
        state = presenter?.getState() ?: VerifyAccountState()
        binding.state = state
        binding.executePendingBindings()
    }

    private fun setListeners() {
        particularsContainer.setOnClickListener(this)
        identityContainer.setOnClickListener(this)
        licenseContainer.setOnClickListener(this)
        photoContainer.setOnClickListener(this)
        creditContainer.setOnClickListener(this)
        depositContainer.setOnClickListener(this)
        saveProgress.setOnClickListener(this)
        chatButton.setOnClickListener(this)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onStart() {
        super.onStart()
        updateState()
    }

    override fun updateState() {
        state = presenter?.getState() ?: VerifyAccountState()
        binding.state = state
    }

    override fun onClick(view: View?) {
        when (view) {
            particularsContainer -> {
                val intent = Intent(this, ParticularsActivity::class.java)
                startActivity(intent)
            }
            identityContainer -> {
                val intent = Intent(this, IdentityCardActivity::class.java)
                startActivity(intent)
            }
            licenseContainer -> {
                val intent = Intent(this, LicenseActivity::class.java)
                startActivity(intent)
            }
            photoContainer -> {
                val intent = Intent(this, ProfilePhotoActivity::class.java)
                startActivity(intent)
            }
            creditContainer -> {
                val intent = Intent(this, CreditCardActivity::class.java)
                intent.putExtra("isVerify", true)
                startActivity(intent)
            }
            chatButton -> {
                showMessage("Coming soon")
            }
            saveProgress -> {
                presenter?.saveProgress()
            }
        }
    }

    override fun onProgressSaved() {
        if (state.particularsAdded.get() &&
                state.identityAdded.get() &&
                state.licenseAdded.get() &&
                state.photoAdded.get() &&
                state.creditAdded.get() &&
                state.depositAdded.get()) {
            setResult(Activity.RESULT_OK)
        }
        onBackPressed()
    }

    override fun showProgress(show: Boolean) {
        verifyProgress.visibility = if (show) View.VISIBLE else View.GONE
        verifyContainer.visibility = if (show) View.GONE else View.VISIBLE
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
