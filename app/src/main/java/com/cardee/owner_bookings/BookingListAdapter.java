package com.cardee.owner_bookings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.owner_bookings.car_returned.view.CarReturnedActivity;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.BookingItemHolder> {

    private static final int DEFAULT_STATE = R.string.booking_state_completed;
    private static final int DEFAULT_STATE_COLOR = R.color.booking_state_completed;

    private final OwnerBookingListContract.Presenter presenter;
    private final RequestManager imageRequestManager;
    private final LayoutInflater inflater;

    public BookingListAdapter(OwnerBookingListContract.Presenter presenter, Context context) {
        this.presenter = presenter;
        imageRequestManager = Glide.with(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BookingItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_owner_booking_list, parent, false);

        //for debug
        rootView.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), CarReturnedActivity.class);
            intent.putExtra("booking_id", 430);
            parent.getContext().startActivity(intent);
        });

        return new BookingItemHolder(rootView, imageRequestManager);
    }

    @Override
    public void onBindViewHolder(BookingItemHolder holder, int position) {
        Booking booking = presenter.onItem(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return presenter.count();
    }

    public static class BookingItemHolder extends RecyclerView.ViewHolder {

        private RequestManager imageRequestManager;

        private TextView bookingStatus;
        private TextView bookingPerion;
        private ImageView bookingCarPicture;
        private TextView bookingCarTitle;
        private TextView bookingCarYear;
        private TextView bookingCarPlateNumber;
        private TextView bookingCreatedDate;
        private TextView bookingAmount;
        private ProgressBar bookingLoadingIndicator;

        BookingItemHolder(View itemView, RequestManager imageRequestManager) {
            super(itemView);
            this.imageRequestManager = imageRequestManager;
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingPerion = itemView.findViewById(R.id.booking_period);
            bookingCarPicture = itemView.findViewById(R.id.booking_car_picture);
            bookingCarTitle = itemView.findViewById(R.id.booking_car_title);
            bookingCarYear = itemView.findViewById(R.id.booking_car_year);
            bookingCarPlateNumber = itemView.findViewById(R.id.booking_car_plate_number);
            bookingCreatedDate = itemView.findViewById(R.id.booking_date_created);
            bookingAmount = itemView.findViewById(R.id.booking_amount);
            bookingLoadingIndicator = itemView.findViewById(R.id.booking_progress);
        }

        public void bind(Booking booking) {
            BookingState status = booking.getBookingStateType();
            bookingStatus.setBackgroundResource(status != null ? status.getColorId() : DEFAULT_STATE_COLOR);
            bookingStatus.setText(status != null ? status.getTitleId() : DEFAULT_STATE);
            String timeBegin = booking.getTimeBegin();
            String timeEnd = booking.getTimeEnd();
            String timePeriod = null;
            if (timeBegin != null && timeEnd != null) {
                timePeriod = timeBegin + " to " + timeEnd;
            }
            bookingPerion.setText(timePeriod);
            bookingCarTitle.setText(booking.getCarTitle());
            bookingCarYear.setText(booking.getManufactureYear());
            bookingCarPlateNumber.setText(booking.getPlateNumber());
            String amountString = booking.getTotalAmount() == null ? "$0" : "$" + booking.getTotalAmount();
            bookingCreatedDate.setText(booking.getDateCreated());
            bookingAmount.setText(amountString);
            bookingLoadingIndicator.setVisibility(View.VISIBLE);
            imageRequestManager
                    .load(booking.getPrimaryImage().getLink())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            bookingLoadingIndicator.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            bookingLoadingIndicator.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.img_no_car)
                    .into(bookingCarPicture);
        }
    }
}
