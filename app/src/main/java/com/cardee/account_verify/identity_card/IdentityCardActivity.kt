package com.cardee.account_verify.identity_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cardee.R
import com.cardee.util.display.ActivityHelper
import kotlinx.android.synthetic.main.activity_identity_card.*

class IdentityCardActivity : AppCompatActivity(), IdentityCardView {


    private var currentToast: Toast? = null
    private var presenter: IdentityCardPresenter? = null

    companion object {
        const val PICK_FRONT_IMAGE_REQUEST_CODE = 1437
        const val PICK_BACK_IMAGE_REQUEST_CODE = 1438
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identity_card)
        initToolBar()
        setListeners()
        presenter = IdentityCardPresenter(this)
    }

    private fun setListeners() {
        identityCardFrontUpload.setOnClickListener {
            ActivityHelper.pickImageIntent(this, PICK_FRONT_IMAGE_REQUEST_CODE)
        }
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun setFrontPhoto(pictureByteArray: ByteArray?) {
        Glide.with(this)
                .load(pictureByteArray)
                .centerCrop()
                .into(identityCardFrontSample)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FRONT_IMAGE_REQUEST_CODE -> {
                    val frontUri = data?.data
                    if (frontUri != null) {
                        presenter?.setFrontImage(frontUri)
                    }
                }
            }
        }
    }
}
