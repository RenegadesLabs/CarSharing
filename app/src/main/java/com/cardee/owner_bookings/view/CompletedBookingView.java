package com.cardee.owner_bookings.view;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.custom.CustomRatingBar;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.Rate;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.util.glide.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CompletedBookingView extends CoordinatorLayout implements OwnerBookingContract.View {
    private OwnerBookingContract.Presenter presenter;
    private Toast currentToast;
    private Unbinder unbinder;
    private RequestManager imageManager;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    public TextView toolbarTitle;
    @BindView(R.id.booking_status)
    public TextView bookingStatus;
    @BindView(R.id.booking_payment)
    public TextView bookingPayment;
    @BindView(R.id.booking_car_picture)
    public ImageView carPhotoView;
    @BindView(R.id.booking_progress)
    public ProgressBar imgProgress;
    @BindView(R.id.booking_car_title)
    public TextView carTitle;
    @BindView(R.id.booking_car_year)
    public TextView carYear;
    @BindView(R.id.booking_car_plate_number)
    public TextView carPlateNumber;
    @BindView(R.id.booking_date_created)
    public TextView bookingCreated;
    @BindView(R.id.rental_period_title)
    public TextView rentalPeriodTitle;
    @BindView(R.id.rental_period)
    public TextView rentalPeriod;
    @BindView(R.id.booking_loading_indicator)
    public View progressView;
    @BindView(R.id.renter_photo)
    public ImageView renterPhoto;
    @BindView(R.id.rating_edit)
    public TextView ratingEdit;
    @BindView(R.id.rating_bar)
    public CustomRatingBar ratingBar;

    public CompletedBookingView(Context context) {
        super(context);
    }

    public CompletedBookingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompletedBookingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this, this);
        imageManager = Glide.with(getContext());
    }

    @Override
    public void showProgress(boolean show) {
        progressView.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        currentToast.show();
        ratingEdit.setOnClickListener(view -> {

        });
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getContext().getString(messageId));
    }

    @Override
    public void setPresenter(OwnerBookingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void bind(Booking booking, boolean isRenter) {
        toolbarTitle.setText(booking.getBookingNum());
        loadImageIntoView(booking.getPrimaryImage().getLink(),
                R.drawable.img_no_car, carPhotoView, imgProgress, false);
        bookingPayment.setBackgroundColor(ContextCompat.getColor(getContext(), booking.getPaymentType().getBgId()));
        bookingPayment.setText(booking.getPaymentType().getTitleId());
        carTitle.setText(booking.getCarTitle());
        carYear.setText(booking.getManufactureYear());
        carPlateNumber.setText(booking.getPlateNumber());
        String timeBegin = booking.getTimeBegin();
        String timeEnd = booking.getTimeEnd();
        String timePeriod = null;
        bookingCreated.setText(booking.getDateCreated());
        if (timeBegin != null && timeEnd != null) {
            timePeriod = timeBegin + " to " + timeEnd;
        }
        rentalPeriod.setText(timePeriod);
        loadImageIntoView(booking.getRenterPhoto(),
                R.drawable.placeholder_user_img, renterPhoto, null, true);
        Integer rating = booking.getRenterRating();
//        Integer rating = null;
//        Rate[] rates = booking.getOwnerRates();
//        for (Rate rate : rates) {
//            if (rate.getRateName().equals("overall_rental_experience")) {
//                rating = rate.getRating();
//            }
//        }
        if (rating != null) {
            ratingBar.setScore(rating);
        }
        String amountString = booking.getTotalAmount() == null ? "$0" : "$" + booking.getTotalAmount();
    }

    @Override
    public void bind() {

    }

    private void loadImageIntoView(String imgUrl, int placeholderRes, ImageView view, ProgressBar progress, boolean circle) {
        imgUrl = imgUrl == null ? "" : imgUrl;
        if (progress != null) {
            progress.setVisibility(VISIBLE);
        }
        DrawableRequestBuilder<String> builder = imageManager
                .load(imgUrl).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        if (progress != null) {
                            progress.setVisibility(GONE);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (progress != null) {
                            progress.setVisibility(GONE);
                        }
                        return false;
                    }
                }).error(placeholderRes);
        if (circle) {
            builder.transform(new CircleTransform(getContext()));
        }
        builder.into(view);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        presenter = null;
    }
}
