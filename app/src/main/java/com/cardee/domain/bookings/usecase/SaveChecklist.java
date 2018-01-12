package com.cardee.domain.bookings.usecase;

import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;


public class SaveChecklist implements UseCase<SaveChecklist.RequestValues, SaveChecklist.ResponseValues> {

    private final BookingRepository mRepository;

    public SaveChecklist() {
        mRepository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.saveChecklist(values.getBookingId(), values.getRemarks(), values.getTank(),
                values.getMasterMileage(), values.getImageIds(),
                new BookingDataSource.SimpleCallback() {
                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }

                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new ResponseValues(true));
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int bookingId;
        private final String remarks;
        private final float tank;
        private final int masterMileage;
        private final int[] imageIds;

        public RequestValues(int bookingId, String remarks, float tank, int masterMileage, int[] imageIds) {
            this.bookingId = bookingId;
            this.remarks = remarks;
            this.tank = tank;
            this.masterMileage = masterMileage;
            this.imageIds = imageIds;
        }

        public int getBookingId() {
            return bookingId;
        }

        public String getRemarks() {
            return remarks;
        }

        public float getTank() {
            return tank;
        }

        public int getMasterMileage() {
            return masterMileage;
        }

        public int[] getImageIds() {
            return imageIds;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean success;

        public ResponseValues(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
