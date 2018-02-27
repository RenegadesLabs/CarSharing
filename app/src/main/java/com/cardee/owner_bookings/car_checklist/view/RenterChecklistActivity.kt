package com.cardee.owner_bookings.car_checklist.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.cardee.R
import com.cardee.owner_bookings.car_checklist.presenter.RenterChecklistContract
import com.cardee.owner_bookings.car_checklist.presenter.RenterChecklistPresenter
import com.cardee.renter_home.view.RenterHomeActivity

class RenterChecklistActivity : AppCompatActivity(), RenterChecklistPresenter.View {

    companion object {
        const val KEY_BOOKING_ID = "booking_id"
        const val KEY_IS_RENTER = "is_renter"
    }

    private var presenter: RenterChecklistContract.Presenter? = null
    private var view: RenterChecklistContract.View? = null

    private var bookingId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val checklistView = LayoutInflater
                .from(this).inflate(R.layout.activity_owner_handover_checklist, null) as ChecklistView
        view = checklistView
        bookingId = intent.getIntExtra(KEY_BOOKING_ID, -1)
        presenter = RenterChecklistPresenter(bookingId)
        view?.setPresenter(presenter ?: return)
        presenter?.setView(view ?: return)
        presenter?.setViewRenterCallbacks(this)
        setContentView(checklistView)
        val toolbar = checklistView.getToolbar()
        toolbar.navigationIcon = null
        setSupportActionBar(checklistView.getToolbar())
        supportActionBar?.title = null
    }

    override fun onStart() {
        super.onStart()
        presenter?.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        view?.onDestroy()
    }

    override fun onConfirmed() {
        onBackPressed()
    }

    override fun onCancelled() {
        val intent = Intent(this, ChecklistActivity::class.java)
        intent.putExtra(ChecklistActivity.KEY_BOOKING_ID, bookingId)
        intent.putExtra(ChecklistActivity.KEY_IS_RENTER, true)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, RenterHomeActivity::class.java)
        intent.putExtra(RenterHomeActivity.TAB_TO_SELECT, 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
