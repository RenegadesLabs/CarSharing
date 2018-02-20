package com.cardee.account_verify.license

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cardee.R
import com.cardee.account_verify.profilePhoto.ProfilePhotoActivity
import com.cardee.account_verify.view.VerifyAccountActivity
import com.cardee.util.display.ActivityHelper
import kotlinx.android.synthetic.main.activity_license.*

class LicenseActivity : AppCompatActivity(), LicenseView {

    private var currentToast: Toast? = null
    private var presenter: LicensePresenter? = null

    companion object {
        const val PICK_FRONT_IMAGE_REQUEST_CODE = 1437
        const val PICK_BACK_IMAGE_REQUEST_CODE = 1438
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        initToolBar()
        setListeners()
        presenter = LicensePresenter(this)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun setListeners() {
        licenseCardFrontUpload.setOnClickListener {
            ActivityHelper.pickImageIntent(this, PICK_FRONT_IMAGE_REQUEST_CODE)
        }
        licenseCardBackUpload.setOnClickListener {
            ActivityHelper.pickImageIntent(this, PICK_BACK_IMAGE_REQUEST_CODE)
        }
        nextActivityButton.setOnClickListener {
            saveState()
            val intent = Intent(this, ProfilePhotoActivity::class.java)
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
        if (state?.licenseAdded?.get() == false) {
            state.licenseAdded.set(presenter?.isLicenseAdded() ?: false)
        }
        presenter?.saveState(state ?: return)
    }

    override fun setFrontPhoto(pictureUri: Uri?) {
        Glide.with(this)
                .load(pictureUri)
                .centerCrop()
                .into(licenseCardFrontSample)
    }

    override fun setBackPhoto(pictureUri: Uri?) {
        Glide.with(this)
                .load(pictureUri)
                .centerCrop()
                .into(licenseCardBackSample)
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
                PICK_FRONT_IMAGE_REQUEST_CODE -> {
                    val frontUri = data?.data
                    if (frontUri != null) {
                        presenter?.setFrontImage(frontUri)
                    }
                }
                PICK_BACK_IMAGE_REQUEST_CODE -> {
                    val backUri = data?.data
                    if (backUri != null) {
                        presenter?.setBackImage(backUri)
                    }
                }
            }
        }
    }
}
