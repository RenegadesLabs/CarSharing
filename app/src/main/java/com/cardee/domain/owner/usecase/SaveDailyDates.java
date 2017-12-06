package com.cardee.domain.owner.usecase;


import com.cardee.data_source.AvailabilityDataSource;
import com.cardee.data_source.AvailabilitySettingsRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.mapper.DateListToArrayMapper;

import java.util.Date;
import java.util.List;

public class SaveDailyDates implements UseCase<SaveDailyDates.RequestValues, SaveDailyDates.ResponseValues> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final AvailabilitySettingsRepository repository;
    private final DateListToArrayMapper mapper;

    public SaveDailyDates() {
        repository = AvailabilitySettingsRepository.getInstance();
        mapper = new DateListToArrayMapper(DATE_PATTERN);
    }


    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        repository.saveDailyAvailability(values.getId(), mapper.transform(values.getDates()),
                new AvailabilityDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int id;
        private final List<Date> dates;

        public RequestValues(int id, List<Date> dates) {
            this.id = id;
            this.dates = dates;
        }

        public int getId() {
            return id;
        }

        public List<Date> getDates() {
            return dates;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean successful;

        public ResponseValues(boolean successful) {
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }
}
