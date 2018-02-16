package com.cardee.account_verify.identity_card

import com.cardee.mvp.BaseView


interface IdentityCardView : BaseView {
    fun setFrontPhoto(pictureByteArray: ByteArray?)
}