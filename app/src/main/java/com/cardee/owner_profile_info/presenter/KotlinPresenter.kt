package com.cardee.owner_profile_info.presenter

import android.content.Context
import android.content.res.Resources
import android.location.Geocoder
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.owner.entity.Car
import com.cardee.domain.owner.usecase.ChangeImage
import com.cardee.domain.owner.usecase.ChangeNote
import com.cardee.domain.owner.usecase.GetOwnerInfo
import com.cardee.domain.owner.usecase.GetOwnerInfoById
import com.cardee.owner_profile_info.view.ProfileInfoView
import io.reactivex.functions.Consumer
import java.util.*

class KotlinPresenter(val mView: ProfileInfoView) : Consumer<Car> {

    private val mGetInfoUseCase: GetOwnerInfo
    private val mGetInfoById: GetOwnerInfoById
    private val mChangeNote: ChangeNote
    private val mChangeImage: ChangeImage
    private val mExecutor: UseCaseExecutor
    private val mResources: Resources

    init {
        mGetInfoUseCase = GetOwnerInfo()
        mGetInfoById = GetOwnerInfoById()
        mChangeNote = ChangeNote()
        mChangeImage = ChangeImage()
        mExecutor = UseCaseExecutor.getInstance()
        mResources = (mView as Context).resources
    }

    fun getOwnerInfo() {
        mView.showProgress(true)
        mExecutor.execute(mGetInfoUseCase, null, object : UseCase.Callback<GetOwnerInfo.ResponseValues> {
            override fun onSuccess(response: GetOwnerInfo.ResponseValues?) {
                mView.showProgress(false)
                setProfileFields(response)
            }

            override fun onError(error: Error?) {
                mView.showProgress(false)
                mView.showMessage(error?.message)
            }
        })
    }

    private fun setProfileFields(response: GetOwnerInfo.ResponseValues?) {
        val profile = response?.ownerProfile
        if (profile != null) {
            mView.setProfileImage(profile.profilePhotoLink)
            mView.setProfileName(profile.name)

            val rate: Float? = profile.rating
            if (rate != null) {
                if (rate % 1 == 0F) {
                    mView.setProfileRating("$rate")
                } else {
                    mView.setProfileRating(java.lang.String.format(Locale.getDefault(), "%.1f", rate))
                }
            }

            val accept: Float? = profile.acceptance
            if (accept == null) {
                mView.setAcceptance("0")
            } else {
                if (accept % 1 == 0F) {
                    mView.setAcceptance("$accept")
                } else {
                    mView.setAcceptance(java.lang.String.format(Locale.getDefault(), "%.1f", accept))
                }
            }

            val responseTime = profile.responseTime
            if (responseTime == null) {
                mView.setResponseText("0")
            } else {
                mView.setResponseText(responseTime)
            }

            var minutes = mResources.getString(R.string.owner_profile_info_minutes)
            if (responseTime.toInt() != 1) {
                minutes += "s"
            }
            mView.setMinutes(minutes)

            mView.setBookings(profile.bookingsCount.toString())

            var note = profile.note
            if (note.isNullOrEmpty()) {
                note = mResources.getString(R.string.profile_default_note)
            }
            mView.setNote(note)

            val address = profile.address
            if (!address.isNullOrEmpty()){
                val geocoder = Geocoder(CardeeApp.context, Locale.getDefault())
            }

        }
    }


    override fun accept(car: Car?) {
        mView.openItem(car)
    }

}

