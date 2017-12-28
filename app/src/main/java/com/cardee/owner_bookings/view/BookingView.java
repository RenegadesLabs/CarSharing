package com.cardee.owner_bookings.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
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
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


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
