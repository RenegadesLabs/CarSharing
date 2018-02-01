package com.cardee.renter_browse_cars;


import android.support.v4.app.FragmentActivity;

import com.cardee.R;
import com.cardee.domain.renter.entity.BrowseCarsFilter;
import com.cardee.custom.modal.SortRenterOffersDialog;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface RenterBrowseCarListContract {

    interface View extends BaseView {

        void setItems(List<OfferCar> cars);

        void setSortValue(String value);

        void updateItem(OfferCar car);

        void removeItem(OfferCar car);

        void openItem(OfferCar car);

        void showSearchProgress(boolean show);

        void onUnauthorized();

        void onConnectionLost();

        void checkLocationPermission();
    }

    interface Presenter {

        void showSort(FragmentActivity activity);

        void showType(FragmentActivity activity);

        void setSort(Sort sort);

        void sortCars(String sortBy);

        void setType(VehicleType type);

        void addCarToFavorites(int carId);

        void searchCars(String criteria);

        void getCarsByFilter(BrowseCarsFilter filter);

        BrowseCarsFilter getFilter();

        void saveFilter(BrowseCarsFilter filter);

        void continueSetSort(Sort sort);

        void onDestroy();
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

    enum VehicleType {
        PERSONAL(0, R.string.cars_browse_vehicle_type_personal),
        PRIVATE(1, R.string.cars_browse_vehicle_type_private),
        COMMERCIAL(2, R.string.cars_browse_vehicle_type_commercial);

        public final int typeId;
        private int titleId;

        VehicleType(int typeId, int titleId) {
            this.typeId = typeId;
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
