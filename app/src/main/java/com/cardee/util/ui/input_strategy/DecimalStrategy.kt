package com.cardee.util.ui.input_strategy

import android.widget.EditText

class DecimalStrategy(private val decimals: Int,
                      private val callback: (String) -> Unit) : InputStrategy {

    private val zeroStartPattern = Regex("^0\\d+")
    private val delimiterStartPattern = Regex("^\\.\\d*")

    override fun execute(input: EditText, newText: String) {

        var validValue: String? = newText.run {
            if (zeroStartPattern.matches(this)) {
                replace(Regex("^0+"), "0.")
            } else this
        }

        validValue = validValue?.run {
            if (delimiterStartPattern.matches(this)) {
                "0$this"
            } else this
        }

        validValue = validValue?.run {
            if (substringAfterLast('.', "").length > decimals) {
                val decimal = substringAfterLast('.', "")
                val suffix = decimal.substring(decimals)
                removeSuffix(suffix)
            } else this
        }

        validValue?.let {
            input.setText(it)
            input.setSelection(it.length)
        }

        callback.invoke(input.text.toString())
    }

    override fun valueOf(input: EditText): String {
        val inputValue = input.text.toString()
        return inputValue.replace(Regex("\\.$"), "")
    }
}