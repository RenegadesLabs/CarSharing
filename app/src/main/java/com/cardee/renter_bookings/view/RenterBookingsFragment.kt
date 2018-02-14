package com.cardee.renter_bookings.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cardee.R
import com.cardee.domain.bookings.BookingState
import com.cardee.domain.bookings.usecase.ObtainBookings
import com.cardee.owner_bookings.OwnerBookingListContract
import com.cardee.owner_bookings.view.BookingListAdapter
import com.cardee.renter_bookings.presenter.RenterBookingsListPresenter
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpActivity
import com.cardee.settings.SettingsManager
import kotlinx.android.synthetic.main.fragment_renter_bookings.*
import kotlinx.android.synthetic.main.fragment_renter_bookings.view.*

class RenterBookingsFragment : Fragment(), OwnerBookingListContract.View {

    private var presenter: OwnerBookingListContract.Presenter? = null
    private var adapter: BookingListAdapter? = null
    private var progress: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RenterBookingsListPresenter(this, SettingsManager.getInstance(activity).obtainSettings())
        adapter = BookingListAdapter(presenter, activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_renter_bookings, container, false)
//        button?.setOnClickListener { view ->
//            val intent = Intent(activity, RateRentalExpActivity::class.java)
//            intent.putExtra("booking_id", 430)
//            startActivity(intent)
//        }
        initViews(root)
        return root
    }

    override fun onStart() {
        super.onStart()
        presenter?.init()
    }

    private fun initViews(root: View?) {
        root?.renterBookingsTabLayout?.apply {
            getTabAt(1)?.customView?.alpha = 0.4f
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab.customView?.alpha = 1f
                    when(tab.position) {
                        0 -> presenter?.setFilter(null)
                        1 -> presenter?.setFilter(BookingState.COMPLETED)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.customView?.alpha = 0.4f
                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
        root?.booking_list?.apply {
            adapter = this@RenterBookingsFragment.adapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        progress = root?.progress_layout
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress?.visibility = View.VISIBLE
            return
        }
        progress?.visibility = View.GONE
    }

    override fun showMessage(message: String?) {
    }

    override fun showMessage(messageId: Int) {
    }

    override fun invalidate() {
        adapter?.notifyDataSetChanged()
    }

    override fun displaySortType(sort: ObtainBookings.Sort?) {

    }

    override fun displayFilterType(filter: BookingState?) {

    }

    override fun openBooking(bookingId: Int?) {

    }

    companion object {
        fun newInstance(): RenterBookingsFragment {
            return RenterBookingsFragment()
        }
    }

}