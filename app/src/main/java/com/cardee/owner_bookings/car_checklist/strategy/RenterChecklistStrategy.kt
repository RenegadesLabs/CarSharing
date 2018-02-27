package com.cardee.owner_bookings.car_checklist.strategy

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.cardee.R
import com.cardee.owner_bookings.car_checklist.view.ChecklistView
import kotlinx.android.synthetic.main.view_checklist_accurate.view.*


class RenterChecklistStrategy(val renterView: ChecklistView, val actionListener: ActionListener) : PresentationStrategy(renterView, actionListener), View.OnClickListener {

    init {
        renterView.toolbar.navigationIcon = null
        renterView.toolbar.setBackgroundColor(view!!.resources.getColor(R.color.colorPrimary))
        renterView.toolbarTitle.setText(R.string.renter_checklist_title)

        renterView.petrolMileageView.visibility = View.GONE
        renterView.petrolDescTV.visibility = View.VISIBLE
        renterView.imagesGrid.layoutManager = GridLayoutManager(renterView.context, 4,
                GridLayoutManager.VERTICAL, false)
        renterView.imagesGrid.itemAnimator = DefaultItemAnimator()
        renterView.remarksContainer.background = null
        renterView.remarksET.visibility = View.GONE
        renterView.remarksText.visibility = View.VISIBLE
        renterView.handoverContainer.visibility = View.GONE
        renterView.viewAccurateContainer.visibility = View.VISIBLE
        renterView.viewAccurateContainer.text.setText(R.string.renter_checklist_bottom_hint)
        renterView.completeYesB.setOnClickListener(this)
        renterView.completeNoB.setOnClickListener(this)
    }

    fun setRemarksText(txt: String) {
        if (view == null) {
            return
        }
        renterView.remarksText.text = txt
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.b_completeHandoverYES -> actionListener.onAccurateConfirm()
            R.id.b_completeHandoverNO -> actionListener.onAccurateCancel()
        }

    }
}