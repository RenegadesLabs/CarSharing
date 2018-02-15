package com.cardee.account_verify.particulars

import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher


class ParticularsPresenter(val view: ParticularsView) {

    fun setCountryTextWatcher(countryInput: TextInputEditText) {
        countryInput.addTextChangedListener(object : TextWatcher {
            internal var length_before = 0

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                length_before = s.length
            }

            override fun afterTextChanged(s: Editable) {
                if (length_before < s.length) {
                    if (length_before < s.length) {
                        if (s.length == 1)
                            s.insert(0, "+")
                    }
                }
            }
        })
    }

    fun setPhoneTextWatcher(phoneInput: TextInputEditText) {
        phoneInput.addTextChangedListener(object : TextWatcher {
            internal var length_before = 0

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                length_before = s.length
            }

            override fun afterTextChanged(s: Editable) {
                if (length_before < s.length) {
                    if (length_before < s.length) {
                        if (s.length == 3 || s.length == 7)
                            s.append("-")
                        if (s.length > 3) {
                            if (Character.isDigit(s[3]))
                                s.insert(3, "-")
                        }
                        if (s.length > 7) {
                            if (Character.isDigit(s[7]))
                                s.insert(7, "-")
                        }
                    }
                }
            }
        })
    }
}