package com.cardee.owner_bookings.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import com.cardee.owner_bookings.OwnerBookingListContract;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.BookingItemHolder> {

    private static final int DEFAULT_STATE = R.string.booking_state_completed;
    private static final int DEFAULT_STATE_COLOR = R.color.booking_state_completed;

    private final OwnerBookingListContract.Presenter presenter;
    private final RequestManager imageRequestManager;
    private final LayoutInflater inflater;

    private boolean isRenter;

    public BookingListAdapter(OwnerBookingListContract.Presenter presenter, Context context, boolean isRenter) {
        this.presenter = presenter;
        imageRequestManager = Glide.with(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isRenter = isRenter;
    }

    @Override
    public BookingItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_owner_booking_list, parent, false);
        return new BookingItemHolder(rootView, imageRequestManager, isRenter);
    }

    @Override
    public void onBindViewHolder(BookingItemHolder holder, int position) {
        Booking booking = presenter.onItem(position);
        holder.bind(booking);
        holder.itemView.setOnClickListener((v) -> presenter.onItemClick(booking));
    }

    @Override
    public int getItemCount() {
        return presenter.count();
    }

    public static class BookingItemHolder extends RecyclerView.ViewHolder {

        private RequestManager imageRequestManager;

        private boolean isRenter;

        private TextView bookingStatus;
        private TextView bookingPeriod;
        private ImageView bookingCarPicture;
        private TextView bookingCarTitle;
        private TextView bookingCarPlateNumber;
        private TextView bookingCreatedDate;
        private TextView bookingAmount;
        private ProgressBar bookingLoadingIndicator;

        BookingItemHolder(View itemView, RequestManager imageRequestManager, boolean isRenter) {
            super(itemView);
            this.imageRequestManager = imageRequestManager;
            this.isRenter = isRenter;
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingPeriod = itemView.findViewById(R.id.booking_period);
            bookingCarPicture = itemView.findViewById(R.id.booking_car_picture);
            bookingCarTitle = itemView.findViewById(R.id.booking_car_title);
            bookingCarPlateNumber = itemView.findViewById(R.id.booking_car_plate_number);
            bookingCreatedDate = itemView.findViewById(R.id.booking_date_created);
            bookingAmount = itemView.findViewById(R.id.booking_amount);
            bookingLoadingIndicator = itemView.findViewById(R.id.booking_progress);
        }

        public void bind(Booking booking) {
            BookingState status = booking.getBookingStateType();
            bookingStatus.setBackgroundResource(status != null ? status.getColorId() : DEFAULT_STATE_COLOR);
//            bookingStatus.setText(status != null ? status.getTitleId() : DEFAULT_STATE);
            setStatusText(booking);
            String timeBegin = booking.getTimeBegin();
            String timeEnd = booking.getTimeEnd();
            String timePeriod = null;
            if (timeBegin != null && timeEnd != null) {
                timePeriod = timeBegin + " - " + timeEnd; //'to' changed to '-' for better UX
            }
            bookingPeriod.setText(timePeriod);

            SpannableString title = new SpannableString(booking.getCarTitle());
            title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bookingCarTitle.setText(title);

            SpannableString year = new SpannableString("   " + booking.getManufactureYear());
            year.setSpan(new RelativeSizeSpan(0.9f), 0, year.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bookingCarTitle.append(year);

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

        private void setStatusText(Booking booking) {
            BookingState status = booking.getBookingStateType();
            if (status == null) {
                bookingStatus.setText(DEFAULT_STATE);
                return;
            }

            if (isRenter) {
                switch (status.getTitleId()) {
                    case R.string.booking_state_new:
                        bookingStatus.setText(R.string.booking_state_new_renter);
                        break;
                    case R.string.booking_state_canceled:
                        bookingStatus.setText(status.getTitleId());
                        break;
                    case R.string.booking_state_confirmed:
                        bookingStatus.setText(status.getTitleId());
                        break;
                    case R.string.booking_state_collecting:
                        bookingStatus.setText(R.string.booking_state_collecting_renter);
                        break;
                    case R.string.booking_state_collected:
                        bookingStatus.setText(R.string.booking_state_collected_renter);
                        break;
                    default:
                        bookingStatus.setText(DEFAULT_STATE);
                        break;
                }
                return;
            }
            bookingStatus.setText(status.getTitleId());
        }
    }

}
