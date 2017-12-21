package com.cardee.owner_car_details;


import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface RentalDetailsContract {

    interface View extends BaseView {

        void setData(RentalDetails rentalDetails);

        void onDailyChange(boolean available);

        void onHourlyChange(boolean available);

    }

    interface ControlView extends BaseView {

        void setData(RentalDetails rentalDetails);

    }

    interface Presenter extends BasePresenter {


    }
}
