package com.cardee.custom.edit_text_credit


class CardPattern {
    companion object {
        val VISA = "4[0-9]{12}(?:[0-9]{3})?"
        val VISA_VALID = "^4[0-9]{12}(?:[0-9]{3})?$"

        // MasterCard
        val MASTERCARD = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$"
        val MASTERCARD_SHORT = "^(?:222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)"
        val MASTERCARD_SHORTER = "^(?:5[1-5])"
        val MASTERCARD_VALID = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$"

        // American Express
        val AMERICAN_EXPRESS = "^3[47][0-9]{0,13}"
        val AMERICAN_EXPRESS_VALID = "^3[47][0-9]{13}$"

        // DISCOVER
        val DISCOVER = "^6(?:011|5[0-9]{1,2})[0-9]{0,12}"
        val DISCOVER_SHORT = "^6(?:011|5)"
        val DISCOVER_VALID = "^6(?:011|5[0-9]{2})[0-9]{12}$"

        // JCB
        val JCB = "^(?:2131|1800|35\\d{0,3})\\d{0,11}$"
        val JCB_SHORT = "^2131|1800"
        val JCB_VALID = "^(?:2131|1800|35\\d{3})\\d{11}$"

        // Discover
        val DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$"
        val DINERS_CLUB_SHORT = "^30[0-5]"
        val DINERS_CLUB_VALID = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$"
    }
}