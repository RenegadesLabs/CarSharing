package com.cardee.account_verify.profilePhoto

import android.net.Uri
import com.cardee.mvp.BaseView


interface ProfilePhotoView : BaseView {
    fun setPhoto(uri: Uri)
}