package com.cardee.custom.modal

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.NumberPicker
import com.cardee.CardeeApp
import com.cardee.R
import kotlinx.android.synthetic.main.modal_dialog_date_picker.view.*
import java.util.*
import kotlin.collections.ArrayList


class DatePickerMenuFragment : BottomSheetDialogFragment() {

    companion object {
        const val YEAR = "year"
        const val MONTH = "month"
        const val DATE = "date"
        const val TYPE = "type"

        enum class DATETYPE {
            BIRTHDAY, LICENSE
        }

        fun newInstance(type: DATETYPE, year: Int, month: Int, date: Int): DatePickerMenuFragment {
            val fragment = DatePickerMenuFragment()
            val args = Bundle()
            args.putSerializable(TYPE, type)
            args.putInt(YEAR, year)
            args.putInt(MONTH, month)
            args.putInt(DATE, date)
            fragment.arguments = args
            return fragment
        }
    }

    private var mListener: DialogOnClickListener? = null
    private var yearValues: Array<String> = arrayOf("")
    private var monthValues: Array<String> = arrayOf("")
    private var dateValues: Array<String> = arrayOf("")
    lateinit var curentType: DATETYPE
    private val currentDate = Date()
    private var fullDateValues: Array<String> = arrayOf("")
    private var fullMonthValues: Array<String> = arrayOf("")

    private val callback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val rootView = View.inflate(context, R.layout.modal_dialog_date_picker, null)
        dialog?.setContentView(rootView)
        init(rootView)
        rootView.saveButton.setOnClickListener {
            val year = yearValues[rootView.yearPicker.value]
            val month = monthValues[rootView.monthPicker.value]
            val date = dateValues[rootView.datePicker.value]

            mListener?.onSaveClicked(curentType, "$date $month $year")
            dismiss()
        }
        val params = (rootView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(callback)
        }
        val parent = rootView.parent
        if (parent != null) {
            (parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun init(root: View) {
        val args = arguments
        when (args.getSerializable(TYPE)) {
            DATETYPE.BIRTHDAY -> {
                curentType = DATETYPE.BIRTHDAY
                root.dateDialogTitle.text = CardeeApp.context.resources.getString(R.string.particulars_birthday_dialog_title)
                yearValues = initBirthdayYears()
            }
            DATETYPE.LICENSE -> {
                curentType = DATETYPE.LICENSE
                root.dateDialogTitle.text = CardeeApp.context.resources.getString(R.string.particulars_license_dialog_title)
                yearValues = initLicenseYears()
            }
            else -> {
                yearValues = initLicenseYears()
            }
        }

        root.yearPicker.displayedValues = yearValues
        root.yearPicker.maxValue = yearValues.lastIndex
        root.yearPicker.minValue = 0
        root.yearPicker.wrapSelectorWheel = false

        fullMonthValues = initMonthValues()
        monthValues = fullMonthValues
        setMonthValues(root)

        fullDateValues = initDateValues()
        dateValues = fullDateValues
        setDateValues(root)

        root.yearPicker.value = args.getInt(YEAR) - 1900

        val month = args.getInt(MONTH)
        if (month < 1 || month > 12) {
            root.monthPicker.value = 0
        } else {
            root.monthPicker.value = month - 1
        }

        val date = args.getInt(DATE)
        if (date < 1 || date > 31) {
            root.datePicker.value = 0
        } else {
            root.datePicker.value = date - 1
        }

        setDividerColor(root.yearPicker, activity.resources.getColor(android.R.color.transparent))
        setDividerColor(root.monthPicker, activity.resources.getColor(android.R.color.transparent))
        setDividerColor(root.datePicker, activity.resources.getColor(android.R.color.transparent))

        setListeners(root)
    }

    private fun setListeners(root: View) {
        val calendar = GregorianCalendar()
        calendar.time = currentDate
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDate = calendar.get(Calendar.DATE)

        setYearListener(root, currentYear, currentMonth, currentDate)
        setMonthListener(root, currentYear, currentMonth, currentDate)
    }

    private fun setMonthListener(root: View, currentYear: Int, currentMonth: Int, currentDate: Int) {
        root.monthPicker.setOnValueChangedListener { numberPicker: NumberPicker, oldValue: Int, newValue: Int ->
            if (curentType == DATETYPE.LICENSE) {
                if (currentMonth == newValue) {
                    if (currentYear == root.yearPicker.value + 1900) {
                        dateValues = getAvailableDays(currentDate)
                        setDateValues(root)
                        return@setOnValueChangedListener
                    }
                }
            }

            if (!dateValues.contentEquals(fullDateValues)) {
                dateValues = fullDateValues
                setDateValues(root)
            }
        }
    }

    private fun setYearListener(root: View, currentYear: Int, currentMonth: Int, currentDate: Int) {
        root.yearPicker.setOnValueChangedListener { numberPicker: NumberPicker, oldValue: Int, newValue: Int ->
            if (curentType == DATETYPE.LICENSE) {
                if (currentYear == newValue + 1900) {
                    monthValues = getAvailableMonths(currentMonth)
                    setMonthValues(root)
                    if (currentMonth == root.monthPicker.value) {
                        dateValues = getAvailableDays(currentDate)
                        setDateValues(root)
                        return@setOnValueChangedListener
                    }
                    return@setOnValueChangedListener
                }
            }

            if (!dateValues.contentEquals(fullDateValues)) {
                dateValues = fullDateValues
                setDateValues(root)
            }
            if (!monthValues.contentEquals(fullMonthValues)) {
                monthValues = fullMonthValues
                setMonthValues(root)
            }
        }
    }

    private fun setMonthValues(root: View) {
        root.monthPicker.displayedValues = null
        root.monthPicker.maxValue = monthValues.lastIndex
        root.monthPicker.minValue = 0
        root.monthPicker.displayedValues = monthValues
    }

    private fun setDateValues(root: View) {
        root.datePicker.displayedValues = null
        root.datePicker.maxValue = dateValues.size - 1
        root.datePicker.minValue = 0
        root.datePicker.displayedValues = dateValues
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

    private fun initLicenseYears(): Array<String> {
        val calendar = GregorianCalendar()
        calendar.time = currentDate
        val currentYear = calendar.get(Calendar.YEAR)
        val difference = (currentYear - 1900) + 1
        return Array(difference, { "${it + 1900}" })
    }

    private fun initBirthdayYears(): Array<String> {
        val calendar = GregorianCalendar()
        calendar.time = currentDate
        val currentYear = calendar.get(Calendar.YEAR)
        val difference = currentYear - 1900
        return Array(difference, { "${it + 1900}" })
    }

    private fun initDateValues(): Array<String> {
        return Array(31, { "${it + 1}" })
    }

    private fun getAvailableDays(currentDate: Int): Array<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 1..currentDate) {
            list.add(i.toString())
        }
        return list.toTypedArray()
    }

    private fun initMonthValues(): Array<String> {
        return CardeeApp.context.resources.getStringArray(R.array.month_titles)
    }

    private fun getAvailableMonths(currentMonth: Int): Array<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..currentMonth) {
            list.add(fullMonthValues[i])
        }
        return list.toTypedArray()
    }

    fun setOnSaveClickListener(listener: DialogOnClickListener) {
        mListener = listener
    }

    interface DialogOnClickListener {
        fun onSaveClicked(type: DATETYPE, value: String)
    }
}