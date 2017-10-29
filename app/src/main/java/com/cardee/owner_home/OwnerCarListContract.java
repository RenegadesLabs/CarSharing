package com.cardee.owner_home;


import com.cardee.domain.owner.entity.Car;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface OwnerCarListContract {

    interface View extends BaseView {

        void setItems(List<Car> cars);

        void updateItem(Car car);

        void removeItem(Car car);

        void openItem(Car car);

        void onUnauthorized();

        void onConnectionLost();
    }

    interface Presenter {

    }

    enum Action {
        OPEN, HOURLY_SWITCHED, DAILY_SWITCHED, LOCATION_CLICKED, HOURLY_CLICKED, DAILY_CLICKED, UPDATED
    }

    class CarEvent {
        private Car mCar;
        private Action mAction;

        public CarEvent(Car car, Action action) {
            mCar = car;
            mAction = action;
        }

        public Action getAction() {
            return mAction;
        }

        public Car getCar() {
            return mCar;
        }
    }
}
