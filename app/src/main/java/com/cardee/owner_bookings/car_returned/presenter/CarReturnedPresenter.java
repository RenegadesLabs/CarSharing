package com.cardee.owner_bookings.car_returned.presenter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ChangeBookingState;
import com.cardee.domain.bookings.usecase.GetBooking;
import com.cardee.domain.bookings.usecase.SendReviewAsOwner;
import com.cardee.domain.owner.entity.Image;
import com.cardee.mvp.BasePresenter;
import com.cardee.owner_bookings.car_returned.view.CarReturnedView;


public class CarReturnedPresenter implements BasePresenter {

    private CarReturnedView mView;
    private UseCaseExecutor mExecutor;
    private SendReviewAsOwner mSendReviewAsOwner;
    private ChangeBookingState mChangeBookingState;
    private GetBooking mGetBooking;
    private Resources mResources;

    public CarReturnedPresenter(CarReturnedView view) {
        mView = view;
        mResources = ((Context) view).getResources();
        mExecutor = UseCaseExecutor.getInstance();
        mSendReviewAsOwner = new SendReviewAsOwner();
        mGetBooking = new GetBooking();
        mChangeBookingState = new ChangeBookingState();
    }

    public void getData(int bookingId) {
        mExecutor.execute(mGetBooking, new GetBooking.RequestValues(bookingId),
                new UseCase.Callback<GetBooking.ResponseValues>() {
                    @Override
                    public void onSuccess(GetBooking.ResponseValues response) {
                        Booking booking = response.getBooking();
                        setCarTitle(booking.getCarTitle(), booking.getManufactureYear());
                        mView.setCarNumber(booking.getPlateNumber());
                        mView.setBookingDate(booking.getDateCreated());
                        setRentalPeriod(booking);
                        setCarPhoto(booking);
                        mView.setRenterPhoto(booking.getRenterPhoto());
                        mView.setRenterName(String.format("%s%s",
                                mResources.getString(R.string.car_returned_rate), booking.getRenterName()));
                        setCommentHint(booking.getRenterName());
                    }

                    private void setRentalPeriod(Booking booking) {
                        String start = booking.getTimeBegin();
                        String end = booking.getTimeEnd();
                        mView.setRentalPeriod(String.format(mResources.getString(
                                R.string.car_returned_period_template), start, end));
                    }

                    private void setCommentHint(String renterName) {
                        if (renterName == null) {
                            return;
                        }
                        String[] parts = renterName.split(" ");
                        String hint = String.format(mResources.getString(
                                R.string.car_returned_comment_hint), parts[0]);
                        mView.setCommentHint(hint);
                    }

                    private void setCarPhoto(Booking booking) {
                        Image[] images = booking.getImages();
                        for (Image image : images) {
                            if (image.isPrimary()) {
                                mView.setCarPhoto(image.getLink());
                                return;
                            }
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showMessage(error.getMessage());
                    }
                });
    }

    private void setCarTitle(String name, String year) {
        Spannable text = new SpannableString(name + "  " + year);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mView.setCarTitle(text);
    }

    @Override
    public void init() {
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void omSubmitClicked(String comment, byte rate, int bookingId) {
        mView.showProgress(true);
        mExecutor.execute(mChangeBookingState,
                new ChangeBookingState.RequestValues(bookingId, BookingState.COMPLETED),
                new UseCase.Callback<ChangeBookingState.ResponseValues>() {
                    @Override
                    public void onSuccess(ChangeBookingState.ResponseValues response) {
                        mExecutor.execute(mSendReviewAsOwner,
                                new SendReviewAsOwner.RequestValues(comment, rate, bookingId),
                                new UseCase.Callback<SendReviewAsOwner.ResponseValues>() {
                                    @Override
                                    public void onSuccess(SendReviewAsOwner.ResponseValues response) {
                                        mView.showProgress(false);
                                        mView.onSendCommentSuccess();
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        mView.showProgress(false);
                                        mView.showMessage(error.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }
}
