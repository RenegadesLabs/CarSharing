package com.cardee.owner_car_rental_info.terms;

import com.cardee.mvp.BaseView;

public interface RentalTermsContract extends BaseView {

    interface View extends BaseView {
        void onSuccess();
    }

    interface Presenter {
        void save(Object... objects);
    }
}
