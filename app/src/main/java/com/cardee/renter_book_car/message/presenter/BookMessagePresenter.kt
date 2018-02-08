package com.cardee.renter_book_car.message.presenter

import com.cardee.domain.renter.usecase.GetBookState
import com.cardee.domain.renter.usecase.SaveBookState
import com.cardee.renter_book_car.message.view.BookMessageView

class BookMessagePresenter {

    private var mView: BookMessageView? = null
    private val getStateCase = GetBookState()
    private val saveStateCase = SaveBookState()

    fun init(view: BookMessageView) {
        mView = view
        val state = getStateCase.getBookState()
        if (state.noteAdded.get()) {
            mView?.setText(state.noteText)
        }
    }

    fun onDestroy() {
        mView = null
    }

    fun saveMessage(message: String) {
        val state = getStateCase.getBookState()
        state.noteText = message
        state.noteAdded.set(true)
        saveStateCase.saveBookState(state)
    }
}