package com.cardee.domain.owner.usecase;

import com.cardee.data_source.AvailabilityDataSource;
import com.cardee.data_source.AvailabilitySettingsRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class SaveAvailability
        implements UseCase<SaveAvailability.RequestValues, SaveAvailability.ResponseValues> {

    private final AvailabilitySettingsRepository repository;

    public SaveAvailability() {
        repository = AvailabilitySettingsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        repository.saveAvailability(id, values.getIsAvailableDaily(), values.getIsAvailableHourly(),
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
        private final boolean isAvailableDaily;
        private final boolean isAvailableHourly;

        public RequestValues(int id, boolean availableDaily, boolean availableHourly) {
            this.id = id;
            this.isAvailableDaily = availableDaily;
            this.isAvailableHourly = availableHourly;
        }

        public int getId() {
            return id;
        }

        public boolean getIsAvailableDaily() {
            return isAvailableDaily;
        }

        public boolean getIsAvailableHourly() {
            return isAvailableHourly;
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
