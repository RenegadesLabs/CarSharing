package com.cardee.owner_bookings.presenter;

import android.content.Intent;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ChangeBookingState;
import com.cardee.domain.bookings.usecase.GetBooking;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_returned.view.CarReturnedActivity;
import com.cardee.owner_bookings.strategy.CanceledStrategy;
import com.cardee.owner_bookings.strategy.CompletedStrategy;
import com.cardee.owner_bookings.strategy.ConfirmedStrategy;
import com.cardee.owner_bookings.strategy.HandedOverStrategy;
import com.cardee.owner_bookings.strategy.HandingOverStrategy;
import com.cardee.owner_bookings.strategy.NewBookingStrategy;
import com.cardee.owner_bookings.strategy.PresentationStrategy;
import com.cardee.owner_bookings.view.BookingView;
import com.cardee.owner_home.view.OwnerHomeActivity;


public class OwnerBookingPresenter implements OwnerBookingContract.Presenter {

    private OwnerBookingContract.View view;
    private BookingView bookingView;
    private PresentationStrategy strategy;
    private final int bookingId;
    private final UseCaseExecutor executor;
    private final GetBooking getBooking;
    private final ChangeBookingState changeBookingState;

    public OwnerBookingPresenter(int bookingId) {
        this.bookingId = bookingId;
        executor = UseCaseExecutor.getInstance();
        getBooking = new GetBooking();
        changeBookingState = new ChangeBookingState();
    }

    @Override
    public void setView(OwnerBookingContract.View view) {
        this.view = view;
        if (view instanceof BookingView) {
            this.bookingView = (BookingView) view;
        }
    }

    @Override
    public void setStrategy(PresentationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void init() {
        view.showProgress(true);
        GetBooking.RequestValues request = new GetBooking.RequestValues(bookingId);
        executor.execute(getBooking, request, new UseCase.Callback<GetBooking.ResponseValues>() {
            @Override
            public void onSuccess(GetBooking.ResponseValues response) {
                if (view != null) {
                    view.showProgress(false);
                    Booking booking = response.getBooking();
                    changeStrategyFrom(booking.getBookingStateType());
                    view.bind(booking);
                }
            }

            @Override
            public void onError(Error error) {
                if (view != null) {
                    view.showProgress(false);
                    view.showMessage(error.getMessage());
                }
            }
        });
    }

    private void changeStrategyFrom(BookingState state) {
        if (strategy == null || !strategy.getType().equals(state)) {
            if (strategy != null) {
                strategy.finish();
            }
            switch (state) {
                case NEW:
                    strategy = new NewBookingStrategy(bookingView, this);
                    break;
                case CONFIRMED:
                    strategy = new ConfirmedStrategy(bookingView, this);
                    break;
                case COLLECTING:
                    strategy = new HandingOverStrategy(bookingView, this);
                    break;
                case COLLECTED:
                    strategy = new HandedOverStrategy(bookingView, this);
                    break;
                case CANCELED:
                    strategy = new CanceledStrategy(bookingView, this);
                    break;
                case COMPLETED:
                    strategy = new CompletedStrategy(bookingView, this);
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        bookingView = null;
    }

    @Override
    public void onDecline() {
        changeState(BookingState.CANCELED);
    }

    @Override
    public void onAccept() {
        changeState(BookingState.CONFIRMED);
    }

    @Override
    public void onCancel() {
        changeState(BookingState.CANCELED);
    }

    @Override
    public void onHandOver() {

    }

    @Override
    public void onCancelHandOver() {
        changeState(BookingState.CONFIRMED);
    }

    @Override
    public void onCompleted() {
        Intent completeIntent = new Intent(bookingView.getContext(), CarReturnedActivity.class);
        completeIntent.putExtra("booking_id", bookingId);
        bookingView.getContext().startActivity(completeIntent);
    }

    private void changeState(BookingState targetState) {
        view.showProgress(true);
        ChangeBookingState.RequestValues request =
                new ChangeBookingState.RequestValues(bookingId, targetState);
        executor.execute(changeBookingState, request, new UseCase.Callback<ChangeBookingState.ResponseValues>() {
            @Override
            public void onSuccess(ChangeBookingState.ResponseValues response) {
                if (view != null && response.isSuccessful()) {
                    view.showProgress(false);
                    handleNewState(response.getNewState());
                }
            }

            @Override
            public void onError(Error error) {
                if (view != null) {
                    view.showProgress(false);
                    view.showMessage(error.getMessage());
                }
            }
        });
    }

    private void handleNewState(BookingState newState) {
        switch (newState) {
            case CONFIRMED:
                changeStrategyFrom(newState);
                break;
            case COMPLETED:
            case CANCELED:
                Intent homeIntent = new Intent(bookingView.getContext(), OwnerHomeActivity.class);
                bookingView.getContext().startActivity(homeIntent);
        }
    }

    @Override
    public void onShowProfile() {

    }

    @Override
    public void onCall() {

    }

    @Override
    public void onChat() {

    }

    @Override
    public void onRatingEdit() {
    }
}
