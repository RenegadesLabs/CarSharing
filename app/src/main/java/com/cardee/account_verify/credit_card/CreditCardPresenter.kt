package com.cardee.account_verify.credit_card


class CreditCardPresenter(var view: CreditCardView?) {


    fun onDestroy() {
        view = null
    }

}