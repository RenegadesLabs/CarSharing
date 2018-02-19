package com.cardee.util.ui.input_strategy

import android.widget.EditText

class PriceStrategy(private val currencySymbol: String) : InputStrategy {

    private companion object {
        private val EMPTY = 0
        private val VALID = 1
        private val INVALID = 2
        private val OVERLIMIT = 3
    }

    private val validPattern = Regex("\\$currencySymbol\\d+(\\.\\d{0,2})?")

    override fun execute(input: EditText, newText: String) {
        when (classify(newText)) {
            INVALID -> {
                val validValue: String
                validValue = if (newText.contains(currencySymbol)) {
                    val currSymbolIndex = newText.indexOf(currencySymbol)
                    val replaced = newText.substring(currSymbolIndex)
                    "$replaced${newText.removeSuffix(replaced)}"
                } else {
                    "$currencySymbol$newText"
                }
                input.setText(validValue)
            }
            OVERLIMIT -> {
                val decimal = newText.substringAfterLast('.', "")
                if (decimal.isEmpty()) return
                val suffix = decimal.substring(2)
                input.setText(newText.removeSuffix(suffix))
            }
            EMPTY -> input.setText("")
        }
    }

    override fun valueOf(input: EditText): String {

        return input.text.toString()
    }

    private fun classify(inputValue: String): Int {
        if (validPattern.matches(inputValue)) return VALID
        if (inputValue.startsWith(currencySymbol).not()) return INVALID
        if (inputValue.substringAfterLast('.', "").length > 2) return OVERLIMIT
        if (inputValue.isEmpty() || inputValue == currencySymbol) return EMPTY
        return -1
    }
}