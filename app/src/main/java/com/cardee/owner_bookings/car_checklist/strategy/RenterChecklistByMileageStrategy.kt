package com.cardee.owner_bookings.car_checklist.strategy

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.cardee.R
import com.cardee.owner_bookings.car_checklist.view.ChecklistView
import kotlinx.android.synthetic.main.activity_owner_handover_checklist.view.*


class RenterChecklistByMileageStrategy(renterView: ChecklistView, private val actionListener: ActionListener) :
        PresentationStrategy(renterView, actionListener), View.OnClickListener {

    init {
        renterView.toolbar.setBackgroundColor(renterView.context.resources.getColor(R.color.colorPrimary))
        renterView.toolbarTitle.setText(R.string.renter_checklist_edit_title)
        renterView.title1.setText(R.string.owner_handover_checklist_master_title)
        renterView.handoverB.setText(R.string.renter_checklist_update_button)
        renterView.handover_info.setText(R.string.renter_checklist_edit_bottom_hint)

        renterView.petrolMileageView.setButtonsVisibility(View.INVISIBLE)
        renterView.petrolMileageView.switchMileageVisibility(true)
        renterView.imagesGrid.layoutManager = GridLayoutManager(renterView.context, 4,
                GridLayoutManager.VERTICAL, false)
        renterView.imagesGrid.itemAnimator = DefaultItemAnimator()
        renterView.handoverB.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.b_handoverCar) {
            actionListener.onHandover()
        }
    }
}