package com.cardee.custom.edit_text_credit

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import com.cardee.R
import java.util.regex.Pattern


class EditCard : AppCompatEditText {

    var cardType = "UNKNOWN"
        internal set

    val cardNumber: String
        get() = text.toString().replace("-", "").trim { it <= ' ' }

    val isValid: Boolean
        get() {
            if (cardNumber.matches(CardPattern.VISA_VALID.toRegex())) return true
            if (cardNumber.matches(CardPattern.MASTERCARD_VALID.toRegex())) return true
            if (cardNumber.matches(CardPattern.AMERICAN_EXPRESS_VALID.toRegex())) return true
            if (cardNumber.matches(CardPattern.DISCOVER_VALID.toRegex())) return true
            if (cardNumber.matches(CardPattern.DINERS_CLUB_VALID.toRegex())) return true
            return cardNumber.matches(CardPattern.JCB_VALID.toRegex())
        }

    constructor(context: Context) : super(context) {
        addMagic()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        addMagic()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        addMagic()
    }

    private fun addMagic() {
        // Changing the icon when it's empty
        changeIcon()
        // Adding the TextWatcher
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, position: Int, before: Int, action: Int) {
                if (action == 1) {
                    if (cardType == "UNKNOWN" || cardType == "Visa" || cardType == "Discover" || cardType == "JCB") {
                        if (position == 3 || position == 8 || position == 13) {
                            if (!s.toString().endsWith("-")) {
                                append("-")
                            }
                        }
                    } else if (cardType == "American_Express" || cardType == "Diners_Club") {
                        if (position == 3 || position == 10) {
                            if (!s.toString().endsWith("-")) {
                                append("-")
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {
                changeIcon()
            }
        })
        // The input filters
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Pattern.compile("[0-9\\-]*").matcher(source.toString()).matches()) {
                    return@InputFilter ""
                }
            }
            null
        }
        // Setting the filters
        filters = arrayOf(filter, InputFilter.LengthFilter(19))
    }

    private fun changeIcon() {
        val s = text.toString().replace("-", "").trim { it <= ' ' }
        if (s.startsWith("4") || s.matches(CardPattern.VISA.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0)
            cardType = "Visa"
        } else if (s.matches(CardPattern.MASTERCARD_SHORTER.toRegex()) || s.matches(CardPattern.MASTERCARD_SHORT.toRegex()) || s.matches(CardPattern.MASTERCARD.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_master_card, 0)
            cardType = "MasterCard"
        } else if (s.matches(CardPattern.AMERICAN_EXPRESS.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_amex, 0)
            cardType = "American_Express"
        } else if (s.matches(CardPattern.DISCOVER_SHORT.toRegex()) || s.matches(CardPattern.DISCOVER.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_discover, 0)
            cardType = "Discover"
        } else if (s.matches(CardPattern.JCB_SHORT.toRegex()) || s.matches(CardPattern.JCB.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            cardType = "JCB"
        } else if (s.matches(CardPattern.DINERS_CLUB_SHORT.toRegex()) || s.matches(CardPattern.DINERS_CLUB.toRegex())) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dinersclub, 0)
            cardType = "Diners_Club"
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            cardType = "UNKNOWN"
        }
    }
}