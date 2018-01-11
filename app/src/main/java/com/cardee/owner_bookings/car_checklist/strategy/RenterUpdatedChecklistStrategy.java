package com.cardee.owner_bookings.car_checklist.strategy;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.adapter.CarImagesAdapter;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;


public class RenterUpdatedChecklistStrategy extends PresentationStrategy implements View.OnClickListener {

    private ChecklistView mChecklistView;

    private ActionListener mActionListener;


    public RenterUpdatedChecklistStrategy(View view, ActionListener listener) {
        super(view, listener);

        mChecklistView = (ChecklistView) view;
        mActionListener = listener;

        mChecklistView.toolbar.setNavigationIcon(null);
        mChecklistView.toolbar.setBackgroundColor(mChecklistView.getResources().getColor(R.color.rate_star));
        mChecklistView.toolbarTitle.setText(R.string.owner_handover_checklist_renter_changed);

        mChecklistView.petrolMileageView.setVisibility(View.GONE);
        mChecklistView.petrolDescTV.setVisibility(View.VISIBLE);
        mChecklistView.imagesGrid.setLayoutManager(new GridLayoutManager(mChecklistView.getContext(), 2,
                GridLayoutManager.VERTICAL, false));
        mChecklistView.imagesGrid.setItemAnimator(new DefaultItemAnimator());
        mChecklistView.remarksContainer.setBackground(null);
        mChecklistView.remarksET.setVisibility(View.GONE);
        mChecklistView.remarksText.setVisibility(View.VISIBLE);
        mChecklistView.handoverContainer.setVisibility(View.GONE);
        mChecklistView.viewAccurateContainer.setVisibility(View.VISIBLE);
        mChecklistView.completeYesB.setOnClickListener(this);
        mChecklistView.completeNoB.setOnClickListener(this);

    }

    public void setPetrolDescription(String txt) {
        if (mChecklistView == null) {
            return;
        }
        mChecklistView.petrolDescTV.setText(txt);
    }

    public void setImagesAdapter(CarImagesAdapter adapter) {
        if (mChecklistView == null) {
            return;
        }
        mChecklistView.imagesGrid.setAdapter(adapter);
    }

    public void setRemarksText(String txt) {
        if (mChecklistView == null) {
            return;
        }
        mChecklistView.remarksText.setText(txt);
    }

    @Override
    public void onClick(View view) {

        if (mActionListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.b_completeHandoverYES:
                mActionListener.onAccurateConfirm();
                break;
            case R.id.b_completeHandoverNO:
                mActionListener.onAccurateCancel();
                break;
        }

    }
}
