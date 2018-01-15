package com.cardee.owner_bookings.car_checklist.presenter;

import android.net.Uri;

import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy;

public interface ChecklistContract {

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void onDestroy();

        void onHandingOverProcessing(boolean showProgress);

    }

    interface Presenter extends BasePresenter, PresentationStrategy.ActionListener {

        void setView(View view);

        void setViewCallbacks(OwnerChecklistPresenter.View callbacks);

        void setViewRenterUpdatedCallbacks(OwnerRenterUpdatedChecklistPresenter.View callbacks);

        void setStrategy(PresentationStrategy strategy);

        void getChecklist();

        void onAddNewImage(Uri uri);

        void onDestroy();

    }
}
