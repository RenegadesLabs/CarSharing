package com.cardee.domain.bookings.usecase;

import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.BookingState;

public class ChangeBookingState
        implements UseCase<ChangeBookingState.RequestValues, ChangeBookingState.ResponseValues> {

    private final BookingRepository repository;

    public ChangeBookingState() {
        repository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        int bookingId = values.getBookingId();
        String state = values.getTargetState().getRequest();
        if (bookingId == -1 || state == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST,
                    "Invalid booking id: " + bookingId + " or state: " + state));
            return;
        }
        repository.changeBookingState(bookingId, state, new BookingDataSource.SimpleCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues(true, values.getTargetState()));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int bookingId;
        private final BookingState targetState;

        public RequestValues(int bookingId, BookingState targetState) {
            this.bookingId = bookingId;
            this.targetState = targetState;
        }

        public int getBookingId() {
            return bookingId;
        }

        public BookingState getTargetState() {
            return targetState;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean successful;
        private final BookingState newState;

        public ResponseValues(boolean successful, BookingState newState) {
            this.successful = successful;
            this.newState = newState;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public BookingState getNewState() {
            return newState;
        }
    }
}
