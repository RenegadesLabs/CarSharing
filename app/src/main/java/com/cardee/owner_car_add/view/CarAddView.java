package com.cardee.owner_car_add.view;

import com.cardee.mvp.BaseView;

public interface CarAddView extends BaseView {
    void onVehicleType();

    void onCarInfo();

    void onCarImage();

    void onCarLocation();

    void onContactInfo();

    void onPaymentAccount();

    void onUploadPicture();

    void onSubmit();
}
