package com.cardee.domain.bookings.usecase;

import android.net.Uri;

import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;


public class AddImageToChecklist implements UseCase<AddImageToChecklist.RequestValues, AddImageToChecklist.ResponseValues> {

    private final BookingRepository mRepository;

    public AddImageToChecklist() {
        mRepository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.uploadImage(values.getBookingId(), values.getImageUri(),
                new BookingDataSource.ImageCallback() {
                    @Override
                    public void onSuccess(int imageId) {
                        callback.onSuccess(new ResponseValues(imageId));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int bookingId;
        private final Uri imageUri;

        public RequestValues(int bookingId, Uri imageUri) {
            this.bookingId = bookingId;
            this.imageUri = imageUri;
        }

        public int getBookingId() {
            return bookingId;
        }

        public Uri getImageUri() {
            return imageUri;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final Integer imageId;

        public ResponseValues(Integer imageId) {
            this.imageId = imageId;
        }

        public Integer getImageId() {
            return imageId;
        }
    }
}
