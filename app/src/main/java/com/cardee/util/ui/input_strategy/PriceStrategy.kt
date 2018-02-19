package com.cardee.util.ui.input_strategy

import android.util.Log
import android.widget.EditText

class PriceStrategy(private val currencySymbol: String) : InputStrategy {

    private val pattern = Regex("(\\d+$)|(\\.\\d{1,2}$)")

    override fun execute(input: EditText, newText: String) {
        Log.e("EDIT_TEXT_VALUE", "${input.text} $newText")
    }

    override fun valueOf(input: EditText): String {
        return input.text.toString()
    }

    private fun validate(inputValue: String): Boolean {
        return pattern.matches(inputValue)
    }
}