package com.cardee.owner_car_details.view.listener;

import com.cardee.owner_car_details.view.binder.SimpleBinder;

public interface DetailsChangedListener {

    void onBind(SimpleBinder binder);

    void onNeedPermission(String... permissions);

    void showProgress(boolean show);

}
