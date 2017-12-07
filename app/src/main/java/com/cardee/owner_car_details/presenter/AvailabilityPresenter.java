package com.cardee.owner_car_details.presenter;

import android.os.Bundle;
import android.util.Log;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetAvailabilityDates;
import com.cardee.domain.owner.usecase.SaveDailyDates;
import com.cardee.domain.owner.usecase.SaveHourlyDates;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.view.config.AvailabilityConfig;

import java.util.Date;
import java.util.List;


public class AvailabilityPresenter implements AvailabilityContract.Presenter {

    private static final String TAG = AvailabilityPresenter.class.getSimpleName();

    private AvailabilityContract.View view;
    private final int id;
    private final GetAvailabilityDates getAvailabilityDates;
    private final SaveDailyDates saveDaily;
    private final SaveHourlyDates saveHourly;
    private final UseCaseExecutor executor;
    private final AvailabilityContract.Mode mode;
    private String startTime;
    private String endTime;

    private AvailabilityConfig config;

    public AvailabilityPresenter(AvailabilityContract.View view, Bundle args, AvailabilityConfig config) {
        this.view = view;
        id = args.containsKey(AvailabilityContract.CAR_ID) ? args.getInt(AvailabilityContract.CAR_ID) : -1;
        mode = args.containsKey(AvailabilityContract.CALENDAR_MODE) ?
                (AvailabilityContract.Mode) args.getSerializable(AvailabilityContract.CALENDAR_MODE) : null;
        this.config = config;
        getAvailabilityDates = new GetAvailabilityDates();
        saveDaily = new SaveDailyDates();
        saveHourly = new SaveHourlyDates();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        executor.execute(getAvailabilityDates,
                new GetAvailabilityDates.RequestValues(id),
                new UseCase.Callback<GetAvailabilityDates.ResponseValues>() {
                    @Override
                    public void onSuccess(GetAvailabilityDates.ResponseValues response) {
                        if (view == null) {
                            return;
                        }
                        if (mode == AvailabilityContract.Mode.DAILY) {
                            view.onDatesRetrieved(response.getDailyDates());
                        } else if (mode == AvailabilityContract.Mode.HOURLY) {
                            view.onDatesRetrieved(response.getHourlyDates());
                            view.onTimeBoundsRetrieved(response.getBeginTime(), response.getEndTime());
                            startTime = response.getBeginTime();
                            endTime = response.getEndTime();
                        } else {
                            throw new IllegalStateException("Invalid mode: " + mode);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e(TAG, error.getMessage());
                    }
                });
    }

    @Override
    public void saveData(List<Date> data) {
        if (mode == AvailabilityContract.Mode.DAILY) {
            saveDaily(data);
        } else if (mode == AvailabilityContract.Mode.HOURLY) {
            saveHourly(data);
        } else {
            throw new IllegalStateException("Illegal mode: " + mode);
        }
    }

    private void saveDaily(final List<Date> data) {
        view.showProgress(true);
        SaveDailyDates.RequestValues request = new SaveDailyDates.RequestValues(id, data);
        executor.execute(saveDaily, request, new UseCase.Callback<SaveDailyDates.ResponseValues>() {
            @Override
            public void onSuccess(SaveDailyDates.ResponseValues response) {
                if (mode == AvailabilityContract.Mode.DAILY && config.isSingleDatesSet()) {
                    saveHourly(data);
                    return;
                }
                close();
            }

            @Override
            public void onError(Error error) {
                hideProgressWithError(error.getMessage());
            }
        });
    }

    private void saveHourly(final List<Date> data) {
        view.showProgress(true);
        SaveHourlyDates.RequestValues request = new SaveHourlyDates.RequestValues(id, startTime, endTime, data);
        executor.execute(saveHourly, request, new UseCase.Callback<SaveHourlyDates.ResponseValues>() {
            @Override
            public void onSuccess(SaveHourlyDates.ResponseValues response) {
                if (mode == AvailabilityContract.Mode.HOURLY && config.isSingleDatesSet()) {
                    saveDaily(data);
                    return;
                }
                close();
            }

            @Override
            public void onError(Error error) {
                hideProgressWithError(error.getMessage());
            }
        });

    }

    private void close() {
        if (view != null) {
            view.showProgress(false);
            view.onSaved();
        }
    }

    private void hideProgressWithError(String message) {
        if (view != null) {
            view.showProgress(false);
            view.showMessage(message);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        AvailabilityConfig.relese();
    }
}
