package com.cardee.renter_browse_cars.view.listener;

import com.cardee.domain.owner.entity.Car;

public interface RenterCarListItemEventListener {

    void onCarItemClick(Car car);

    void onAddToFavClick();

    void onStartLoading();

    void onStopLoading();
}
