package com.cardee.domain.bookings.usecase;


import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class SendReviewAsRenter implements UseCase<SendReviewAsRenter.RequestValues, SendReviewAsRenter.ResponseValues> {

    private final BookingRepository repository;

    public SendReviewAsRenter() {
        this.repository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        repository.sendReviewAsRenter(values.getBookingId(), values.getCondition(), values.getComfort(),
                values.getOwner(), values.getOverall(), values.getReview(), new BookingDataSource.SimpleCallback() {
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
        private final String review;
        private final byte condition;
        private final byte comfort;
        private final byte owner;
        private final byte overall;
        private final int bookingId;

        public RequestValues(int bookingId, String review, byte condition, byte comfort, byte owner, byte overall) {
            this.bookingId = bookingId;
            this.review = review;
            this.condition = condition;
            this.comfort = comfort;
            this.owner = owner;
            this.overall = overall;
        }

        public String getReview() {
            return review;
        }

        public int getBookingId() {
            return bookingId;
        }

        public byte getCondition() {
            return condition;
        }

        public byte getComfort() {
            return comfort;
        }

        public byte getOwner() {
            return owner;
        }

        public byte getOverall() {
            return overall;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
