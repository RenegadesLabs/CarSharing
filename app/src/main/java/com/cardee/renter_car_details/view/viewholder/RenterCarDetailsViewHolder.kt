package com.cardee.renter_car_details.view.viewholder

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.annotation.DrawableRes
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity
import com.cardee.domain.owner.entity.Image
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_car_details.reviews.RenterCarReviewsActivity
import com.cardee.renter_car_details.view.RenterCarDetailsActivity
import com.cardee.util.AvailabilityFromFilterDelegate
import com.cardee.util.DateRepresentationDelegate
import com.cardee.util.glide.CircleTransform
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*
import kotlinx.android.synthetic.main.view_renter_car_details_info_page.view.*
import kotlinx.android.synthetic.main.view_renter_car_details_map.*

class RenterCarDetailsViewHolder(private val mActivity: RenterCarDetailsActivity) : TabLayout.OnTabSelectedListener {


    private val mGlideRequestManager: RequestManager? = Glide.with(mActivity)
    private var renterDetailedCar: RenterDetailedCar? = null
    private var hourly: Boolean? = true
    private val dateDelegate: DateRepresentationDelegate by lazy { DateRepresentationDelegate(mActivity) }

    init {
        mActivity.tlRenterCarDetails.apply {
            addTab(this@apply.newTab().setText(R.string.car_rental_info_hourly), 0)
            addTab(this@apply.newTab().setText(R.string.car_rental_info_daily), 1)
            addOnTabSelectedListener(this@RenterCarDetailsViewHolder)
        }
    }

    fun bind(renterDetailedCar: RenterDetailedCar) {
        this.renterDetailedCar = renterDetailedCar
        fillToolBar()
        mActivity.car_image_pager.adapter = ImagePagerAdapter(mActivity, renterDetailedCar.images)
        toggleHourlyDailyTabs(true)
        toggleBookInstantly(renterDetailedCar.orderHourlyDetails?.instantBooking ?: false)
        fillAboutInfo()
        fillReview()
        fillOwner()
        fillBookView()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                hourly = true
                toggleBookInstantly(renterDetailedCar?.orderHourlyDetails?.instantBooking ?: false)
                toggleHourlyDailyTabs(hourly ?: false)
            }
            1 -> {
                hourly = false
                toggleBookInstantly(renterDetailedCar?.orderDailyDetails?.instantBooking ?: false)
                toggleHourlyDailyTabs(hourly ?: false)
            }
        }
        updateRate()
    }

    private inner class ImagePagerAdapter(context: Context, images: Array<Image>?) : PagerAdapter() {

        @DrawableRes
        private val mDefaultImageId = R.drawable.img_no_car
        private val mInflater: LayoutInflater = LayoutInflater.from(context)
        private var mImages: Array<Image>? = null
        internal var mEmpty = true

        init {
            if (images == null || images.isEmpty()) {
                mEmpty = true
            } else {
                mImages = images
                mEmpty = false
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = mInflater.inflate(R.layout.item_image_slide, container, false)
            val imageView = itemView.findViewById<ImageView>(R.id.image_slide)
            val progressBar = itemView.findViewById<View>(R.id.image_progress_bar)
            if (!mEmpty) {
                progressBar.visibility = View.VISIBLE
                mGlideRequestManager?.load(mImages?.get(position)?.link)?.listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                        ?.error(mDefaultImageId)
                        ?.into(imageView)
            } else {
                imageView.setImageResource(mDefaultImageId)
            }
            container.addView(itemView)
            return itemView
        }

        private fun needRefresh(newImages: Array<Image>): Boolean {
            return mImages == null || mImages?.size != newImages.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return if (mEmpty) 1 else mImages!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }

    private fun toggleHourlyDailyTabs(hourly: Boolean) {
        mActivity.carDetailsInfoView.apply {
            if (hourly) {
                tv_renterCarDetailsTimingTitle.setText(R.string.renter_car_details_timing)
                setPickupAndReturnTime(this.rootView.tv_renterCarDetailsTimingText)
            } else {
                tv_renterCarDetailsTimingTitle.setText(R.string.renter_car_details_timing_available)
                AvailabilityFromFilterDelegate().setHourlyAvailabilityRange(mActivity, this.tv_renterCarDetailsTimingText, renterDetailedCar)
            }
            setRentalRates(hourly, this@apply)
            setDeliveryRates(hourly, this@apply)
            setFuelPolicyText(hourly, this@apply)
        }
    }

    private fun setRentalRates(hourly: Boolean, root: View) {
        root.tv_renterCarDetailsRatesText.text = if (hourly) {
            "$" + renterDetailedCar?.orderHourlyDetails?.amntRateFirst.toString() + " " +
                    mActivity.getString(R.string.car_rental_rates_per_hour_peak) + "\n" +
                    "$" + renterDetailedCar?.orderHourlyDetails?.amntRateSecond.toString() + " " +
                    mActivity.getString(R.string.car_rental_rates_per_hour_off_peak)
        } else {
            "$" + renterDetailedCar?.orderDailyDetails?.amntRateFirst.toString() + " " +
                    mActivity.getString(R.string.car_rental_rates_per_day_weekday) + "\n" +
                    "$" + renterDetailedCar?.orderDailyDetails?.amntRateSecond.toString() + " " +
                    mActivity.getString(R.string.car_rental_rates_per_day_weekday_and)
        }
    }

    private fun setPickupAndReturnTime(view: TextView) {
        val pickupTime = renterDetailedCar?.orderDailyDetails?.timePickup ?: return
        val returnTime = renterDetailedCar?.orderDailyDetails?.timeReturn ?: return
        dateDelegate.onSetPickupReturnTime(view, pickupTime, returnTime)
    }

    private fun setFuelPolicyText(hourly: Boolean, root: View) {
        root.tv_renterCarDetailsFuelText.text = if (!hourly) {
            val fuelPolicyEntity: FuelPolicyEntity? = renterDetailedCar?.orderDailyDetails?.fuelPolicy
            when (fuelPolicyEntity?.fuelPolicyId) {
                1 -> {
                    fuelPolicyEntity.fuelPolicyName
                }
                2 -> {
                    fuelPolicyEntity.fuelPolicyName + " @ \$" + fuelPolicyEntity.amountPayMileage?.toString() +
                            " " + mActivity.getString(R.string.car_rental_rates_per_km)
                }
                else -> {
                    fuelPolicyEntity?.fuelPolicyName
                }
            }

        } else {
            val fuelPolicyEntity: FuelPolicyEntity? = renterDetailedCar?.orderHourlyDetails?.fuelPolicy
            when (fuelPolicyEntity?.fuelPolicyId) {
                1 -> {
                    fuelPolicyEntity.fuelPolicyName
                }
                2 -> {
                    fuelPolicyEntity.fuelPolicyName + " @ \$" + fuelPolicyEntity.amountPayMileage?.toString() +
                            " " + mActivity.getString(R.string.car_rental_rates_per_km)
                }
                else -> {
                    fuelPolicyEntity?.fuelPolicyName
                }
            }
        }
    }

    private fun setDeliveryRates(hourly: Boolean, root: View) {
        root.tv_renterCarDetailsDeliveryText.text = if (!hourly) {
            mActivity.getString(R.string.car_rental_delivery_rates_prefix) + " @ \$" + renterDetailedCar?.deliveryRates?.baseRate +
                    " + $" + renterDetailedCar?.deliveryRates?.distanceRate +
                    mActivity.getString(R.string.car_rental_rates_per_km)
        } else {
            mActivity.getString(R.string.car_rental_delivery_rates_self)
        }
    }

    private fun fillToolBar() {
        mActivity.ivRenterCarDetailsToolbarFavoritesImg.setImageResource(if (renterDetailedCar?.favorite == true) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)

        val title = SpannableString(renterDetailedCar?.carTitle ?: return)
        title.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mActivity.toolbar_title.text = title

        val year = SpannableString("   " + renterDetailedCar?.year ?: return)
        year.setSpan(RelativeSizeSpan(0.9f), 0, year.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mActivity.toolbar_title.append(year)

    }

    private fun fillMapAddressBar() {
        mActivity.addressText.text = renterDetailedCar?.address
    }

    private fun fillAboutInfo() {
        val text = renterDetailedCar?.bodyType + " " + renterDetailedCar?.seatingCapacity +
                mActivity.getString(R.string.renter_car_details_seats_suffix) +
                renterDetailedCar?.carEngineCapacity + " " + renterDetailedCar?.carTransmission
        mActivity.tvRenterCarDetailsAboutCarTitle.text = text
        mActivity.tvRenterCarDetailsAboutCarDesc.text = renterDetailedCar?.description ?: ""
        mActivity.tvRenterCarDetailsAboutCarDesc.visibility =
                if (renterDetailedCar?.description == null) View.GONE else View.VISIBLE
    }

    private fun setTripsCount() {
        val text = "\u2022  " + renterDetailedCar?.trips +
                mActivity.getString(R.string.renter_car_details_review_trips_suffix)
        mActivity.tvRenterCarDetailsRatingText.text = text
    }

    private fun fillReview() {
        mActivity.rbRenterCarDetailsRating.score = renterDetailedCar?.rating ?: 0F
        setTripsCount()
        if (renterDetailedCar?.reviews?.isNotEmpty() == true) {
            mActivity.tvRenterCarDetailsCommentName.text = renterDetailedCar?.reviews?.get(0)?.profile?.name
            renterDetailedCar?.reviews?.get(0)?.dateCreated?.let { date ->
                dateDelegate.onSetMonthDayYear(mActivity.tvRenterCarDetailsCommentDate, date)
            }
            mActivity.tvRenterCarDetailsComment.text = renterDetailedCar?.reviews?.get(0)?.comment
            val readMoreText = mActivity.getString(R.string.renter_car_details_review_more_prefix) + " " +
                    renterDetailedCar?.reviews?.size + " " +
                    if (renterDetailedCar?.reviewCount ?: 0 > 1) mActivity.getString(R.string.renter_car_details_review_more_suffix)
                    else mActivity.getString(R.string.renter_car_details_review_more_suffix_single)
            mActivity.tvRenterCarDetailsReviewMore.text = readMoreText
            mActivity.tvRenterCarDetailsReviewMore.setOnClickListener {
                val intent = Intent(mActivity, RenterCarReviewsActivity::class.java)
                intent.putExtra("carId", renterDetailedCar?.carId)
                mActivity.startActivity(intent)
            }
            mActivity.clReviewContainer.visibility = View.VISIBLE
            return
        }
        mActivity.clReviewContainer.visibility = View.GONE
    }

    private fun fillOwner() {
        mActivity.tvRenterCarDetailsOwnerName.text = renterDetailedCar?.owner?.name
        Glide.with(mActivity)
                .load(renterDetailedCar?.owner?.photo)
                .error(mActivity.resources.getDrawable(R.drawable.ic_photo_placeholder))
                .transform(CircleTransform(mActivity))
                .into(mActivity.ivRenterCarDetailsOwnerPicture)
        val acceptanceText = renterDetailedCar?.owner?.acceptance.toString() + "%"
        mActivity.tvRenterCarDetailsOwnerAcceptance.text = acceptanceText
        val cancellationText = renterDetailedCar?.owner?.cancellation.toString() + "%"
        mActivity.tv_renterCarDetailsOwnerCancellation.text = cancellationText
        val responseText = renterDetailedCar?.owner?.responseTime.toString() + " " +
                if (renterDetailedCar?.owner?.responseTime ?: 0 > 1) mActivity.getString(R.string.owner_profile_info_minutes)
                else mActivity.getString(R.string.owner_profile_info_minute)
        mActivity.tvRenterCarDetailsOwnerResponse.text = responseText
    }

    private fun fillBookView() {
        mActivity.rbBookCarRating.score = renterDetailedCar?.rating ?: 0F
        updateRate()
    }

    private fun updateRate() {
        val rateText = if (hourly == true) "$" + renterDetailedCar?.orderHourlyDetails?.amntRateFirst + " " +
                mActivity.getString(R.string.car_rental_rates_per_hour)
        else "$" + renterDetailedCar?.orderDailyDetails?.amntRateFirst + " " +
                mActivity.getString(R.string.car_rental_rates_per_day)
        mActivity.tvBookCarRate.text = rateText
    }

    private fun toggleBookInstantly(instant: Boolean) {
        if (instant) {
            mActivity.tv_bookButtonText.setText(R.string.renter_car_details_book_instantly)
            mActivity.iv_bookButtonInstant.visibility = View.VISIBLE
            return
        }
        mActivity.tv_bookButtonText.setText(R.string.renter_car_details_book)
        mActivity.iv_bookButtonInstant.visibility = View.GONE
    }

    fun getDetails(): RenterDetailedCar? {
        return renterDetailedCar
    }

    fun isHourly(): Boolean? {
        return hourly
    }
}

