package com.cardee.domain.bookings.usecase;


import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class SendReviewAsOwner implements UseCase<SendReviewAsOwner.RequestValues, SendReviewAsOwner.ResponseValues> {

    private final BookingRepository repository;

    public SendReviewAsOwner() {
        this.repository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        repository.sendReviewAsOwner(values.getBookingId(), values.getRate(), values.getComment(), new BookingDataSource.SimpleCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues());
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final String comment;
        private final byte rate;
        private final int bookingId;

        public RequestValues(String comment, byte rate, int bookingId) {
            this.comment = comment;
            this.rate = rate;
            this.bookingId = bookingId;
        }

        public String getComment() {
            return comment;
        }

        public byte getRate() {
            return rate;
        }

        public int getBookingId() {
            return bookingId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
