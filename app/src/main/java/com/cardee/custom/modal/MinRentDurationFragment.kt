package com.cardee.custom.modal

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.NumberPicker
import com.cardee.R
import kotlinx.android.synthetic.main.modal_dialog_rent_duration_picker.view.*
import java.util.*


class MinRentDurationFragment : BottomSheetDialogFragment() {

    companion object {
        private var mMode: MinRentDurationFragment.Mode? = null

        private var mValues: Array<String>? = null

        private var mSelectedValue: String? = null

        fun getInstance(selected: String, mode: Mode): MinRentDurationFragment {
            val fragment = MinRentDurationFragment()
            mSelectedValue = selected
            mMode = mode
            mValues = when (mode) {
                MinRentDurationFragment.Mode.HOURLY -> initMinRentDurationHours()
                MinRentDurationFragment.Mode.DAILY -> initMinRentDurationDays()
            }
            return fragment
        }

        private fun initMinRentDurationDays(): Array<String> {
            val days = IntArray(7)
            val values = ArrayList<String>()
            for (i in days.indices) {
                days[i] = i + 1
                if (i == 0) {
                    values.add(days[i].toString() + " day")
                } else {
                    values.add(days[i].toString() + " days")
                }
            }
            return values.toTypedArray()
        }

        private fun initMinRentDurationHours(): Array<String> {
            val hours = IntArray(7)
            val values = ArrayList<String>()
            for (i in hours.indices) {
                hours[i] = i + 1
                if (i == 0) {
                    values.add(hours[i].toString() + " hour")
                } else {
                    values.add(hours[i].toString() + " hours")
                }
            }
            return values.toTypedArray()
        }
    }

    enum class Mode {
        HOURLY, DAILY
    }

    interface DialogOnClickListener {
        fun onDoneClicked(value: String)
    }

    private var mListener: PickerMenuFragment.DialogOnClickListener? = null

    fun setOnDoneClickListener(listener: PickerMenuFragment.DialogOnClickListener) {
        mListener = listener
    }

    private val mCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val rootView = View.inflate(context, R.layout.modal_dialog_rent_duration_picker, null)
        dialog.setContentView(rootView)

        val title = rootView.menu_title
        when (mMode) {
            MinRentDurationFragment.Mode.HOURLY -> {
                title.setText(R.string.car_rental_info_minimum_booking_hourly)
                rootView.picker_title.setText(R.string.dialog_rent_duration_picker_hourly)
            }
            MinRentDurationFragment.Mode.DAILY -> {
                title.setText(R.string.car_rental_info_minimum_booking_daily)
                rootView.picker_title.setText(R.string.dialog_rent_duration_picker_daily)
            }
        }

        val np = rootView.findViewById<NumberPicker>(R.id.np_dialogNumberPicker)
        setDividerColor(np, activity.resources.getColor(android.R.color.transparent))
        np.displayedValues = mValues
        np.maxValue = mValues!!.size - 1
        np.minValue = 0

        if (mSelectedValue != null && mSelectedValue != "") {
            for (i in mValues!!.indices) {
                if (mValues!![i] == mSelectedValue) {
                    np.value = i
                    break
                }
            }
        }

        rootView.findViewById<View>(R.id.b_dialogNumberPickerCancel).setOnClickListener { dismiss() }

        rootView.findViewById<View>(R.id.b_dialogNumberPickerDone).setOnClickListener(View.OnClickListener {
            if (mListener == null) {
                return@OnClickListener
            }
            dismiss()
            mListener!!.onDoneClicked(mValues!![np.value])
        })

        val params = (rootView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mCallback)
        }
        val parent = rootView.parent
        if (parent != null) {
            (parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun setDividerColor(picker: NumberPicker, color: Int) {

        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(color)
                    pf.set(picker, colorDrawable)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
    }

}
