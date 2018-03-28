package com.cardee.owner_car_details.view.viewholder

import android.content.Context
import android.content.Intent
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
import com.cardee.CardeeApp
import com.cardee.R
import com.cardee.custom.modal.*
import com.cardee.domain.owner.entity.RentalDetails
import com.cardee.owner_car_details.AvailabilityContract
import com.cardee.owner_car_details.RentalDetailsContract
import com.cardee.owner_car_details.presenter.StrategyRentalDetailPresenter
import com.cardee.owner_car_details.view.OwnerCarRentalFragment
import com.cardee.owner_car_details.view.eventbus.DailyTimingEventBus
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent
import com.cardee.owner_car_details.view.listener.ChildProgressListener
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity
import com.cardee.owner_car_rental_info.rates.RentalRatesActivity
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity
import com.cardee.util.DateRepresentationDelegate
import com.cardee.util.StringFormatDelegate
import kotlinx.android.synthetic.main.view_rental_daily.view.*
import java.util.*


class DailyRentalViewHolder(rootView: View, activity: AppCompatActivity) : BaseViewHolder<RentalDetails>(rootView, activity), View.OnClickListener, RentalDetailsContract.ControlView, DailyTimingEventBus.Listener, CompoundButton.OnCheckedChangeListener {

    private val availabilityDays: TextView
    private val timingPickup: TextView
    private val timingReturn: TextView
    private val availabilityDaysEdit: View
    private val timeEdit: View
    private val settingsHelp: View
    private val instantBookingTitle: TextView
    private val instantBookingIcon: AppCompatImageView
    private val instantBookingSwitch: SwitchCompat
    private val instantBookingEdit: TextView
    //    private TextView curbsideDeliveryTitle;
    //    private AppCompatImageView curbsideDeliveryIcon;
    //    private SwitchCompat curbsideDeliverySwitch;
    //    private TextView curbsideDeliveryEdit;
    private val acceptCashTitle: TextView
    private val acceptCashImage: AppCompatImageView
    private val acceptCashSwitch: SwitchCompat
    private val rentalRatesEdit: View
    private val rentalRatesValueFirst: TextView //1 of 3 (temporary)
    private val rentalRatesValueSecond: TextView
    private val rentalDiscount: TextView
    private val fuelPolicyEdit: View
    private val fuelPolicyValue: TextView
    private val rentalTermsEdit: View

    private var dailyRental: RentalDetails? = null
    private val presenter: StrategyRentalDetailPresenter
    private var progressListener: ChildProgressListener? = null
    private var stringDelegate: StringFormatDelegate? = null
    private var dateDeletage: DateRepresentationDelegate? = null
    private var currentToast: Toast? = null


    init {
        DailyTimingEventBus.getInstance().setListener(this)
        presenter = StrategyRentalDetailPresenter(this, StrategyRentalDetailPresenter.Strategy.DAILY)
        availabilityDays = rootView.findViewById(R.id.availability_days)
        timingPickup = rootView.findViewById(R.id.tv_rentalAvailableTimingPickup)
        timingReturn = rootView.findViewById(R.id.tv_rentalAvailableTimingReturn)
        availabilityDaysEdit = rootView.findViewById(R.id.tv_rentalAvailabilityEdit)
        timeEdit = rootView.findViewById(R.id.tv_rentalTimingEdit)
        settingsHelp = rootView.findViewById(R.id.iv_rentalHelp)
        instantBookingTitle = rootView.findViewById(R.id.instant_booking)
        instantBookingIcon = rootView.findViewById(R.id.icon_instant)
        instantBookingSwitch = rootView.findViewById(R.id.sw_rentalInstant)
        instantBookingEdit = rootView.findViewById(R.id.tv_rentalInstantEdit)
        //        curbsideDeliveryTitle = rootView.findViewById(R.id.curbside_delivery);
        //        curbsideDeliveryIcon = rootView.findViewById(R.id.icon_curbside);
        //        curbsideDeliverySwitch = rootView.findViewById(R.id.sw_rentalDelivery);
        //        curbsideDeliveryEdit = rootView.findViewById(R.id.tv_rentalCurbsideRatesEdit);
        acceptCashImage = rootView.findViewById(R.id.icon_cash)
        acceptCashTitle = rootView.findViewById(R.id.accept_cash)
        acceptCashSwitch = rootView.findViewById(R.id.sw_rentalCash)
        rentalRatesEdit = rootView.findViewById(R.id.tv_rentalRentalRatesEdit)
        rentalRatesValueFirst = rootView.findViewById(R.id.tv_rentalValueFirst)
        rentalRatesValueSecond = rootView.findViewById(R.id.tv_rentalValueSecond)
        rentalDiscount = rootView.findViewById(R.id.tv_rentalDiscount)
        fuelPolicyEdit = rootView.findViewById(R.id.tv_rentalFuelEdit)
        fuelPolicyValue = rootView.findViewById(R.id.tv_rentalFuelValue)
        rentalTermsEdit = rootView.findViewById(R.id.cl_rentalTermsContainer)
        availabilityDaysEdit.setOnClickListener(this)
        timeEdit.setOnClickListener(this)
        instantBookingEdit.setOnClickListener(this)
        //        curbsideDeliveryEdit.setOnClickListener(this);
        rootView.minimumBookingValue.setOnClickListener(this)
        rentalRatesEdit.setOnClickListener(this)
        fuelPolicyEdit.setOnClickListener(this)
        rentalTermsEdit.setOnClickListener(this)
        settingsHelp.setOnClickListener(this)
        instantBookingSwitch.setOnCheckedChangeListener(this)
        //        curbsideDeliverySwitch.setOnCheckedChangeListener(this);
        acceptCashSwitch.setOnCheckedChangeListener(this)
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(this)
        initResources(activity)
    }

    private fun initResources(context: Context) {
        setInstantViewsState(instantBookingSwitch.isChecked)
        //        setDeliveryViewsState(curbsideDeliverySwitch.isChecked());
        setCashViewState(acceptCashSwitch.isChecked)
        stringDelegate = StringFormatDelegate(context)
        dateDeletage = DateRepresentationDelegate(context)
    }

    override fun bind(model: RentalDetails) {
        dailyRental = model
        presenter.onBind(model)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cl_rentalTermsContainer -> {
                val iTerms = Intent(activity,
                        RentalTermsActivity::class.java)
                iTerms.putExtra(RentalTermsActivity.CAR_ID, dailyRental!!.carId)
                activity.startActivity(iTerms)
            }
            R.id.tv_rentalFuelEdit -> {
                val iFuel = Intent(activity,
                        RentalFuelPolicyActivity::class.java)
                iFuel.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.DAILY)
                activity.startActivity(iFuel)
            }
            R.id.tv_rentalAvailabilityEdit -> {
                val menuFragment = AvailabilityMenuFragment
                        .getInstance(dailyRental?.carId
                                ?: return, AvailabilityContract.Mode.DAILY)
                menuFragment.show(activity?.supportFragmentManager, menuFragment.tag)

//                val intent = Intent(activity, AvailabilityCalendarActivity::class.java)
//                val args = Bundle()
//                args.putInt(AvailabilityContract.CAR_ID, dailyRental!!.carId)
//                args.putSerializable(AvailabilityContract.CALENDAR_MODE, AvailabilityContract.Mode.DAILY)
//                intent.putExtras(args)
//                activity.startActivity(intent)
            }
            R.id.tv_rentalTimingEdit -> DailyAvailabilityTimingFragment.newInstance(
                    dateDeletage!!.formatHour(dailyRental!!.dailyTimePickup),
                    dateDeletage!!.formatHour(dailyRental!!.dailyTimeReturn))
                    .show(activity.supportFragmentManager,
                            DailyAvailabilityTimingFragment::class.java.simpleName)
            R.id.tv_rentalInstantEdit -> {
                val menu = BookingPickerMenuFragment.getInstance(instantBookingEdit.text.toString(),
                        BookingPickerMenuFragment.Mode.BOOKING_DAYS)
                menu.show(activity.supportFragmentManager, menu.tag)
                menu.setOnDoneClickListener { value ->
                    instantBookingEdit.text = value
                    presenter.updateInstantBookingCount(Integer.valueOf(value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])!!)
                }
            }
            R.id.minimumBookingValue -> {
                val menu = MinRentDurationFragment.getInstance(rootView.minimumBookingValue.text.toString(),
                        MinRentDurationFragment.Mode.DAILY)
                menu.show(activity.supportFragmentManager, menu.tag)
                menu.setOnDoneClickListener(PickerMenuFragment.DialogOnClickListener { value ->
                    rootView.minimumBookingValue.text = value
                    presenter.updateDailyMinimumBooking(
                            Integer.valueOf(value
                                    .split(" ".toRegex())
                                    .dropLastWhile { it.isEmpty() }
                                    .toTypedArray()[0]))
                })
            }
        //            case R.id.tv_rentalCurbsideRatesEdit:
        //                Intent iDelivery = new Intent(getActivity(), RentalDeliveryRatesActivity.class);
        //                iDelivery.putExtra(RentalDeliveryRatesActivity.BASE_RATE, dailyRental.getDeliveryRates().getBaseRate());
        //                iDelivery.putExtra(RentalDeliveryRatesActivity.DISTANCE_RATE, dailyRental.getDeliveryRates().getDistanceRate());
        //                iDelivery.putExtra(RentalDeliveryRatesActivity.PROVIDE_FREE, dailyRental.getDeliveryRates().getProvideFreeDelivery());
        //                iDelivery.putExtra(RentalDeliveryRatesActivity.RENTAL_DURATION, dailyRental.getDeliveryRates().getRentalDuration());
        //                getActivity().startActivity(iDelivery);
        //                break;
            R.id.tv_rentalRentalRatesEdit -> {
                val iRates = Intent(activity,
                        RentalRatesActivity::class.java)
                iRates.putExtra(RentalRatesActivity.RATE_FIRST, dailyRental!!.dailyAmountRateFirst.toString())
                iRates.putExtra(RentalRatesActivity.RATE_SECOND, dailyRental!!.dailyAmountRateSecond.toString())
                iRates.putExtra(RentalRatesActivity.DISCOUNT_FIRST, Math.round(dailyRental!!.dailyAmountDiscountFirst!!).toString())
                iRates.putExtra(RentalRatesActivity.DISCOUNT_SECOND, Math.round(dailyRental!!.dailyAmountDiscountSecond!!).toString())
                iRates.putExtra(RentalRatesActivity.MIN_RENTAL, dailyRental!!.dailyMinRentalDuration)
                iRates.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.DAILY)
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
        //            case R.id.sw_rentalDelivery:
        //                setDeliveryViewsState(b);
        //                presenter.updateCurbsideDelivery(b);
        //                break;
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

    //    private void setDeliveryViewsState(boolean enabled) {
    //        curbsideDeliveryEdit.setClickable(enabled);
    //        if (enabled) {
    //            curbsideDeliveryIcon.setImageResource(R.drawable.ic_curbside);
    //            curbsideDeliveryTitle.setTextColor(getActivity().getResources().getColor(R.color.text_enabled));
    //            curbsideDeliveryEdit.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
    //        } else {
    //            curbsideDeliveryIcon.setImageResource(R.drawable.ic_direction_inactive);
    //            curbsideDeliveryTitle.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
    //            curbsideDeliveryEdit.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
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
        dateDeletage?.onSetPickupTime(timingPickup, rentalDetails.dailyTimePickup)

        val pickupTime = dateDeletage?.convertTimeToDate(rentalDetails.dailyTimePickup)
        val returnTime = dateDeletage?.convertTimeToDate(rentalDetails.dailyTimeReturn)
        if (isNextDay(pickupTime, returnTime) == true) {
            dateDeletage?.onSetReturnTime(timingReturn, rentalDetails.dailyTimeReturn, true)
        } else {
            dateDeletage?.onSetReturnTime(timingReturn, rentalDetails.dailyTimeReturn, false)
        }

        stringDelegate?.onDateCountValueChange(availabilityDays, rentalDetails.dailyCount)
        stringDelegate?.onSetDailyRentalRateFirst(rentalRatesValueFirst, rentalDetails.dailyAmountRateFirst)
        stringDelegate?.onSetDailyRentalRateSecond(rentalRatesValueSecond, rentalDetails.dailyAmountRateSecond)
        stringDelegate?.onSetDailyRentalDiscount(rentalDiscount, rentalDetails.dailyAmountDiscountFirst)
        stringDelegate?.onSetFuelPolicy(fuelPolicyValue, rentalDetails.dailyFuelPolicyName, "")
        setInstantBookingState(rentalDetails)
        var days = rentalDetails.dailyInstantBookingCount ?: 0
        var template = "%d days"
        if (days == 1) {
            template = "%d day"
        }
        instantBookingEdit.text = String.format(Locale.getDefault(), template, days)
        //        setCurbsideDeliveryState(rentalDetails);
        setMinRentDurationState(rentalDetails)
        days = rentalDetails.dailyMinRentalDuration ?: 0
        template = if (days == 1) {
            "%d day"
        } else {
            "%d days"
        }
        rootView.minimumBookingValue.text = String.format(Locale.getDefault(), template, days)
        setAcceptCashState(rentalDetails)
    }

    private fun setMinRentDurationState(rentalDetails: RentalDetails) {
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(null)
        rootView.minimumBookingSwitch.isChecked = rentalDetails.dailyMinRentalDuration != 0
        setMinimumBookingViewState(rootView.minimumBookingSwitch.isChecked)
        rootView.minimumBookingSwitch.setOnCheckedChangeListener(this)
    }

    private fun setInstantBookingState(rentalDetails: RentalDetails) {
        instantBookingSwitch.setOnCheckedChangeListener(null)
        instantBookingSwitch.isChecked = rentalDetails.isDailyInstantBooking
        setInstantViewsState(rentalDetails.isDailyInstantBooking)
        instantBookingSwitch.setOnCheckedChangeListener(this)
    }

    //    private void setCurbsideDeliveryState(RentalDetails rentalDetails) {
    //        curbsideDeliverySwitch.setOnCheckedChangeListener(null);
    //        curbsideDeliverySwitch.setChecked(rentalDetails.isDailyCurbsideDelivery());
    //        setDeliveryViewsState(rentalDetails.isDailyCurbsideDelivery());
    //        curbsideDeliverySwitch.setOnCheckedChangeListener(this);
    //    }

    private fun setAcceptCashState(rentalDetails: RentalDetails) {
        acceptCashSwitch.setOnCheckedChangeListener(null)
        acceptCashSwitch.isChecked = rentalDetails.isDailyAcceptCash
        setCashViewState(rentalDetails.isDailyAcceptCash)
        acceptCashSwitch.setOnCheckedChangeListener(this)
    }

    override fun onSave(event: TimingSaveEvent) {
        dateDeletage?.onSetPickupTime(timingPickup, event.timeBegin)

        val pickupTime = dateDeletage?.convertTimeToDate(event.timeBegin)
        val returnTime = dateDeletage?.convertTimeToDate(event.timeEnd)
        if (isNextDay(pickupTime, returnTime) == true) {
            dateDeletage?.onSetReturnTime(timingReturn, event.timeEnd, true)
        } else {
            dateDeletage?.onSetReturnTime(timingReturn, event.timeEnd, false)
        }

        dailyRental?.dailyTimePickup = event.timeBegin
        dailyRental?.dailyTimeReturn = event.timeEnd
        presenter.updateAvailabilityTiming(event.timeBegin, event.timeEnd)
    }

    private fun isNextDay(begin: Date?, end: Date?): Boolean? {
        val calBegin = Calendar.getInstance(Locale.US)
        calBegin.timeZone = CardeeApp.getTimeZone()
        calBegin.time = begin ?: return null

        val calEnd = Calendar.getInstance(Locale.US)
        calEnd.timeZone = CardeeApp.getTimeZone()
        calEnd.time = end ?: return null

        return calBegin.get(Calendar.AM_PM) == calEnd.get(Calendar.AM_PM)
    }
}
