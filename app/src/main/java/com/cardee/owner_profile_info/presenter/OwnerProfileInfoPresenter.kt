package com.cardee.owner_profile_info.presenter

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.location.Geocoder
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile
import com.cardee.data_source.util.DialogHelper
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.owner.entity.Car
import com.cardee.domain.owner.entity.mapper.OwnerReviewToCarReviewMapper
import com.cardee.domain.owner.usecase.ChangeImage
import com.cardee.domain.owner.usecase.ChangeNote
import com.cardee.domain.owner.usecase.GetOwnerInfo
import com.cardee.domain.owner.usecase.GetOwnerInfoById
import com.cardee.owner_profile_info.view.ProfileInfoView
import io.reactivex.functions.Consumer
import java.io.File
import java.util.*

class OwnerProfileInfoPresenter(val mView: ProfileInfoView?) : Consumer<Car> {

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

    fun getOwnerById(profileId: Int) {
        mView?.showProgress(true)
        mExecutor.execute(mGetInfoById, GetOwnerInfoById.RequestValues(profileId), object : UseCase.Callback<GetOwnerInfoById.ResponseValues> {
            override fun onSuccess(response: GetOwnerInfoById.ResponseValues?) {
                setProfileFields(response)
                mView?.showProgress(false)
            }

            override fun onError(error: Error?) {
                mView?.showProgress(false)
                mView?.showMessage(error?.message)
            }
        })
    }

    fun getOwnerInfo() {
        mView?.showProgress(true)
        mExecutor.execute(mGetInfoUseCase, null, object : UseCase.Callback<GetOwnerInfo.ResponseValues> {
            override fun onSuccess(response: GetOwnerInfo.ResponseValues?) {
                mView?.showProgress(false)
                setProfileFields(response)
            }

            override fun onError(error: Error?) {
                mView?.showProgress(false)
                mView?.showMessage(error?.message)
            }
        })
    }

    private fun setProfileFields(response: GetOwnerInfo.ResponseValues?) {
        val profile = response?.ownerProfile
        if (profile != null) {
            mView?.setProfileImage(profile.profilePhotoLink)
            mView?.setProfileName(profile.name)
            setProfileRating(profile)
            setProfileAcceptance(profile)
            setResponseTime(profile)
            mView?.setBookings(profile.bookingsCount.toString())
            setNote(profile)
            setCarsCount(profile)
            setReviewsCount(profile)
            setCars(response)
            setReviews(profile)
        }
    }

    private fun setReviews(profile: OwnerProfile) {
        val reviews = profile.reviews
        if (reviews.isNotEmpty()) {
            val mapper = OwnerReviewToCarReviewMapper()
            val carReviewList = mapper.transform(reviews)
            val iterator = carReviewList.iterator()
            while (iterator.hasNext()) {
                val review = iterator.next()
                val text = review.reviewText
                if (text.isNullOrEmpty()) {
                    iterator.remove()
                } else {
                    review.reviewText = text.trim()
                }
            }
            carReviewList.sortByDescending { review -> review.reviewDate }
            mView?.setCarReviews(carReviewList)
        }
    }

    private fun setCars(response: GetOwnerInfo.ResponseValues) {
        val cars: MutableList<Car>? = response.cars
        if (cars?.isNotEmpty() == true) {
            mView?.setCars(cars)
        }
    }

    private fun setReviewsCount(profile: OwnerProfile) {
        val reviewsCount = profile.reviewCount
        val builder = StringBuilder(reviewsCount.toString())
        builder.append(mResources.getString(R.string.owner_profile_info_car_review))
        if (reviewsCount != 1) {
            builder.append("s")
        }
        mView?.setReviewsCount(builder.toString())
    }

    private fun setCarsCount(profile: OwnerProfile) {
        val carsCount = profile.carCount
        val builder = StringBuilder(carsCount.toString())
        builder.append(mResources.getString(R.string.owner_profile_info_car))
        if (carsCount != 1) {
            builder.append("s")
        }
        mView?.setCarsCount(builder.toString())
    }

    private fun setNote(profile: OwnerProfile) {
        var note = profile.note
        if (note.isNullOrEmpty()) {
            note = mResources.getString(R.string.profile_default_note)
        }
        mView?.setNote(note)

        var city: String? = ""
        val address = profile.address
        if (!address.isNullOrEmpty()) {
            val geocoder = Geocoder(CardeeApp.context, Locale.getDefault())
            var addressList = geocoder.getFromLocationName(address, 1)
            if (addressList.isNotEmpty()) {
                city = addressList[0].locality
            }
        }
        if (city.isNullOrEmpty()) {
            city = mResources.getString(R.string.owner_profile_info_default_city)
        }
        mView?.setNoteTitle(city)
    }

    private fun setResponseTime(profile: OwnerProfile) {
        val responseTime = profile.responseTime
        if (responseTime == null) {
            mView?.setResponseText("0")
        } else {
            mView?.setResponseText(responseTime)
        }

        var minutes = mResources.getString(R.string.owner_profile_info_minute)
        if (responseTime.toInt() != 1) {
            minutes += "s"
        }
        mView?.setMinutes(minutes)
    }

    private fun setProfileAcceptance(profile: OwnerProfile) {
        val accept: Float? = profile.acceptance
        if (accept == null) {
            mView?.setAcceptance("0")
        } else {
            if (accept % 1 == 0F) {
                mView?.setAcceptance(String.format(Locale.getDefault(), "%d", accept.toInt()))
            } else {
                mView?.setAcceptance(String.format(Locale.getDefault(), "%.1f", accept))
            }
        }
    }

    private fun setProfileRating(profile: OwnerProfile) {
        val rate: Float? = profile.rating
        if (rate != null) {
            if (rate % 1 == 0F) {
                mView?.setProfileRating(String.format(Locale.getDefault(), "%d", rate.toInt()))
            } else {
                mView?.setProfileRating(String.format(Locale.getDefault(), "%.1f", rate))
            }
        }
    }

    override fun accept(car: Car?) {
        mView?.openItem(car)
    }

    fun changeNote(context: Context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_profile_change_note,
                mResources.getString(R.string.profile_info_note_change_title),
                mResources.getString(R.string.profile_info_note_change), object : DialogHelper.OnClickCallback {
            override fun onPositiveButtonClick(newNote: String?, dialog: DialogInterface?) {
                val changeNoteRequest = ChangeNoteRequest()
                changeNoteRequest.note = newNote
                mExecutor.execute(mChangeNote, ChangeNote.RequestValues(changeNoteRequest), object : UseCase.Callback<ChangeNote.ResponseValues> {
                    override fun onSuccess(response: ChangeNote.ResponseValues?) {
                        dialog?.dismiss()
                        mView?.setNote(newNote)
                        mView?.showMessage(R.string.profile_info_note_change_success)
                    }

                    override fun onError(error: Error?) {
                        dialog?.dismiss()
                        mView?.showMessage(error?.message)
                    }
                })
            }

            override fun onNegativeButtonClick(dialog: DialogInterface?) {
                dialog?.dismiss()
            }
        }).show()
    }

    fun setProfilePicture(photo: File) {
        mView?.showProgress(true)
        mExecutor.execute(mChangeImage, ChangeImage.RequestValues(null, null, photo, null), object : UseCase.Callback<ChangeImage.ResponseValues> {
            override fun onSuccess(response: ChangeImage.ResponseValues?) {
                mView?.showProgress(false)
                mView?.showMessage(R.string.owner_profile_info_photo_success)
                mView?.onChangeImageSuccess()
            }

            override fun onError(error: Error?) {
                mView?.showProgress(false)
                mView?.showMessage(error?.message)
            }
        })
    }
}

