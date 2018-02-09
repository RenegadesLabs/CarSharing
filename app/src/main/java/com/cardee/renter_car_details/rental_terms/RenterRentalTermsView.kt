package com.cardee.renter_car_details.rental_terms

import com.cardee.mvp.BaseView


interface RenterRentalTermsView : BaseView{
    fun setRequirements(format: String)
    fun setRules(rules: String?)
    fun setDeposit(format: String)
    fun setInsurance(insurance: String?)
    fun hideRules()
    fun hideDeposit()
}