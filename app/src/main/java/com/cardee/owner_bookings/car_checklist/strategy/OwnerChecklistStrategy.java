package com.cardee.owner_bookings.car_checklist.strategy;


import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;

public class OwnerChecklistStrategy extends PresentationStrategy implements View.OnClickListener {

    private ChecklistView mChecklistView;
    private ActionListener mActionListener;


    public OwnerChecklistStrategy(View view, ActionListener listener) {
        super(view, listener);

        mChecklistView = (ChecklistView) view;
        mActionListener = listener;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) view.getContext()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mChecklistView.getResources().getColor(R.color.status_bar_secondary));
        }

        mChecklistView.toolbarTitle.setText(R.string.owner_handover_checklist_title);
        mChecklistView.petrolMileageView.switchMileageVisibility(false);
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
