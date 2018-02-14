package com.cardee.owner_credit_balance.view

import android.view.View
import android.widget.Toast

class BaseActionDelegate : BaseActionsView {

    var progressView: View? = null
    var currentToast: Toast? = null

    override fun initProgressView(progress: View) {
        this.progressView = progress
    }

    override fun showProgress(show: Boolean) {
        progressView?.let {
            val visible = it.visibility == View.VISIBLE
            when (show && !visible) {
                true -> it.visibility = View.VISIBLE
                false -> it.visibility = View.GONE
            }
        }
    }

    override fun showMessage(message: String) {
        val context = progressView?.context ?: return
        currentToast?.cancel()
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        val context = progressView?.context ?: return
        val message = context.getString(messageId)
        showMessage(message)
    }

}
