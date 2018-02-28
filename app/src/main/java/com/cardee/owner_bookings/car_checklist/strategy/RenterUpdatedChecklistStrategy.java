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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) view.getContext()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mChecklistView.getResources().getColor(R.color.status_bar_secondary));
        }

        mChecklistView.petrolMileageView.setVisibility(View.GONE);
        mChecklistView.petrolDescTV.setVisibility(View.VISIBLE);
        mChecklistView.imagesGrid.setLayoutManager(new GridLayoutManager(mChecklistView.getContext(), 4,
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
