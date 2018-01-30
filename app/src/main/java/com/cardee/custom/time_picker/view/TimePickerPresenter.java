package com.cardee.custom.time_picker.view;


import android.widget.Toast;

import com.cardee.custom.time_picker.domain.UseCase;
import com.cardee.custom.time_picker.domain.calendar.GenerateTimeModel;
import com.cardee.custom.time_picker.domain.executor.UseCaseExecutor;
import com.cardee.custom.time_picker.model.Error;

class TimePickerPresenter {

    private TimePicker view;
    private UseCaseExecutor executor;
    private GenerateTimeModel timeModel;

    TimePickerPresenter(TimePicker view) {
        this.view = view;
        executor = new UseCaseExecutor();
        timeModel = new GenerateTimeModel();
    }

    public void retrieveDays() {
        retrieveDays(new GenerateTimeModel.RequestValues());
    }

    public void retrieveDays(int count) {
        retrieveDays(new GenerateTimeModel.RequestValues(count));
    }

    private void retrieveDays(GenerateTimeModel.RequestValues values) {
        executor.execute(timeModel, values, new UseCase.Callback<GenerateTimeModel.ResponseValues>() {
            @Override
            public void onSuccess(GenerateTimeModel.ResponseValues response) {
                view.addMonths(response.getDays());
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
