package com.cardee.renter_browse_cars.presenter;


import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface RenterBrowseCarListContract {

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
        OPEN, ADD_TO_FAVORITE, UPDATED
    }

    class CarEvent {
        private final OfferCar mCar;
        private final Action mAction;

        public CarEvent(OfferCar car, Action action) {
            mCar = car;
            mAction = action;
        }

        public Action getAction() {
            return mAction;
        }

        public OfferCar getCar() {
            return mCar;
        }
    }
}
