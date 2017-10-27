package com.cardee.owner_home;


import com.cardee.domain.owner.entity.Car;

import java.util.List;

public interface OwnerCarListContract {

    interface View {

        void setItems(List<Car> cars);

        void updateItem(Car car);

        void removeItem(Car car);

        void openItem(Car car);
    }

    interface Presenter {

    }

    enum Action {
        OPEN, HOURLY_SWITCHED, DAILY_SWITCHED, UPDATED
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
