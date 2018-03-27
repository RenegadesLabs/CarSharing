package com.cardee.owner_car_details.view.viewholder


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import com.cardee.R
import com.cardee.custom.modal.BookingPickerMenuFragment
import com.cardee.custom.modal.HourlyAvailabilityTimingFragment
import com.cardee.custom.modal.PickerMenuFragment
import com.cardee.domain.owner.entity.RentalDetails
import com.cardee.owner_car_details.AvailabilityContract
import com.cardee.owner_car_details.RentalDetailsContract
import com.cardee.owner_car_details.presenter.StrategyRentalDetailPresenter
import com.cardee.owner_car_details.view.AvailabilityCalendarActivity
import com.cardee.owner_car_details.view.OwnerCarRentalFragment
import com.cardee.owner_car_details.view.eventbus.HourlyTimingEventBus
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent
import com.cardee.owner_car_details.view.listener.ChildProgressListener
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity
import com.cardee.owner_car_rental_info.rates.RentalRatesActivity
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity
import com.cardee.util.DateRepresentationDelegate
import com.cardee.util.StringFormatDelegate
import kotlinx.android.synthetic.main.view_rental_hourly.view.*
import java.util.*

class HourlyRentalViewHolder(rootView: View, activity: AppCompatActivity) : BaseViewHolder<RentalDetails>(rootView, activity), View.OnClickListener, RentalDetailsContract.ControlView, HourlyTimingEventBus.Listener, CompoundButton.OnCheckedChangeListener {


    private val availabilityDays: TextView
    private val timing: TextView
    private val availabilityDaysEdit: View
    private val timeEdit: View
    private val instantBookingTitle: TextView
    private val instantBookingIcon: AppCompatImageView
    private val instantBookingSwitch: SwitchCompat
    private val instantBookingEdit: TextView
    private val settingsHelp: View
    //    private val curbsideDeliveryTitle: TextView
//    private val curbsideDeliveryIcon: AppCompatImageView
//    private val curbsideDeliverySwitch: SwitchCompat
//    private val curbsideDeliveryEdit: TextView
    private val acceptCashTitle: TextView
    private val acceptCashImage: AppCompatImageView
    private val acceptCashSwitch: SwitchCompat
    private val rentalRatesEdit: View
    private val rentalRatesValueFirst: TextView
    private val rentalRatesValueSecond: TextView
    private val rentalMinimum: TextView
    private val fuelPolicyEdit: View
    private val fuelPolicyValue: TextView
    private val rentalTermsEdit: View

    private var hourlyRental: RentalDetails? = null
    private var stringDelegate: StringFormatDelegate? = null
    private var dateDelegate: DateRepresentationDelegate? = null
    private val presenter: StrategyRentalDetailPresenter = StrategyRentalDetailPresenter(this, StrategyRentalDetailPresenter.Strategy.HOURLY)
    private var progressListener: ChildProgressListener? = null
    private var currentToast: Toast? = null


    init {
        HourlyTimingEventBus.getInstance().setListener(this)
        availabilityDays = rootView.findViewById(R.id.availability_days)
        timing = rootView.findViewById(R.id.tv_rentalAvailableTimingValue)
        availabilityDaysEdit = rootView.findViewById(R.id.tv_rentalAvailabilityEdit)
        timeEdit = rootView.findViewById(R.id.tv_rentalTimingEdit)
        settingsHelp = rootView.findViewById(R.id.iv_rentalHelp)
        instantBookingTitle = rootView.findViewById(R.id.instant_booking)
        instantBookingIcon = rootView.findViewById(R.id.icon_instant)
        instantBookingSwitch = rootView.findViewById(R.id.sw_rentalInstant)
        instantBookingEdit = rootView.findViewById(R.id.tv_rentalInstantEdit)
//        curbsideDeliveryTitle = rootView.findViewById(R.id.curbside_delivery)
//        curbsideDeliveryIcon = rootView.findViewById(R.id.icon_curbside)
//        curbsideDeliverySwitch = rootView.findViewById(R.id.sw_rentalDelivery)
//        curbsideDeliveryEdit = rootView.findViewById(R.id.tv_rentalCurbsideRatesEdit)
        acceptCashImage = rootView.findViewById(R.id.icon_cash)
        acceptCashTitle = rootView.findViewById(R.id.accept_cash)
        acceptCashSwitch = rootView.findViewById(R.id.sw_rentalCash)
        rentalRatesEdit = rootView.findViewById(R.id.tv_rentalRentalRatesEdit)
        rentalRatesValueFirst = rootView.findViewById(R.id.tv_rentalValueFirst)
        rentalRatesValueSecond = rootView.findViewById(R.id.tv_rentalValueSecond)
        rentalMinimum = rootView.findViewById(R.id.tv_rentalMinimumValue)
        fuelPolicyEdit = rootView.findViewById(R.id.tv_rentalFuelEdit)
        fuelPolicyValue = rootView.findViewById(R.id.tv_rentalFuelValue)
        rentalTermsEdit = rootView.findViewById(R.id.cl_rentalTermsContainer)
        availabilityDaysEdit.setOnClickListener(this)
        timeEdit.setOnClickListener(this)
        instantBookingEdit.setOnClickListener(this)
//        curbsideDeliveryEdit.setOnClickListener(this)
        rootView.minimumBookingValue.setOnClickListener(this)
        rentalRatesEdit.setOnClickListener(this)
        fuelPolicyEdit.setOnClickListener(this)
        rentalTermsEdit.setOnClickListener(this)
        settingsHelp.setOnClickListener(this)
        instantBookingSwitch.setOnCheckedChangeListener(this)
//        curbsideDeliverySwitch.setOnCheckedChangeListener(this)
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(this)
        acceptCashSwitch.setOnCheckedChangeListener(this)
        initResources(activity)
    }

    private fun initResources(context: Context) {
        setInstantViewsState(instantBookingSwitch.isChecked)
//        setDeliveryViewsState(curbsideDeliverySwitch.isChecked)
        setCashViewState(acceptCashSwitch.isChecked)
        stringDelegate = StringFormatDelegate(context)
        dateDelegate = DateRepresentationDelegate(context)
    }

    override fun bind(model: RentalDetails) {
        hourlyRental = model
        presenter.onBind(model)
    }

    override fun onClick(view: View) {
        if (hourlyRental == null) {
            return
        }
        when (view.id) {
            R.id.cl_rentalTermsContainer -> {
                val iTerms = Intent(activity,
                        RentalTermsActivity::class.java)
                iTerms.putExtra(RentalTermsActivity.CAR_ID, hourlyRental!!.carId)
                activity.startActivity(iTerms)
            }
            R.id.tv_rentalFuelEdit -> {
                val iFuel = Intent(activity, RentalFuelPolicyActivity::class.java)
                iFuel.putExtra(RentalFuelPolicyActivity.POLICY_ID, hourlyRental!!.hourlyFuelPolicyId)
                iFuel.putExtra(RentalFuelPolicyActivity.AMOUNT_MILEAGE, hourlyRental!!.hourlyAmountPayMileage)
                iFuel.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.HOURLY)
                activity.startActivity(iFuel)
            }
            R.id.tv_rentalAvailabilityEdit -> {
                val intent = Intent(activity, AvailabilityCalendarActivity::class.java)
                val args = Bundle()
                args.putInt(AvailabilityContract.CAR_ID, hourlyRental!!.carId)
                args.putSerializable(AvailabilityContract.CALENDAR_MODE, AvailabilityContract.Mode.HOURLY)
                intent.putExtras(args)
                activity.startActivity(intent)
            }
            R.id.tv_rentalTimingEdit -> HourlyAvailabilityTimingFragment.newInstance(
                    dateDelegate!!.formatHour(hourlyRental!!.hourlyBeginTime),
                    dateDelegate!!.formatHour(hourlyRental!!.hourlyEndTime))
                    .show(activity.supportFragmentManager,
                            HourlyAvailabilityTimingFragment::class.java.simpleName)
            R.id.tv_rentalInstantEdit -> {
                val menu = BookingPickerMenuFragment.getInstance(instantBookingEdit.text.toString(),
                        BookingPickerMenuFragment.Mode.BOOKING_HOURS)
                menu.show(activity.supportFragmentManager, menu.tag)
                menu.setOnDoneClickListener { value ->
                    instantBookingEdit.text = value
                    presenter.updateInstantBookingCount(Integer.valueOf(value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])!!)
                }
            }
            R.id.minimumBookingValue -> {
                val menu = PickerMenuFragment.getInstance(rootView.minimumBookingValue.text.toString(),
                        PickerMenuFragment.Mode.MINIMUM_RENTAL_DURATION_HOURLY)
                menu.show(activity.supportFragmentManager, menu.tag)
                menu.setOnDoneClickListener { value ->
                    rootView.minimumBookingValue.text = value
                    presenter.updateHourlyMinimumBooking(Integer.valueOf(value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]))
                }
            }
//            R.id.tv_rentalCurbsideRatesEdit -> {
//                val iDelivery = Intent(activity, RentalDeliveryRatesActivity::class.java)
//                iDelivery.putExtra(RentalDeliveryRatesActivity.BASE_RATE, hourlyRental!!.deliveryRates.baseRate)
//                iDelivery.putExtra(RentalDeliveryRatesActivity.DISTANCE_RATE, hourlyRental!!.deliveryRates.distanceRate)
//                iDelivery.putExtra(RentalDeliveryRatesActivity.PROVIDE_FREE, hourlyRental!!.deliveryRates.provideFreeDelivery)
//                iDelivery.putExtra(RentalDeliveryRatesActivity.RENTAL_DURATION, hourlyRental!!.deliveryRates.rentalDuration)
//                activity.startActivity(iDelivery)
//            }
            R.id.tv_rentalRentalRatesEdit -> {
                val iRates = Intent(activity, RentalRatesActivity::class.java)
                iRates.putExtra(RentalRatesActivity.RATE_FIRST, hourlyRental!!.hourlyAmountRateFirst.toString())
                iRates.putExtra(RentalRatesActivity.RATE_SECOND, hourlyRental!!.hourlyAmountRateSecond.toString())
                iRates.putExtra(RentalRatesActivity.DISCOUNT_FIRST, Math.round(hourlyRental!!.hourlyAmountDiscountFirst!!).toString())
                iRates.putExtra(RentalRatesActivity.DISCOUNT_SECOND, Math.round(hourlyRental!!.hourlyAmountDiscountSecond!!).toString())
                iRates.putExtra(RentalRatesActivity.MIN_RENTAL, hourlyRental!!.hourlyMinRentalDuration)
                iRates.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.HOURLY)
                activity.startActivity(iRates)
            }
            R.id.iv_rentalHelp -> showInfoDialog()
        }
    }

    private fun showInfoDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val rootView = inflater.inflate(R.layout.dialog_rental_info_settings, null)
        builder.setView(rootView)
        val dialog = builder.create()
        val buttonOk = rootView.findViewById<AppCompatButton>(R.id.button_ok)
        buttonOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, b: Boolean) {
        when (compoundButton.id) {
            R.id.sw_rentalInstant -> {
                setInstantViewsState(b)
                presenter.updateInstantBooking(b)
            }
//            R.id.sw_rentalDelivery -> {
//                setDeliveryViewsState(b)
//                presenter.updateCurbsideDelivery(b)
//            }
            R.id.sw_rentalCash -> {
                setCashViewState(b)
                presenter.updateAcceptCash(b)
            }
            R.id.minimumBookingSwitch -> {
                setMinimumBookingViewState(b)
                presenter.updateMinimumBooking(b)
            }
        }
    }

    private fun setMinimumBookingViewState(enabled: Boolean) {
        rootView.minimumBookingValue.isClickable = enabled
        rootView.minimumBookingValue.visibility = when (enabled) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    private fun setInstantViewsState(enabled: Boolean) {
        instantBookingEdit.isClickable = enabled
        if (enabled) {
            instantBookingIcon.setImageResource(R.drawable.ic_instant)
            instantBookingTitle.setTextColor(activity.resources.getColor(R.color.text_enabled))
            instantBookingEdit.setTextColor(activity.resources.getColor(R.color.colorPrimary))
        } else {
            instantBookingIcon.setImageResource(R.drawable.ic_instant_inactive)
            instantBookingTitle.setTextColor(activity.resources.getColor(R.color.text_disabled))
            instantBookingEdit.setTextColor(activity.resources.getColor(R.color.text_disabled))
        }
    }

//    private fun setDeliveryViewsState(enabled: Boolean) {
//        curbsideDeliveryEdit.isClickable = enabled
//        if (enabled) {
//            curbsideDeliveryIcon.setImageResource(R.drawable.ic_curbside)
//            curbsideDeliveryTitle.setTextColor(activity.resources.getColor(R.color.text_enabled))
//            curbsideDeliveryEdit.setTextColor(activity.resources.getColor(R.color.colorPrimary))
//        } else {
//            curbsideDeliveryIcon.setImageResource(R.drawable.ic_direction_inactive)
//            curbsideDeliveryTitle.setTextColor(activity.resources.getColor(R.color.text_disabled))
//            curbsideDeliveryEdit.setTextColor(activity.resources.getColor(R.color.text_disabled))
//        }
//    }

    private fun setCashViewState(enabled: Boolean) {
        if (enabled) {
            acceptCashImage.setImageResource(R.drawable.ic_cash)
            acceptCashTitle.setTextColor(activity.resources.getColor(R.color.text_enabled))
        } else {
            acceptCashImage.setImageResource(R.drawable.ic_cash_inactive)
            acceptCashTitle.setTextColor(activity.resources.getColor(R.color.text_disabled))
        }
    }

    override fun showProgress(show: Boolean) {
        if (progressListener != null) {
            progressListener!!.onChildProgressShow(show)
        }
    }

    override fun showMessage(message: String) {
        if (currentToast != null) {
            currentToast!!.cancel()
        }
        currentToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }

    override fun showMessage(@StringRes messageId: Int) {
        showMessage(activity.getString(messageId))
    }

    fun setProgressListener(progressListener: ChildProgressListener) {
        this.progressListener = progressListener
    }

    override fun setData(rentalDetails: RentalDetails) {
        dateDelegate!!.onSetTimeRangeString(timing, rentalDetails.hourlyBeginTime, rentalDetails.hourlyEndTime)
        stringDelegate!!.onDateCountValueChange(availabilityDays, rentalDetails.hourlyCount)
        stringDelegate!!.onSetHourlyRentalRateFirst(rentalRatesValueFirst, rentalDetails.hourlyAmountRateSecond)
        stringDelegate!!.onSetHourlyRentalRateSecond(rentalRatesValueSecond, rentalDetails.hourlyAmountRateFirst)
        stringDelegate!!.onSetRentalMinimum(rentalMinimum, rentalDetails.hourlyMinRentalDuration)
        val amtMileage = rentalDetails.hourlyAmountPayMileage
        if (amtMileage == null) {
            stringDelegate!!.onSetFuelPolicy(fuelPolicyValue, rentalDetails.hourlyFuelPolicyName, null)
        } else {
            stringDelegate!!.onSetFuelPolicy(fuelPolicyValue, rentalDetails.hourlyFuelPolicyName, amtMileage.toString())
        }
        setInstantBookingState(rentalDetails)
        var hours = rentalDetails.hourlyInstantBookingCount ?: 0
        var template = "%d hours"
        if (hours == 1) {
            template = "%d hour"
        }
        instantBookingEdit.text = String.format(Locale.getDefault(), template, hours)
//        setCurbsideDeliveryState(rentalDetails)
        setMinRentDurationState(rentalDetails)
        hours = rentalDetails.hourlyMinRentalDuration ?: 0
        template = if (hours == 1) {
            "%d hour"
        } else {
            "%d hours"
        }
        rootView.minimumBookingValue.text = String.format(Locale.getDefault(), template, hours)
        setAcceptCashState(rentalDetails)
    }

    private fun setMinRentDurationState(rentalDetails: RentalDetails) {
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(null)
        rootView.minimumBookingSwitch.isChecked = rentalDetails.hourlyMinRentalDuration != 0
        setMinimumBookingViewState(rootView.minimumBookingSwitch.isChecked)
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(this)
    }

    private fun setInstantBookingState(rentalDetails: RentalDetails) {
        instantBookingSwitch.setOnCheckedChangeListener(null)
        instantBookingSwitch.isChecked = rentalDetails.isHourlyInstantBooking
        setInstantViewsState(rentalDetails.isHourlyInstantBooking)
        instantBookingSwitch.setOnCheckedChangeListener(this)
    }

//    private fun setCurbsideDeliveryState(rentalDetails: RentalDetails) {
//        curbsideDeliverySwitch.setOnCheckedChangeListener(null)
//        curbsideDeliverySwitch.isChecked = rentalDetails.isHourlyCurbsideDelivery
//        setDeliveryViewsState(rentalDetails.isHourlyCurbsideDelivery)
//        curbsideDeliverySwitch.setOnCheckedChangeListener(this)
//    }

    private fun setAcceptCashState(rentalDetails: RentalDetails) {
        acceptCashSwitch.setOnCheckedChangeListener(null)
        acceptCashSwitch.isChecked = rentalDetails.isHourlyAcceptCash
        setCashViewState(rentalDetails.isHourlyAcceptCash)
        acceptCashSwitch.setOnCheckedChangeListener(this)
    }

    override fun onSave(event: TimingSaveEvent) {
        dateDelegate!!.onSetTimeRangeString(timing, event.timeBegin, event.timeEnd)
        presenter.updateAvailabilityTiming(event.timeBegin, event.timeEnd)
    }
}
