package com.cardee.renter_browse_cars.presenter;


import android.support.v4.app.FragmentActivity;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface RenterBrowseCarListContract {

    interface View extends BaseView {

        void setItems(List<OfferCar> cars);

        void updateItem(OfferCar car);

        void removeItem(OfferCar car);

        void openItem(OfferCar car);

        void onUnauthorized();

        void onConnectionLost();
    }

    interface Presenter {

        void showSort(FragmentActivity activity);

        void setSort(RenterBrowseCarListContract.Sort sort);

    }

    enum Action {
        OPEN, FAVORITE, UPDATED
    }

    public enum Sort {
        DISTANCE("distance", R.string.cars_browse_sort_distance),
        PRICE("price", R.string.cars_browse_sort_price),
        RATINGS("ratings", R.string.cars_browse_sort_ratings),
        POPULARITY("popularity", R.string.cars_browse_sort_popularity);

        public final String value;
        private int titleId;

        Sort(String value, int titleId) {
            this.value = value;
            this.titleId = titleId;
        }

        public int getTitleId() {
            return titleId;
        }
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
