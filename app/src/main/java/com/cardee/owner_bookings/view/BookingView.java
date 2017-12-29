package com.cardee.owner_bookings.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_bookings.OwnerBookingContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingView extends CoordinatorLayout implements OwnerBookingContract.View {

    private OwnerBookingContract.Presenter presenter;
    private Unbinder unbinder;

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
    @BindView(R.id.deliver_to_title)
    public TextView deliverToTitle;
    @BindView(R.id.deliver_to)
    public TextView deliverTo;
    @BindView(R.id.handover_on_title)
    public TextView handoverOnTitle;
    @BindView(R.id.handover_on)
    public TextView handoverOn;
    @BindView(R.id.return_by_title)
    public TextView returnByTitle;
    @BindView(R.id.return_by)
    public TextView returnBy;
    @BindView(R.id.handover_at_title)
    public TextView handoverAtTitle;
    @BindView(R.id.handover_at)
    public TextView handoverAt;
    @BindView(R.id.total_cost_title)
    public TextView totalCostTitle;
    @BindView(R.id.total_cost)
    public TextView totalCost;
    @BindView(R.id.renter_photo)
    public ImageView renterPhoto;
    @BindView(R.id.renter_name)
    public TextView renterName;
    @BindView(R.id.renter_message)
    public TextView renterMessage;
    @BindView(R.id.btn_decline_request)
    public TextView btnCancel;
    @BindView(R.id.btn_accept_request)
    public TextView btnAccept;
    @BindView(R.id.booking_accept_message)
    public TextView acceptMessage;
    @BindView(R.id.booking_cancel_message)
    public TextView cancelMessage;

    public BookingView(Context context) {
        super(context);
    }

    public BookingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this, this);
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void setPresenter(OwnerBookingContract.Presenter presenter) {
        this.presenter = presenter;
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
