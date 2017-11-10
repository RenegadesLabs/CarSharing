package com.cardee.owner_car_add.view;

import android.support.annotation.StringRes;

import com.cardee.R;
import com.cardee.domain.owner.entity.NewCar;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface NewCarFormsContract {

    String VIEW_MODE = "_view_mode";

    enum Mode {
        TYPE(R.string.car_add_vehicle_title),
        INFO(R.string.car_add_info_car_title),
        IMAGE(R.string.car_add_image_title),
        LOCATION(R.string.car_add_location_title),
        CONTACT(R.string.car_add_contact_title),
        PAYMENT(R.string.car_add_payment_title);

        @StringRes
        private int titleId;

        Mode(int titleId) {
            this.titleId = titleId;
        }

        public int getTitleId() {
            return titleId;
        }
    }

    interface View extends BaseView {

        void setCarData(NewCar carData);

    }

    interface Presenter extends BasePresenter {

        void onCarDataResponse(NewCar carData);

    }
}
