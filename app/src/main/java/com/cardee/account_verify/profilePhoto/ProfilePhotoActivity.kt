package com.cardee.account_verify.profilePhoto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cardee.R
import com.cardee.account_verify.credit_card.CreditCardActivity
import com.cardee.account_verify.view.VerifyAccountActivity
import com.cardee.util.display.ActivityHelper
import kotlinx.android.synthetic.main.activity_profile_photo.*

class ProfilePhotoActivity : AppCompatActivity(), ProfilePhotoView {

    private var currentToast: Toast? = null
    private var presenter: ProfilePhotoPresenter? = null

    companion object {
        const val PICK_PROFILE_IMAGE_REQUEST_CODE = 1437
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_photo)
        initToolBar()
        setListeners()
        presenter = ProfilePhotoPresenter(this)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun setListeners() {
        profileUpload.setOnClickListener {
            ActivityHelper.pickImageIntent(this, PICK_PROFILE_IMAGE_REQUEST_CODE)
        }
        nextActivityButton.setOnClickListener {
            saveState()
            val intent = Intent(this, CreditCardActivity::class.java)
            startActivity(intent)
        }
        toolbarAction.setOnClickListener {
            saveState()
            val intent = Intent(this, VerifyAccountActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun saveState() {
        val state = presenter?.getState()
        if (state?.photoAdded?.get() == false) {
            state.photoAdded.set(presenter?.isPhotoAdded() ?: false)
        }
        presenter?.saveState(state ?: return)
    }

    override fun setPhoto(uri: Uri) {
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(profileSample)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_PROFILE_IMAGE_REQUEST_CODE -> {
                    val uri = data?.data
                    if (uri != null) {
                        presenter?.setProfileImage(uri)
                    }
                }
            }
        }
    }
}
