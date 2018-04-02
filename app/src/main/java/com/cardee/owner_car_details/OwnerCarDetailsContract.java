package com.cardee.owner_car_details;


import android.os.Bundle;

import com.cardee.domain.owner.entity.Car;
import com.cardee.mvp.BaseView;
import com.cardee.owner_home.OwnerCarListContract;

public interface OwnerCarDetailsContract {

    String CAR_ID = "car_id";
    String CAR_NUMBER = "car_number";

    interface View extends BaseView {

        void setCar(Car car);

        void moveToImages(Bundle args);

        void moveToSpecs(Bundle args);

        void moveToLocation(Bundle args);

        void moveToDescription(Bundle args);

        void onUnauthorized();

        void onConnectionLost();

        void onTitleSaved();
    }

    interface Presenter {

    }

    enum Action {
        EDIT_IMAGES, EDIT_SPECS, EDIT_LOCATION, EDIT_DESCRIPTION
    }

    class CarDetailEvent {
        Action mAction;

        public CarDetailEvent(Action action) {
            mAction = action;
        }

        public Action getAction() {
            return mAction;
        }
    }
}
