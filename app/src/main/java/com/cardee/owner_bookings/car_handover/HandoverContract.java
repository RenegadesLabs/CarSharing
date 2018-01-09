package com.cardee.owner_bookings.car_handover;

import com.cardee.mvp.BaseView;

public interface HandoverContract {

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void onDestroy();

    }

    interface Presenter {

    }
}
