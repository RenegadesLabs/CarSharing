package com.cardee.util.ui.input_strategy

import android.widget.EditText

class DecimalStrategy(private val decimals: Int,
                      private val callback: (String) -> Unit) : InputStrategy {

    private companion object {
        private val EMPTY = 0
        private val VALID = 1
        private val INVALID = 2
    }

    override fun execute(input: EditText, newText: String) {
        val validValue = when (classify(newText)) {
            INVALID -> newText.run {
                val decimal = substringAfterLast('.', "")
                if (decimal.isEmpty()) return
                val suffix = decimal.substring(decimals)
                removeSuffix(suffix)
            }
            else -> newText
        }
        input.setText(validValue)
        callback.invoke(validValue)
    }

    override fun valueOf(input: EditText): String {
        val inputValue = input.text.toString()
        return inputValue.replace(Regex("\\.$"), "")
    }

    private fun classify(inputValue: String): Int {
        if (inputValue.substringAfterLast('.', "").length <= decimals) return VALID
        if (inputValue.isNullOrEmpty()) return EMPTY
        return INVALID
    }
}