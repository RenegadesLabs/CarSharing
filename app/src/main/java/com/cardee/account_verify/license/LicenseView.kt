package com.cardee.account_verify.license

import android.net.Uri
import com.cardee.mvp.BaseView


interface LicenseView : BaseView {
    fun setFrontPhoto(pictureUri: Uri?)
    fun setBackPhoto(pictureUri: Uri?)
}