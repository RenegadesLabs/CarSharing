package com.cardee.owner_car_details.view;


import android.support.annotation.StringRes;

import com.cardee.R;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface CarDetailsEditContract {

    String VIEW_MODE = "_view_mode";
    String CAR_ID = "_owner_car_id";
    String CAR_LAT = "_owner_car_lat";
    String CAR_LNG = "_owner_car_lng";

    enum Mode {

        LOCATION(R.string.title_owner_car_location),
        DESCRIPTION(R.string.title_owner_car_description),
        SPECS(R.string.title_owner_car_specs),
        IMAGES(R.string.title_owner_car_images);

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

    }

    interface Presenter extends BasePresenter {

    }
}
