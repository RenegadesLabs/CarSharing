package com.cardee.renter_bookings.rate_rental_exp.presenter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.GetBooking;
import com.cardee.domain.bookings.usecase.SendReviewAsRenter;
import com.cardee.domain.owner.entity.Image;
import com.cardee.mvp.BasePresenter;
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpView;
import com.cardee.renter_bookings.rate_rental_exp.view.SecondRateFragment;

public class RateRentalExpPresenter implements BasePresenter {

    private RateRentalExpView mView;
    private SendReviewAsRenter mSendReviewAsRenter;
    private GetBooking mGetBooking;
    private UseCaseExecutor mExecutor;
    private Resources mResources;

    private int mBookingId;
    private byte conditionRate;
    private byte comfortRate;
    private byte ownerRate;
    private byte overallRate;
    private int mProfileId;

    public RateRentalExpPresenter(RateRentalExpView view, int bookingId) {
        mView = view;
        mResources = ((Context) mView).getResources();
        mExecutor = UseCaseExecutor.getInstance();
        mSendReviewAsRenter = new SendReviewAsRenter();
        mGetBooking = new GetBooking();
        mBookingId = bookingId;
    }

    @Override
    public void init() {
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void getBookingData() {
        if (mBookingId != -1) {
            mExecutor.execute(mGetBooking, new GetBooking.RequestValues(mBookingId),
                    new UseCase.Callback<GetBooking.ResponseValues>() {
                        @Override
                        public void onSuccess(GetBooking.ResponseValues response) {
                            Booking booking = response.getBooking();
                            setCarTitle(booking.getCarTitle(), booking.getPlateNumber());
                            setRentalPeriod(booking);
                            setCarPhoto(booking);
                            mView.setOwnerPhoto(booking.getOwnerPhoto());
                            mProfileId = booking.getOwnerId();
                        }

                        @Override
                        public void onError(Error error) {
                            mView.showMessage(error.getMessage());
                        }
                    });
        }
    }

    private void setCarTitle(String carTitle, String plateNumber) {
        Spannable text = new SpannableString(carTitle + "  " + plateNumber);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, carTitle.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mView.setCarTitle(text);
    }

    private void setRentalPeriod(Booking booking) {
        String start = booking.getTimeBegin();
        String end = booking.getTimeEnd();
        mView.setRentalPeriod(String.format(mResources.getString(
                R.string.car_returned_period_template), start, end));
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

    public void setRates(byte condition, byte comfort, byte owner, byte overall) {
        conditionRate = condition;
        comfortRate = comfort;
        ownerRate = owner;
        overallRate = overall;
    }

    public void sendRateAndReview(String review) {
        review = review.trim();
        mExecutor.execute(mSendReviewAsRenter,
                new SendReviewAsRenter.RequestValues(mBookingId, review, conditionRate, comfortRate, ownerRate, overallRate),
                new UseCase.Callback<SendReviewAsRenter.ResponseValues>() {
                    @Override
                    public void onSuccess(SendReviewAsRenter.ResponseValues response) {
                        mView.onSendRateSuccess();
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showMessage(error.getMessage());
                    }
                });
    }

    public Fragment getSecondFragment(SecondRateFragment.OnSecondFragmentClickedListener listener) {
        return SecondRateFragment.newInstance(listener, overallRate);
    }

    public int getProfileId() {
        return mProfileId;
    }
}
