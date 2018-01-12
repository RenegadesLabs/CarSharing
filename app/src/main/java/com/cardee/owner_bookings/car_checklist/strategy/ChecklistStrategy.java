package com.cardee.owner_bookings.car_checklist.strategy;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;

public class ChecklistStrategy extends PresentationStrategy implements View.OnClickListener {

    private ChecklistView mChecklistView;
    private ActionListener mActionListener;


    public ChecklistStrategy(View view, ActionListener listener) {
        super(view, listener);

        mChecklistView = (ChecklistView) view;
        mActionListener = listener;

        mChecklistView.toolbarTitle.setText(R.string.owner_handover_checklist_title);
//        mChecklistView.petrolMileageView.setButtonsVisibility(View.INVISIBLE);
        mChecklistView.imagesGrid.setLayoutManager(new GridLayoutManager(mChecklistView.getContext(), 4,
                GridLayoutManager.VERTICAL, false));
        mChecklistView.imagesGrid.setItemAnimator(new DefaultItemAnimator());
        mChecklistView.handoverB.setOnClickListener(this);

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
