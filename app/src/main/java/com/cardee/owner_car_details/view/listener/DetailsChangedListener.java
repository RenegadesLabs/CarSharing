package com.cardee.owner_car_details.view.listener;

import com.cardee.owner_car_add.view.NewCarContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;

public interface DetailsChangedListener {

    void onModeDisplayed(NewCarContract.Mode mode);

    void onBind(SimpleBinder binder);

    void onNeedPermission(String... permissions);

    void onFinish(NewCarContract.Mode mode, NewCarContract.Action action);

    void showProgress(boolean show);

}
