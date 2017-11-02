package com.cardee.owner_car_details;


import com.cardee.domain.owner.entity.Car;
import com.cardee.mvp.BaseView;

public interface OwnerCarDetailsContract {

    String CAR_ID = "car_id";
    String CAR_NUMBER = "car_number";

    interface View extends BaseView {

        void setCar(Car car);

    }

    interface Presenter {

    }


}
