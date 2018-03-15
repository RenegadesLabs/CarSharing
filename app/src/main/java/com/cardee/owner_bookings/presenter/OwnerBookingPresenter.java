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
import com.cardee.owner_bookings.car_checklist.view.ChecklistActivity;
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
import com.cardee.renter_home.view.RenterHomeActivity;

import static com.cardee.domain.bookings.BookingState.COLLECTING;


public class OwnerBookingPresenter implements OwnerBookingContract.Presenter {

    private OwnerBookingContract.View view;
    private OwnerBookingContract.ParentView parentView;
    private BookingView bookingView;
    private PresentationStrategy strategy;
    private final int bookingId;
    private final UseCaseExecutor executor;
    private final GetBooking getBooking;
    private final ChangeBookingState changeBookingState;
    private boolean isRenter = false;

    public OwnerBookingPresenter(int bookingId, boolean isRenter) {
        this.bookingId = bookingId;
        executor = UseCaseExecutor.getInstance();
        getBooking = new GetBooking();
        changeBookingState = new ChangeBookingState();
        this.isRenter = isRenter;
    }

    @Override
    public void setView(OwnerBookingContract.View view) {
        this.view = view;
        if (view instanceof BookingView) {
            this.bookingView = (BookingView) view;
        }
    }

    @Override
    public void setParentView(OwnerBookingContract.ParentView parentView) {
        this.parentView = parentView;
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

                    if (isRenter && booking.getBookingStateType().equals(COLLECTING)) {
                        parentView.showRenterCheckList();
                        return;
                    }

                    changeStrategyFrom(booking.getBookingStateType());
                    if (BookingState.COMPLETED.equals(strategy.getType())) {
                        requestAdditionalState();
                    }
                    view.bind(booking, isRenter);
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
                    strategy = new NewBookingStrategy(bookingView, this, isRenter);
                    break;
                case CONFIRMED:
                    strategy = new ConfirmedStrategy(bookingView, this, isRenter);
                    break;
                case COLLECTING:
                    strategy = new HandingOverStrategy(bookingView, this, isRenter);
                    break;
                case COLLECTED:
                    strategy = new HandedOverStrategy(bookingView, this, isRenter);
                    break;
                case CANCELED:
                    strategy = new CanceledStrategy(bookingView, this);
                    break;
                case COMPLETED:
                    strategy = new CompletedStrategy(bookingView, this, isRenter);
            }
        }
    }

    private void requestAdditionalState() {

    }

    @Override
    public void onDestroy() {
        view = null;
        bookingView = null;
        parentView = null;
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
        Intent i = new Intent(bookingView.getContext(), ChecklistActivity.class);
        i.putExtra(ChecklistActivity.KEY_BOOKING_ID, bookingId);
        bookingView.getContext().startActivity(i);
    }

    @Override
    public void onCancelHandOver() {
        changeState(BookingState.CANCELED);
    }

    @Override
    public void onCompleted() {
        if (isRenter) {
            if (parentView != null) {
                parentView.showExtendBookingDialog();
            }
            return;
        }
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
                if (isRenter) {
                    Intent homeIntent = new Intent(bookingView.getContext(), RenterHomeActivity.class);
                    bookingView.getContext().startActivity(homeIntent);
                    return;
                }
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
