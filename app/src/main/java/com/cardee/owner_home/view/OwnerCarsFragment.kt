package com.cardee.owner_home.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.owner.entity.Car
import com.cardee.owner_home.OwnerCarListContract
import com.cardee.owner_home.presenter.OwnerCarsPresenter
import com.cardee.owner_home.view.adapter.CarListAdapter
import com.cardee.owner_home.view.listener.CarListItemEventListener
import kotlinx.android.synthetic.main.fragment_owner_cars.*

class OwnerCarsFragment : Fragment(), OwnerCarListContract.View {

    private var mAdapter: CarListAdapter? = null
    private var mCarsListView: RecyclerView? = null

    private var mPresenter: OwnerCarsPresenter? = null
    private var mEventListener: CarListItemEventListener? = null
    private var mCurrentToast: Toast? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mEventListener = context as CarListItemEventListener?
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mEventListener = activity as CarListItemEventListener?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = CarListAdapter(activity as Context?)
        mPresenter = OwnerCarsPresenter(this)
        mAdapter?.subscribe(mPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_owner_cars, container, false)
        mCarsListView = rootView.findViewById(R.id.owner_cars_list)
        initCarList(mCarsListView)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setOnRefreshListener {
            mPresenter?.loadItems()
        }
        mPresenter?.refresh()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.loadItems()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.destroy()
        swipeRefresh?.isEnabled = false
    }

    private fun initCarList(listView: RecyclerView?) {
        listView?.adapter = mAdapter
        listView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listView?.itemAnimator = DefaultItemAnimator()
    }

    override fun setItems(items: List<Car>) {
        mAdapter?.insert(items)
    }

    override fun updateItem(car: Car) {
        mAdapter?.update(car)
    }

    override fun removeItem(car: Car) {

    }

    override fun openItem(car: Car) {
        mEventListener?.onCarItemClick(car)
    }

    override fun openDailyPicker(car: Car) {
        mEventListener?.onDailyPickerClick(car)
    }

    override fun openHourlyPicker(car: Car) {
        mEventListener?.onHourlyPickerClick(car)
    }

    override fun openLocationPicker(car: Car) {
        mEventListener?.onLocationPickerClick(car)
    }

    override fun onUnauthorized() {

    }

    override fun onConnectionLost() {
        showMessage(Error.Message.CONNECTION_LOST)
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            mEventListener?.onStartLoading()
        } else {
            mEventListener?.onStopLoading()
            swipeRefresh?.isRefreshing = false
        }
    }

    override fun showMessage(message: String) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    override fun showMessage(@StringRes messageId: Int) {
        showMessage(getString(messageId))
    }

    companion object {

        fun newInstance(): Fragment {
            return OwnerCarsFragment()
        }
    }

}
