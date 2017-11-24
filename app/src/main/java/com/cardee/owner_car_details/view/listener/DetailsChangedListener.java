package com.cardee.owner_car_details.view.listener;

import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;

public interface DetailsChangedListener {

    void onModeDisplayed(NewCarFormsContract.Mode mode);

    void onBind(SimpleBinder binder);

    void onNeedPermission(String... permissions);

    void onFinish(NewCarFormsContract.Mode mode, NewCarFormsContract.Action action);

    void showProgress(boolean show);

}
