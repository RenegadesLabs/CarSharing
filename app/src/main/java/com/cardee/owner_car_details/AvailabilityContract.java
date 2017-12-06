package com.cardee.owner_car_details;

import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

import java.util.Date;
import java.util.List;

public interface AvailabilityContract {

    enum Mode {
        DAILY, HOURLY
    }

    String CAR_ID = "_car_id";
    String CALENDAR_MODE = "_calendar_mode";

    interface View extends BaseView {

        void onDatesRetrieved(List<Date> data);

        void onTimeBoundsRetrieved(String startTime, String endTime);

        void onSaved();
    }

    interface Presenter extends BasePresenter {

        void saveData(List<Date> data);
    }
}
