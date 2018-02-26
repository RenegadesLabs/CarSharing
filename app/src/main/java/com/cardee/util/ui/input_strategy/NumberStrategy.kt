package com.cardee.util.ui.input_strategy

import android.widget.EditText


class NumberStrategy(private val callback: (String) -> Unit) : InputStrategy {

    override fun execute(input: EditText, newText: String) {

    }

    override fun valueOf(input: EditText): String {
        return input.text.toString()
    }

}