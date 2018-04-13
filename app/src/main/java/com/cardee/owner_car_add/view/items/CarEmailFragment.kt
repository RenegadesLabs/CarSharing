package com.cardee.owner_car_add.view.items

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R
import com.cardee.domain.owner.entity.CarData
import com.cardee.owner_car_add.NewCarFormsContract
import com.cardee.owner_car_details.view.listener.DetailsChangedListener


class CarEmailFragment : Fragment(), NewCarFormsContract.View {

    companion object {
        fun newInstance(): CarEmailFragment {
            return CarEmailFragment()
        }
    }

    private var parentListener: DetailsChangedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DetailsChangedListener) {
            parentListener = context
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is DetailsChangedListener) {
            parentListener = activity
        }
    }

    override fun onStart() {
        super.onStart()
        parentListener?.onModeDisplayed(NewCarFormsContract.Mode.MOBILE)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_add_car_email, container, false)
        return v
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showMessage(message: String?) {

    }

    override fun showMessage(messageId: Int) {
    }

    override fun setCarData(carData: CarData?) {

    }

    override fun onFinish() {
        parentListener?.onFinish(NewCarFormsContract.Mode.PAYMENT, null)
    }

    override fun onNoSavedCar() {
    }
}