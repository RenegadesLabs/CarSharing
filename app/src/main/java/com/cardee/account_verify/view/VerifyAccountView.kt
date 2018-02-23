package com.cardee.account_verify.view

import com.cardee.mvp.BaseView

interface VerifyAccountView : BaseView {
    fun onProgressSaved()
    fun updateState()

}