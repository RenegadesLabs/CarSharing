package com.cardee.owner_bookings.car_returned.presenter;


import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.usecase.SendReviewAsOwner;
import com.cardee.mvp.BasePresenter;
import com.cardee.owner_bookings.car_returned.view.CarReturnedView;


public class CarReturnedPresenter implements BasePresenter {

    private CarReturnedView mView;
    private UseCaseExecutor mExecutor;
    private SendReviewAsOwner mSendReviewAsOwner;

    public CarReturnedPresenter(CarReturnedView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mSendReviewAsOwner = new SendReviewAsOwner();
    }

    public void setCarTitle(String name, String year) {
        Spannable text = new SpannableString(name + "  " + year);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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
        mExecutor.execute(mSendReviewAsOwner, new SendReviewAsOwner.RequestValues(comment, rate, bookingId), new UseCase.Callback<SendReviewAsOwner.ResponseValues>() {
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
}
