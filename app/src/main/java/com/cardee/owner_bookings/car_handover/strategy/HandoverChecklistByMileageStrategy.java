package com.cardee.owner_bookings.car_handover.strategy;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cardee.R;
import com.cardee.owner_bookings.car_handover.adapter.CarImagesAdapter;
import com.cardee.owner_bookings.car_handover.view.ChecklistView;

public class HandoverChecklistByMileageStrategy extends PresentationStrategy implements View.OnClickListener {

    private ChecklistView mChecklistView;
    private ActionListener mActionListener;


    public HandoverChecklistByMileageStrategy(View view, ActionListener listener) {
        super(view, listener);

        mChecklistView = (ChecklistView) view;
        mActionListener = listener;

        mChecklistView.toolbarTitle.setText(R.string.owner_handover_checklist_title);
        mChecklistView.title1.setText(R.string.owner_handover_checklist_master_title);
        mChecklistView.petrolMileageView.setButtonsVisibility(View.INVISIBLE);
        mChecklistView.imagesGrid.setLayoutManager(new GridLayoutManager(mChecklistView.getContext(), 2,
                GridLayoutManager.VERTICAL, false));
        mChecklistView.imagesGrid.setItemAnimator(new DefaultItemAnimator());
        mChecklistView.handoverB.setOnClickListener(this);

    }

    public void setMasterMileageValue(String txt) {
        if (mChecklistView == null) {
            return;
        }
        mChecklistView.petrolMileageView.setValue(txt);
    }

    public void setImagesAdapter(CarImagesAdapter adapter) {
        if (mChecklistView == null) {
            return;
        }
        mChecklistView.imagesGrid.setAdapter(adapter);
    }

    public String getRemarksText() {
        if (mChecklistView == null) {
            return "";
        }
        return mChecklistView.remarksET.getText().toString();
    }

    @Override
    public void onClick(View view) {
        if (mActionListener == null)
            return;

        if (view.getId() == R.id.b_handoverCar) {
            mActionListener.onHandover();
        }
    }
}
