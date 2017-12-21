package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;


public class UpdateHourlyInstantBookingState implements UseCase<UpdateHourlyInstantBookingState.RequestValues,
        UpdateHourlyInstantBookingState.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateHourlyInstantBookingState() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.updateInstantBookingHourly(values.getId(), values.isInstantBooking(),
                new CarEditDataSource.Callback() {
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
        private final int mId;
        private final boolean isInstantBooking;

        public RequestValues(int id, boolean isInstantBooking) {
            mId = id;
            this.isInstantBooking = isInstantBooking;
        }

        public int getId() {
            return mId;
        }

        public boolean isInstantBooking() {
            return isInstantBooking;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean isSuccess;

        public ResponseValues(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
