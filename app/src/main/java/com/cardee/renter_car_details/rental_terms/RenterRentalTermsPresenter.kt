package com.cardee.renter_car_details.rental_terms

import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.offers.response.OfferByIdResponseBody
import com.cardee.domain.RxUseCase
import com.cardee.domain.renter.usecase.GetOfferById
import io.reactivex.disposables.Disposable


class RenterRentalTermsPresenter {
    private val getOffer: GetOfferById = GetOfferById()
    private var mDisposable: Disposable? = null
    private var mView: RenterRentalTermsView? = null

    fun init(view: RenterRentalTermsView) {
        mView = view
    }

    fun getRentalTerms(mCarId: Int?) {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
        mDisposable = getOffer.execute(GetOfferById.RequestValues(mCarId
                ?: return), object : RxUseCase.Callback<GetOfferById.ResponseValues> {
            override fun onSuccess(response: GetOfferById.ResponseValues) {
                setRentalTerms(response.offer ?: return)
            }

            override fun onError(error: Error) {
                mView?.showMessage(error.message)
            }
        })
    }

    private fun setRentalTerms(offer: OfferByIdResponseBody) {
        val minAge = offer.carDetails?.requiredMinAge
        val maxAge = offer.carDetails?.requiredMaxAge
        val minExp = offer.carDetails?.requiredDrivingExp
        val rules: String? = offer.carDetails?.carOtherRules
        val deposit = offer.carDetails?.securityDepositDescription
        val insurance = offer.carDetails?.compensationExcess

        mView?.setRequirements(CardeeApp.context.resources.getString(R.string.requirements_template).format(minAge, maxAge, minExp))
        if (rules == null) {
            mView?.hideRules()
        } else {
            mView?.setRules(rules)
        }

        if (offer.carDetails?.requireSecurityDeposit == false || deposit.isNullOrEmpty()) {
            mView?.hideDeposit()
        } else {
            mView?.setDeposit(CardeeApp.context.resources.getString(R.string.security_deposit_template).format(deposit))
        }

        if (insurance == null) {
            mView?.hideInsurance()
        } else {
            mView?.setInsurance(insurance)
        }
    }
}