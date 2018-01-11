package com.cardee.owner_bookings.car_checklist;

import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy;

public interface ChecklistContract {

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void onDestroy();

    }

    interface Presenter extends BasePresenter, PresentationStrategy.ActionListener {

        void setView(View view);

        void setStrategy(PresentationStrategy strategy);

        void getChecklist();

        void onDestroy();

    }
}
