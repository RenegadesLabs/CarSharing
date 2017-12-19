package com.cardee.owner_car_add.view;

import com.cardee.mvp.BaseView;

public interface CarDescriptionView extends BaseView {

    void onDescriptionObtained(String description);

    void onDescriptionSaved();

}
