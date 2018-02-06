package com.cardee.renter_book_car.message.view

import com.cardee.mvp.BaseView


interface BookMessageView : BaseView {
    fun setText(noteText: String?)
}