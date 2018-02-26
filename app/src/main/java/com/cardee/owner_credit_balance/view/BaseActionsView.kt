package com.cardee.owner_credit_balance.view

import android.support.annotation.StringRes
import android.view.View

interface BaseActionsView {

    fun initProgressView(progress: View)

    fun showProgress(show: Boolean)

    fun showMessage(message: String)

    fun showMessage(@StringRes messageId: Int)

}
