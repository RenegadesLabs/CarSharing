package com.cardee.owner_home.view.listener;

import com.cardee.domain.owner.entity.Car;

public interface CarListItemEventListener {

    void onCarItemClick(Car car);

    void onHourlyPickerClick(Car car);

    void onDailyPickerClick(Car car);

    void onLocationPickerClick(Car car);

    void onStartLoading();

    void onStopLoading();
}
