package com.cardee.custom.calendar.view;


import android.widget.Toast;

import com.cardee.custom.calendar.domain.UseCase;
import com.cardee.custom.calendar.domain.calendar.GenerateCalendarModel;
import com.cardee.custom.calendar.domain.executor.UseCaseExecutor;
import com.cardee.custom.calendar.model.Error;

class CalendarPresenter {

    private CalendarView view;
    private UseCaseExecutor executor;
    private GenerateCalendarModel calendarModel;

    CalendarPresenter(CalendarView view) {
        this.view = view;
        executor = new UseCaseExecutor();
        calendarModel = new GenerateCalendarModel();
    }

    public void retrieveMonthList() {
        retrieveMonthList(new GenerateCalendarModel.RequestValues());
    }

    public void retrieveMonthList(boolean selectCurrent) {
        GenerateCalendarModel.RequestValues request = new GenerateCalendarModel.RequestValues(selectCurrent);
        retrieveMonthList(request);
    }

    public void retrieveMonthList(int count) {
        retrieveMonthList(new GenerateCalendarModel.RequestValues(count));
    }

    private void retrieveMonthList(GenerateCalendarModel.RequestValues values) {
        executor.execute(calendarModel, values, new UseCase.Callback<GenerateCalendarModel.ResponseValues>() {
            @Override
            public void onSuccess(GenerateCalendarModel.ResponseValues response) {
                view.addMonths(response.getMonths());
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
