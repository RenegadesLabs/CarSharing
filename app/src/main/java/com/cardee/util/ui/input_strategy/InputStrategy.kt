package com.cardee.util.ui.input_strategy

import android.widget.EditText

interface InputStrategy {

    fun execute(input: EditText, newText: String)

    fun valueOf(input: EditText): String
}