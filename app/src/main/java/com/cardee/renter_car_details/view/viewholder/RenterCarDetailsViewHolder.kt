package com.cardee.renter_car_details.view.viewholder

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity
import com.cardee.domain.owner.entity.Image
import com.cardee.domain.renter.entity.RenterDetailedCar
import com.cardee.renter_car_details.view.RenterCarDetailsActivity
import com.cardee.util.AvailabilityFromFilterDelegate
import com.cardee.util.DateStringDelegate
import com.cardee.util.glide.CircleTransform
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*
import kotlinx.android.synthetic.main.view_renter_car_details_info_page.view.*

class RenterCarDetailsViewHolder(private val mActivity: RenterCarDetailsActivity) : TabLayout.OnTabSelectedListener {


    private val mGlideRequestManager: RequestManager? = Glide.with(mActivity)
    private var renterDetailedCar: RenterDetailedCar? = null
    private var hourly: Boolean? = true

    init {
        mActivity.tlRenterCarDetails.apply {
            addOnTabSelectedListener(this@RenterCarDetailsViewHolder)
            setupWithViewPager(mActivity.vpRenterCarDetailsInfoPager)
        }
    }

    fun bind(renterDetailedCar: RenterDetailedCar) {
        this.renterDetailedCar = renterDetailedCar
        fillToolBar()
        mActivity.car_image_pager.adapter = ImagePagerAdapter(mActivity, renterDetailedCar.images)
        mActivity.vpRenterCarDetailsInfoPager.adapter = RentalPagerAdapter(renterDetailedCar)
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
                mActivity.tv_bookButtonText.setText(R.string.renter_car_details_book_instantly)
                mActivity.iv_bookButtonInstant.visibility = View.VISIBLE
                hourly = true
            }
            1 -> {
                mActivity.tv_bookButtonText.setText(R.string.renter_car_details_book)
                mActivity.iv_bookButtonInstant.visibility = View.GONE
                hourly = false
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


    private inner class RentalPagerAdapter(val renterDetailedCar: RenterDetailedCar) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val contentPage = ContentPage.values()[position]
            val inflater = LayoutInflater.from(mActivity)
            val layout = inflater.inflate(R.layout.view_renter_car_details_info_page, container, false) as ViewGroup

            when (contentPage) {
                ContentPage.DAILY -> {
                    layout.tv_renterCarDetailsTimingTitle.setText(R.string.renter_car_details_timing)
                    setPickupAndReturnTime(layout)
                    setRentalRates(true, layout)
                    setDeliveryRates(true, layout)
                    setFuelPolicyText(true, layout)
                }
                ContentPage.HOURLY -> {
                    layout.tv_renterCarDetailsTimingTitle.setText(R.string.renter_car_details_timing_available)
                    AvailabilityFromFilterDelegate().setHourlyAvailabilityRange(mActivity, layout.tv_renterCarDetailsTimingText, renterDetailedCar)
                    setRentalRates(false, layout)
                    setDeliveryRates(false, layout)
                    setFuelPolicyText(false, layout)
                }
            }
            container.addView(layout)
            return layout
        }

        override fun getPageTitle(position: Int): CharSequence {
            val customPagerEnum = ContentPage.values()[position]
            return mActivity.getString(customPagerEnum.titleResId)
        }

        override fun getCount(): Int {
            return ContentPage.values().size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        private fun setRentalRates(daily: Boolean, root: ViewGroup) {
            root.tv_renterCarDetailsRatesText.text = if (!daily) {
                "$" + renterDetailedCar.orderHourlyDetails?.amntRateFirst.toString() + " " +
                        mActivity.getString(R.string.car_rental_rates_per_hour_peak) + "\n" +
                        "$" + renterDetailedCar.orderHourlyDetails?.amntRateSecond.toString() + " " +
                        mActivity.getString(R.string.car_rental_rates_per_hour_off_peak)
            } else {
                "$" + renterDetailedCar.orderDailyDetails?.amntRateFirst.toString() + " " +
                        mActivity.getString(R.string.car_rental_rates_per_day_weekday) + "\n" +
                        "$" + renterDetailedCar.orderDailyDetails?.amntRateSecond.toString() + " " +
                        mActivity.getString(R.string.car_rental_rates_per_day_weekday_and)
            }
        }

        private fun setPickupAndReturnTime(root: ViewGroup) {
            DateStringDelegate(mActivity).apply {
                val text: String = getPickupTime(renterDetailedCar.orderDailyDetails?.timePickup) + "\n" +
                        getReturnTime(renterDetailedCar.orderDailyDetails?.timeReturn)
                root.tv_renterCarDetailsTimingText.text = text
            }
        }

        private fun setFuelPolicyText(daily: Boolean, root: ViewGroup) {
            root.tv_renterCarDetailsFuelText.text = if (daily) {
                val fuelPolicyEntity: FuelPolicyEntity? = renterDetailedCar.orderDailyDetails?.fuelPolicy
                when (fuelPolicyEntity?.fuelPolicyId) {
                    0 -> {
                        fuelPolicyEntity.fuelPolicyName
                    }
                    1 -> {
                        fuelPolicyEntity.fuelPolicyName + " @ \$" + fuelPolicyEntity.amountPayMileage.toString() +
                                " " + mActivity.getString(R.string.car_rental_rates_per_km)
                    }
                    else -> {
                        ""
                    }
                }

            } else {
                val fuelPolicyEntity: FuelPolicyEntity? = renterDetailedCar.orderHourlyDetails?.fuelPolicy
                when (fuelPolicyEntity?.fuelPolicyId) {
                    0 -> {
                        fuelPolicyEntity.fuelPolicyName
                    }
                    1 -> {
                        fuelPolicyEntity.fuelPolicyName + " @ \$" + fuelPolicyEntity.amountPayMileage.toString() +
                                " " + mActivity.getString(R.string.car_rental_rates_per_km)
                    }
                    else -> {
                        ""
                    }
                }
            }
        }

        private fun setDeliveryRates(daily: Boolean, root: ViewGroup) {
            root.tv_renterCarDetailsDeliveryText.text = if (daily) {
                mActivity.getString(R.string.car_rental_delivery_rates_prefix) + " @ \$" + renterDetailedCar.deliveryRates?.baseRate +
                        " + $" + renterDetailedCar.deliveryRates?.distanceRate +
                        mActivity.getString(R.string.car_rental_rates_per_km)
            } else {
                mActivity.getString(R.string.car_rental_delivery_rates_self)
            }
        }
    }

    enum class ContentPage private constructor(val titleResId: Int) {
        HOURLY(R.string.car_rental_info_hourly),
        DAILY(R.string.car_rental_info_daily)
    }

    private fun fillToolBar() {
        mActivity.toolbar_title.text = renterDetailedCar?.carTitle
        mActivity.tvRenterCarDetailsTitleYear.text = renterDetailedCar?.year
    }

    private fun fillAboutInfo() {
        val text = renterDetailedCar?.bodyType + " " + renterDetailedCar?.seatingCapacity +
                mActivity.getString(R.string.renter_car_details_seats_suffix) +
                renterDetailedCar?.carEngineCapacity + " " + renterDetailedCar?.carTransmission
        mActivity.tvRenterCarDetailsAboutCarTitle.text = text
        mActivity.tvRenterCarDetailsAboutCarDesc.text = renterDetailedCar?.description ?: ""
    }

    private fun setTripsCount() {
        val text = "\u2022" + renterDetailedCar?.trips
        mActivity.tvRenterCarDetailsRatingText.text = text
    }

    private fun fillReview() {
        mActivity.rbRenterCarDetailsRating.score = renterDetailedCar?.rating!!
        setTripsCount()
        mActivity.tvRenterCarDetailsCommentName.text = renterDetailedCar?.reviews?.get(0)?.profile?.name
        val dateText = mActivity.getString(R.string.renter_car_details_review_date_prefix) + " " +
                DateStringDelegate(mActivity).getDateWithoutTimeString(renterDetailedCar?.reviews?.get(0)?.dateCreated)
        mActivity.tvRenterCarDetailsCommentDate.text = dateText
        mActivity.tvRenterCarDetailsComment.text = renterDetailedCar?.reviews?.get(0)?.comment
        val readMoreText = mActivity.getString(R.string.renter_car_details_review_more_prefix) + " " +
                renterDetailedCar?.reviews?.size + " " +
                if (renterDetailedCar?.reviewCount ?: 0 > 1) mActivity.getString(R.string.renter_car_details_review_more_suffix)
                else mActivity.getString(R.string.renter_car_details_review_more_suffix_single)
        mActivity.tvRenterCarDetailsReviewMore.text = readMoreText
    }

    private fun fillOwner() {
        mActivity.tvRenterCarDetailsOwnerName.text = renterDetailedCar?.owner?.name
        Glide.with(mActivity)
                .load(renterDetailedCar?.owner?.photo)
                .error(mActivity.resources.getDrawable(R.drawable.ic_photo_placeholder))
                .transform(CircleTransform(mActivity))
                .into(mActivity.ivRenterCarDetailsOwnerPicture)
        mActivity.tvRenterCarDetailsOwnerAcceptance.text = renterDetailedCar?.owner?.acceptance.toString()

        val responseText = renterDetailedCar?.owner?.responseTime.toString() + " " +
                if (renterDetailedCar?.owner?.responseTime ?: 0 > 1) mActivity.getString(R.string.owner_profile_info_minutes)
                else mActivity.getString(R.string.owner_profile_info_minute)
        mActivity.tvRenterCarDetailsOwnerResponse.text = responseText
    }

    private fun fillBookView() {
        mActivity.rbBookCarRating.score = renterDetailedCar?.rating!!
        updateRate()
    }

    private fun updateRate() {
        val rateText = if (hourly == true) "$" + renterDetailedCar?.orderHourlyDetails?.amntRateFirst + " " +
                mActivity.getString(R.string.car_rental_rates_per_hour)
        else "$" + renterDetailedCar?.orderDailyDetails?.amntRateFirst + " " +
                mActivity.getString(R.string.car_rental_rates_per_day)
        mActivity.tvBookCarRate.text = rateText
    }

    fun isHourly(): Boolean? {
        return hourly
    }
}

