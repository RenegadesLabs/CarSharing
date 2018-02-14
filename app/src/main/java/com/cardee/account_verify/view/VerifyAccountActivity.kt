package com.cardee.account_verify.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cardee.R

class VerifyAccountActivity : AppCompatActivity(), VerifyAccountView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_account)
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showMessage(message: String?) {

    }

    override fun showMessage(messageId: Int) {

    }
}
