package com.cardee.owner_bookings.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.cardee.custom.CustomRatingBar
import com.cardee.data_source.inbox.local.chat.entity.Chat.*
import com.cardee.data_source.remote.api.booking.response.entity.BookingCost
import com.cardee.data_source.remote.service.AccountManager
import com.cardee.domain.bookings.BookingState
import com.cardee.domain.bookings.entity.Booking
import com.cardee.inbox.chat.single.view.ChatActivity
import com.cardee.owner_bookings.OwnerBookingContract
import com.cardee.owner_bookings.car_returned.view.CarReturnedActivity
import com.cardee.renter_book_car.rental_period.RentalPeriodActivity
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpActivity
import com.cardee.util.glide.CircleTransform
import kotlinx.android.synthetic.main.view_booking.view.*

class BookingView : CoordinatorLayout, OwnerBookingContract.View {


    private var presenter: OwnerBookingContract.Presenter? = null
    private var currentToast: Toast? = null
    private var imageManager: RequestManager? = null

    var toolbar: android.support.v7.widget.Toolbar? = null
    var toolbarTitle: TextView? = null
    var bookingStatus: TextView? = null
    var bookingPayment: TextView? = null
    var carPhotoView: ImageView? = null
    var imgProgress: ProgressBar? = null
    var carTitle: TextView? = null
    var carYear: TextView? = null
    var carPlateNumber: TextView? = null
    var bookingCreated: TextView? = null
    var rentalPeriodTitle: TextView? = null
    var rentalPeriod: TextView? = null
    var deliverToTitle: TextView? = null
    var deliverTo: TextView? = null
    var handoverOnTitle: TextView? = null
    var handoverOn: TextView? = null
    var returnByTitle: TextView? = null
    var returnBy: TextView? = null
    var handoverAtTitle: TextView? = null
    var handoverAt: TextView? = null
    var totalCostTitle: TextView? = null
    var totalCost: TextView? = null
    var renterPhoto: ImageView? = null
    var renterPhotoCompleted: ImageView? = null
    var renterNameTitle: TextView? = null
    var renterName: TextView? = null
    var renterMessage: TextView? = null
    var renterCallTitle: TextView? = null
    var renterCall: ImageView? = null
    var renterChatTitle: TextView? = null
    var renterChat: ImageView? = null
    var btnCancel: TextView? = null
    var btnAccept: TextView? = null
    var acceptMessage: TextView? = null
    var cancelMessage: TextView? = null
    var progressView: View? = null
    var ratingEdit: TextView? = null
    var ratingBar: CustomRatingBar? = null
    var ratingBlock: View? = null
    var ratingTitle: TextView? = null
    var earningsContainer: ScrollView? = null
    var receiptsContainer: ScrollView? = null
    var offPeakTitle: TextView? = null
    var offPeakValue: TextView? = null
    var peakTitle: TextView? = null
    var peakValue: TextView? = null
    var discountTitle: TextView? = null
    var discountValue: TextView? = null
    var deliveryTitle: TextView? = null
    var deliveryValue: TextView? = null
    var costTitle: TextView? = null
    var costValue: TextView? = null
    var feeTitle: TextView? = null
    var feeValue: TextView? = null
    var nettEarningTitle: TextView? = null
    var nettEarningsValue: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        imageManager = Glide.with(context)

        toolbar = bookings_toolbar
        toolbarTitle = toolbar_title
        bookingStatus = booking_status
        bookingPayment = booking_payment
        carPhotoView = booking_car_picture
        imgProgress = booking_progress
        carTitle = booking_car_title
        carYear = booking_car_year
        carPlateNumber = booking_car_plate_number
        bookingCreated = booking_date_created
        rentalPeriodTitle = rental_period_title
        rentalPeriod = rental_period
        deliverToTitle = deliver_to_title
        deliverTo = deliver_to
        handoverOnTitle = handover_on_title
        handoverOn = handover_on
        returnByTitle = return_by_title
        returnBy = return_by
        handoverAtTitle = handover_at_title
        handoverAt = handover_at
        totalCostTitle = total_cost_title
        totalCost = total_cost
        renterPhoto = renter_photo
        renterPhotoCompleted = renter_photo_completed
        renterNameTitle = renter_name_title
        renterName = renter_name
        renterMessage = renter_message
        renterCallTitle = renter_call_title
        renterCall = renter_call
        renterChatTitle = renter_chat_title
        renterChat = renter_chat
        btnCancel = btn_cancel
        btnAccept = btn_accept
        acceptMessage = booking_accept_message
        cancelMessage = booking_cancel_message
        progressView = booking_loading_indicator
        ratingEdit = rating_edit
        ratingBar = rating_bar
        ratingBlock = rating_block
        ratingTitle = rating_title
        earningsContainer = earnings_container
        receiptsContainer = receipts_container
        offPeakTitle = earning_rental_off_peak
        offPeakValue = earning_rental_off_peak_value
        peakTitle = earning_rental_peak
        peakValue = earning_rental_peak_value
        discountTitle = earning_rental_discount
        discountValue = earning_rental_discount_value
        deliveryTitle = earning_rental_delivery
        deliveryValue = earning_rental_delivery_value
        costTitle = earning_booking_cost
        costValue = earning_booking_cost_value
        feeTitle = earning_service_fee
        feeValue = earning_service_fee_value
        nettEarningTitle = earning_nett
        nettEarningsValue = earning_nett_value
    }

    override fun showProgress(show: Boolean) {
        progressView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        currentToast?.cancel()
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(context.getString(messageId))
    }

    override fun setPresenter(presenter: OwnerBookingContract.Presenter) {
        this.presenter = presenter
    }

    override fun bind(booking: Booking, isRenter: Boolean) {
        toolbarTitle!!.text = booking.bookingNum
        loadImageIntoView(booking.primaryImage.link,
                R.drawable.img_no_car, carPhotoView, imgProgress, false)
        bookingPayment!!.setBackgroundColor(ContextCompat.getColor(context, booking.paymentType.bgId))
        bookingPayment!!.setText(booking.paymentType.titleId)
        carTitle!!.text = booking.carTitle
        carYear!!.text = booking.manufactureYear
        carPlateNumber!!.text = booking.plateNumber
        val timeBegin = booking.timeBegin
        val timeEnd = booking.timeEnd
        var timePeriod: String? = null
        bookingCreated!!.text = booking.dateCreated
        if (timeBegin != null && timeEnd != null) {
            timePeriod = "$timeBegin - $timeEnd" //'to' changed to '-' for better UX
        }
        rentalPeriod!!.text = timePeriod
        val amountString = if (booking.totalAmount == null) "$0" else "$" + booking.totalAmount!!
        totalCost!!.text = amountString
        handoverOn!!.text = timeBegin
        returnBy!!.text = timeEnd
        handoverAt!!.text = booking.deliveryAddress
        renterMessage!!.text = booking.note
        if (isRenter) {
            renterName!!.text = booking.ownerName
            loadImageIntoView(booking.ownerPhoto,
                    R.drawable.ic_photo_placeholder, renterPhoto, null, true)
            loadImageIntoView(booking.ownerPhoto,
                    R.drawable.ic_photo_placeholder, renterPhotoCompleted, null, true)
        } else {
            renterName!!.text = booking.renterName
            loadImageIntoView(booking.renterPhoto,
                    R.drawable.ic_photo_placeholder, renterPhoto, null, true)
            loadImageIntoView(booking.renterPhoto,
                    R.drawable.ic_photo_placeholder, renterPhotoCompleted, null, true)
        }

        var rating: Int? = null
        if (isRenter) {
            val rates = booking.ownerRates
            if (rates != null) {
                for (rate in rates) {
                    if (rate.rateName == "overall_rental_experience") {
                        rating = rate.rating
                    }
                }
            }
        } else {
            rating = booking.renterRating
        }
        if (rating != null) {
            ratingBar!!.score = rating.toFloat()
            if (rating == 0) {
                if (isRenter) {
                    startRateRentalActivity(booking.bookingId!!)
                }
            }
        } else {
            if (booking.bookingStateType == BookingState.COMPLETED) {
                if (isRenter) {
                    startRateRentalActivity(booking.bookingId!!)
                }
            }
        }

        ratingEdit!!.setOnClickListener {
            if (isRenter) {
                startRateRentalActivity(booking.bookingId!!)
            } else {
                startCarReturnedActivity(booking.bookingId!!)
            }
        }

        if (booking.cost != null) {
            if (isRenter) {
                bindCostForRenter(booking.cost, booking.isBookingByDays)
            } else {
                bindCostTitles(booking.cost, booking.isBookingByDays)
                bindCost(booking.cost)
            }
        }

        renterChat!!.setOnClickListener {
            val attachment = AccountManager.getInstance(this.context).sessionInfo

            val args = Bundle()
            args.putInt(CHAT_SERVER_ID, booking.bookingId!!)
            args.putString(CHAT_ATTACHMENT, attachment)
            args.putInt(CHAT_UNREAD_COUNT, 0)
            args.putBoolean(CHAT_FROM_BOOKING, true)

            val intent = Intent(this.context, ChatActivity::class.java)
            intent.putExtras(args)
            this.context.startActivity(intent)
        }
    }

    private fun startRateRentalActivity(bookingId: Int) {
        val intent = Intent(context, RateRentalExpActivity::class.java)
        intent.putExtra("booking_id", bookingId)
        context.startActivity(intent)
    }

    private fun startCarReturnedActivity(bookingId: Int) {
        val intent = Intent(context, CarReturnedActivity::class.java)
        intent.putExtra("booking_id", bookingId)
        context.startActivity(intent)
    }

    private fun bindCostTitles(cost: BookingCost, bookingByDays: Boolean) {
        var rentalOrdinary = context.getString(R.string.cost_rental_prefix)
        var rentalSpecial = context.getString(R.string.cost_rental_prefix)
        var rentalDiscount = context.getString(R.string.cost_discount_prefix)
        val rentalOrdinarySuffix: String
        val rentalSpecialSuffix: String
        if (bookingByDays) {
            rentalOrdinarySuffix = context.getString(R.string.cost_rental_weekdays_suffix)
            rentalSpecialSuffix = context.getString(R.string.cost_rental_weekends_suffix)
        } else {
            rentalOrdinarySuffix = context.getString(R.string.cost_rental_off_peak_suffix)
            rentalSpecialSuffix = context.getString(R.string.cost_rental_peak_suffix)
        }
        rentalOrdinary = rentalOrdinary + " " + cost.nonPeakCount.toString() + " " + rentalOrdinarySuffix
        rentalSpecial = rentalSpecial + " " + cost.peakCount.toString() + " " + rentalSpecialSuffix
        if (cost.discount != null && cost.discount != 0f) {
            rentalDiscount += (" " + cost.discount.toString() + "%")
        }
        discountTitle!!.text = rentalDiscount
        offPeakTitle!!.text = rentalOrdinary
        peakTitle!!.text = rentalSpecial
    }

    private fun bindCost(cost: BookingCost) {
        val peak = if (cost.peakCost == null) 0f else cost.peakCost
        val nonPeak = if (cost.nonPeakCost == null) 0f else cost.nonPeakCost
        val discountRate = if (cost.discount == null) 0f else -cost.discount
        val discount = peak + nonPeak * discountRate / 100
        val delivery = if (cost.delivery == null) 0f else cost.delivery
        val nettCost = peak + nonPeak + discount + delivery
        val feeRate = if (cost.fee == null) 0f else -cost.fee
        val fee = nettCost * feeRate / 100
        val nettEarnings = nettCost + fee
        onBindCostValue(peakValue, peak)
        onBindCostValue(offPeakValue, nonPeak)
        onBindCostValue(discountValue, discount)
        onBindCostValue(deliveryValue, delivery)
        onBindCostValue(costValue, nettCost)
        onBindCostValue(feeValue, fee)
        onBindCostValue(nettEarningsValue, nettEarnings)
    }

    private fun onBindCostValue(view: TextView?, value: Float) {
        val stringValue: String = if (value < 0) {
            view!!.setTextColor(ContextCompat.getColor(context, R.color.red_error))
            "-$" + (-value).toString()
        } else {
            "$" + value.toString()
        }
        view!!.text = stringValue
    }

    private fun bindCostForRenter(cost: BookingCost, bookingByDays: Boolean) {
        val nonPeakCount = cost.nonPeakCount
        val nonPeakCost = cost.nonPeakCost
        val peakCount = cost.peakCount
        val peakCost = cost.peakCost
        val delivery = cost.delivery
        val fee = cost.fee
        val discount = cost.discount
        val total = cost.total

        if (nonPeakCount == null || nonPeakCount == 0) {
            non_peak_container.visibility = View.GONE
        } else {
            var count = if (bookingByDays) {
                "$nonPeakCount day"
            } else {
                "$nonPeakCount hour"
            }
            if (nonPeakCount != 1) count += "s"

            non_peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_non_peak_template), count)
            non_peak_amount.text = "$%.2f".format(nonPeakCost)
        }

        if (peakCount == null || peakCount == 0) {
            peak_container.visibility = View.GONE
        } else {
            var count = if (bookingByDays) {
                "$peakCount day"
            } else {
                "$peakCount hour"
            }
            if (peakCount != 1) count += "s"

            peak_days.text = String.format(context.resources.getString(R.string.cost_breakdown_peak_template), count)
            peak_amount.text = "$%.2f".format(peakCost)
        }

        if (delivery == null || delivery == 0f) {
            delivery_container.visibility = View.GONE
        } else {
            delivery_amount.text = "$%.2f".format(delivery)
        }

        if (fee == null || fee == 0f) {
            fee_container.visibility = View.GONE
        } else {
            fee_amount.text = "$%.2f".format(fee)
        }

        if (discount == null || discount == 0f) {
            discount_container.visibility = View.GONE
        } else {
            if (discount % 1f == 0f) {
                discount_amount.text = "%.0f%%".format(discount)
            } else {
                discount_amount.text = "$discount%"
            }
        }

        total_amount.text = "$%.2f".format(total)
    }

    override fun bind() {
        val intent = Intent(context, RentalPeriodActivity::class.java)
        intent.putExtra("isHourly", false)
        context.startActivity(intent)
    }

    private fun loadImageIntoView(imUrl: String?, placeholderRes: Int, view: ImageView?, progress: ProgressBar?, circle: Boolean) {
        var imgUrl = imUrl
        imgUrl = if (imgUrl == null) "" else imgUrl
        if (progress != null) {
            progress.visibility = View.VISIBLE
        }
        val builder = imageManager!!
                .load(imgUrl).listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>,
                                             isFirstResource: Boolean): Boolean {
                        progress?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        progress?.visibility = View.GONE
                        return false
                    }
                }).error(placeholderRes)
        if (circle) {
            builder.transform(CircleTransform(context))
        }
        builder.into(view!!)
    }

    override fun onDestroy() {
        presenter = null

        toolbar = null
        toolbarTitle = null
        bookingStatus = null
        bookingPayment = null
        carPhotoView = null
        imgProgress = null
        carTitle = null
        carYear = null
        carPlateNumber = null
        bookingCreated = null
        rentalPeriodTitle = null
        rentalPeriod = null
        deliverToTitle = null
        deliverTo = null
        handoverOnTitle = null
        handoverOn = null
        returnByTitle = null
        returnBy = null
        handoverAtTitle = null
        handoverAt = null
        totalCostTitle = null
        totalCost = null
        renterPhoto = null
        renterPhotoCompleted = null
        renterNameTitle = null
        renterName = null
        renterMessage = null
        renterCallTitle = null
        renterCall = null
        renterChatTitle = null
        renterChat = null
        btnCancel = null
        btnAccept = null
        acceptMessage = null
        cancelMessage = null
        progressView = null
        ratingEdit = null
        ratingBar = null
        ratingBlock = null
        ratingTitle = null
        earningsContainer = null
        receiptsContainer = null
        offPeakTitle = null
        offPeakValue = null
        peakTitle = null
        peakValue = null
        discountTitle = null
        discountValue = null
        deliveryTitle = null
        deliveryValue = null
        costTitle = null
        costValue = null
        feeTitle = null
        feeValue = null
        nettEarningTitle = null
        nettEarningsValue = null
    }

}
