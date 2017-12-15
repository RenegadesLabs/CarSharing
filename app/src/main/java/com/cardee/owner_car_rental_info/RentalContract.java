package com.cardee.owner_car_rental_info;

import com.cardee.mvp.BaseView;

public interface RentalContract extends BaseView {

    interface View extends BaseView {
        void onSuccess();
    }

    interface Presenter {
        void save(Object... objects);
    }
}
